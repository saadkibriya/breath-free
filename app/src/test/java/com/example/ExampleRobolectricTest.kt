package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppDatabase
import com.example.data.CravingRecord
import com.example.data.JourneyProfile
import com.example.model.ResearchBenefitsData
import com.example.model.Trophy
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    private lateinit var database: AppDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("BreatheFree", appName)
    }

    @Test
    fun `test craving logging and resisted count query`() = runBlocking {
        val dao = database.cravingDao()

        // Insert first resisted craving
        dao.insertCraving(
            CravingRecord(
                timestamp = System.currentTimeMillis(),
                intensity = 8,
                trigger = "Stress / Work",
                techniqueUsed = "Deep Breathing",
                overcome = true
            )
        )

        // Insert second resisted craving
        dao.insertCraving(
            CravingRecord(
                timestamp = System.currentTimeMillis(),
                intensity = 5,
                trigger = "Coffee Break",
                techniqueUsed = "Drank Cold Water",
                overcome = true
            )
        )

        val list = dao.getAllCravings().first()
        val count = dao.getOvercomeCount().first()

        assertEquals(2, list.size)
        assertEquals(2, count)
        assertEquals("Coffee Break", list[0].trigger)
        assertTrue(list[0].overcome)
    }

    @Test
    fun `test research benefits phases and quotes`() {
        val phase16h = ResearchBenefitsData.getResearchPhase(16 * 3600 * 1000L)
        assertNotNull(phase16h)
        assertTrue(phase16h.headline.contains("Nicotine"))
        assertTrue(phase16h.bodyStatus.contains("carbon monoxide"))

        val phaseDay10 = ResearchBenefitsData.getResearchPhase(10L * 24 * 3600 * 1000L)
        assertTrue(phaseDay10.headline.contains("15-Day"))
        assertTrue(phaseDay10.cravingInsight.contains("Day 15"))

        assertTrue(ResearchBenefitsData.QUOTES.isNotEmpty())
    }

    @Test
    fun `test journey profile calculation for days, hours and money saved`() {
        val now = 1700000000000L
        val quitTime = now - (128L * 3600 * 1000L)
        val profile = JourneyProfile(
            quitTimestamp = quitTime,
            cigarettesPerDay = 20,
            pricePerPack = 10.0,
            cigarettesPerPack = 20,
            currencySymbol = "$",
            goalTitle = "Holiday",
            goalCost = 500.0
        )

        assertEquals(5L, profile.days(now))
        assertEquals(8L, profile.hours(now))
        assertEquals(10.0, profile.dailyCost, 0.01)
        assertEquals(53.33, profile.totalMoneySaved(now), 0.1)
        assertEquals(106, profile.cigarettesAvoided(now))
        assertTrue(profile.lifeRegainedMinutes(now) > 1000)

        // Check trophy unlocks
        val clean24hTrophy = Trophy.ALL_TROPHIES.first { it.id == "clean_24h" }
        assertTrue(clean24hTrophy.isUnlocked(profile, 5, now))
        val clean3dTrophy = Trophy.ALL_TROPHIES.first { it.id == "clean_3d" }
        assertTrue(clean3dTrophy.isUnlocked(profile, 5, now))
    }

    @Test
    fun `test ChatViewModel messaging and distraction recommendation`() {
        val chatViewModel = com.example.viewmodel.ChatViewModel()
        val initialMessages = chatViewModel.messages.value
        assertTrue(initialMessages.isNotEmpty())
        assertTrue(initialMessages[0].text.contains("BreatheAI"))

        // Send a craving prompt asking about music
        chatViewModel.sendMessage("Tell me about using music to beat a craving")
        val updated = chatViewModel.messages.value
        assertEquals(3, updated.size) // Welcome + User prompt + Bot response
        val botReply = updated[2].text
        assertTrue(botReply.contains("Music") || botReply.contains("3-Minute"))
    }

    @Test
    fun `test biometrics, BMI, disease benefits, and smoke window`() {
        val now = 1700000000000L
        val profile = JourneyProfile(
            quitTimestamp = now - 100000L,
            cigarettesPerDay = 15,
            pricePerPack = 12.0,
            cigarettesPerPack = 20,
            currencySymbol = "$",
            goalTitle = "Camera",
            goalCost = 600.0,
            heightCm = 175f,
            weightKg = 70f,
            healthConditions = setOf("High Blood Pressure", "Type 2 Diabetes"),
            hasCompletedAiOnboarding = true,
            smokeWindowEndTime = now + 600 * 1000L // 10 minutes from now
        )

        // Check BMI = 70 / (1.75 * 1.75) = 22.857
        assertEquals(22.86f, profile.bmi, 0.1f)
        assertEquals("Normal weight", profile.bmiCategory)

        // Check annual projections
        assertEquals(5478, profile.projectedAnnualCigarettesAvoided)
        assertTrue(profile.projectedAnnualSaved > 3000.0)

        // Check disease benefits
        val matchedBenefits = ResearchBenefitsData.getBenefitsForConditions(profile.healthConditions)
        assertEquals(2, matchedBenefits.size)
        assertTrue(matchedBenefits.any { it.condition == "High Blood Pressure" })
        assertTrue(matchedBenefits.any { it.condition == "Type 2 Diabetes" })

        // Check smoke window
        assertTrue(profile.isSmokeWindowActive(now))
        assertEquals(600L, profile.smokeWindowRemainingSeconds(now))
        assertTrue(!profile.isSmokeWindowActive(now + 700 * 1000L))
    }
}

