package com.example.data

import android.content.Context
import android.content.SharedPreferences
import java.util.Locale
import kotlin.math.max

data class JourneyProfile(
    val quitTimestamp: Long,
    val cigarettesPerDay: Int,
    val pricePerPack: Double,
    val cigarettesPerPack: Int,
    val currencySymbol: String,
    val goalTitle: String,
    val goalCost: Double,
    val heightCm: Float = 175f,
    val weightKg: Float = 70f,
    val healthConditions: Set<String> = emptySet(),
    val hasCompletedAiOnboarding: Boolean = false,
    val smokeWindowEndTime: Long = 0L
) {
    fun elapsedMillis(now: Long = System.currentTimeMillis()): Long {
        return max(0L, now - quitTimestamp)
    }

    fun days(now: Long = System.currentTimeMillis()): Long {
        return elapsedMillis(now) / (1000L * 60 * 60 * 24)
    }

    fun hours(now: Long = System.currentTimeMillis()): Long {
        return (elapsedMillis(now) / (1000L * 60 * 60)) % 24
    }

    fun minutes(now: Long = System.currentTimeMillis()): Long {
        return (elapsedMillis(now) / (1000L * 60)) % 60
    }

    fun seconds(now: Long = System.currentTimeMillis()): Long {
        return (elapsedMillis(now) / 1000L) % 60
    }

    val dailyCost: Double
        get() {
            val packsPerDay = cigarettesPerDay.toDouble() / max(1, cigarettesPerPack)
            return packsPerDay * pricePerPack
        }

    fun totalMoneySaved(now: Long = System.currentTimeMillis()): Double {
        val daysElapsed = elapsedMillis(now).toDouble() / (1000.0 * 60 * 60 * 24)
        return daysElapsed * dailyCost
    }

    fun cigarettesAvoided(now: Long = System.currentTimeMillis()): Int {
        val daysElapsed = elapsedMillis(now).toDouble() / (1000.0 * 60 * 60 * 24)
        return (daysElapsed * cigarettesPerDay).toInt()
    }

    fun lifeRegainedMinutes(now: Long = System.currentTimeMillis()): Long {
        // ~11 minutes per cigarette avoided according to medical research
        return cigarettesAvoided(now) * 11L
    }

    // Health & Biometrics calculations
    val bmi: Float
        get() {
            val heightM = heightCm / 100f
            if (heightM <= 0.5f) return 22.0f
            return weightKg / (heightM * heightM)
        }

    val bmiCategory: String
        get() {
            val b = bmi
            return when {
                b < 18.5f -> "Underweight"
                b < 25.0f -> "Normal weight"
                b < 30.0f -> "Overweight"
                else -> "Obese"
            }
        }

    val projectedAnnualSaved: Double
        get() = dailyCost * 365.25

    val projectedAnnualCigarettesAvoided: Int
        get() = (cigarettesPerDay * 365.25).toInt()

    fun isSmokeWindowActive(now: Long = System.currentTimeMillis()): Boolean {
        return smokeWindowEndTime > now
    }

    fun smokeWindowRemainingSeconds(now: Long = System.currentTimeMillis()): Long {
        return max(0L, (smokeWindowEndTime - now) / 1000L)
    }

    fun formatCurrency(amount: Double): String {
        return String.format(Locale.US, "%s%.2f", currencySymbol, amount)
    }
}

class JourneyPreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("breathe_free_journey_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_QUIT_TIME = "quit_timestamp"
        private const val KEY_CIGS_PER_DAY = "cigs_per_day"
        private const val KEY_PRICE_PER_PACK = "price_per_pack"
        private const val KEY_CIGS_PER_PACK = "cigs_per_pack"
        private const val KEY_CURRENCY = "currency_symbol"
        private const val KEY_GOAL_TITLE = "goal_title"
        private const val KEY_GOAL_COST = "goal_cost"
        private const val KEY_HEIGHT_CM = "height_cm"
        private const val KEY_WEIGHT_KG = "weight_kg"
        private const val KEY_CONDITIONS = "health_conditions"
        private const val KEY_AI_ONBOARDING_COMPLETED = "ai_onboarding_completed"
        private const val KEY_SMOKE_WINDOW_END = "smoke_window_end"
    }

    fun getProfile(): JourneyProfile {
        // Default initialized to 3 days, 14 hours, 20 minutes ago for instant live demonstration
        val defaultTime = System.currentTimeMillis() - ((3L * 24 + 14) * 3600 + 20 * 60) * 1000L
        val quitTime = prefs.getLong(KEY_QUIT_TIME, defaultTime)
        val cigsPerDay = prefs.getInt(KEY_CIGS_PER_DAY, 16)
        val pricePerPack = prefs.getFloat(KEY_PRICE_PER_PACK, 12.00f).toDouble()
        val cigsPerPack = prefs.getInt(KEY_CIGS_PER_PACK, 20)
        val currency = prefs.getString(KEY_CURRENCY, "$") ?: "$"
        val goalTitle = prefs.getString(KEY_GOAL_TITLE, "Weekend Getaway") ?: "Weekend Getaway"
        val goalCost = prefs.getFloat(KEY_GOAL_COST, 250.0f).toDouble()
        val heightCm = prefs.getFloat(KEY_HEIGHT_CM, 175.0f)
        val weightKg = prefs.getFloat(KEY_WEIGHT_KG, 72.0f)
        val conditions = prefs.getStringSet(KEY_CONDITIONS, setOf("Hypertension", "General Health")) ?: emptySet()
        val hasCompletedOnboarding = prefs.getBoolean(KEY_AI_ONBOARDING_COMPLETED, false)
        val smokeWindowEnd = prefs.getLong(KEY_SMOKE_WINDOW_END, 0L)

        return JourneyProfile(
            quitTimestamp = quitTime,
            cigarettesPerDay = cigsPerDay,
            pricePerPack = pricePerPack,
            cigarettesPerPack = cigsPerPack,
            currencySymbol = currency,
            goalTitle = goalTitle,
            goalCost = goalCost,
            heightCm = heightCm,
            weightKg = weightKg,
            healthConditions = conditions,
            hasCompletedAiOnboarding = hasCompletedOnboarding,
            smokeWindowEndTime = smokeWindowEnd
        )
    }

    fun saveProfile(profile: JourneyProfile) {
        prefs.edit()
            .putLong(KEY_QUIT_TIME, profile.quitTimestamp)
            .putInt(KEY_CIGS_PER_DAY, profile.cigarettesPerDay)
            .putFloat(KEY_PRICE_PER_PACK, profile.pricePerPack.toFloat())
            .putInt(KEY_CIGS_PER_PACK, profile.cigarettesPerPack)
            .putString(KEY_CURRENCY, profile.currencySymbol)
            .putString(KEY_GOAL_TITLE, profile.goalTitle)
            .putFloat(KEY_GOAL_COST, profile.goalCost.toFloat())
            .putFloat(KEY_HEIGHT_CM, profile.heightCm)
            .putFloat(KEY_WEIGHT_KG, profile.weightKg)
            .putStringSet(KEY_CONDITIONS, profile.healthConditions)
            .putBoolean(KEY_AI_ONBOARDING_COMPLETED, profile.hasCompletedAiOnboarding)
            .putLong(KEY_SMOKE_WINDOW_END, profile.smokeWindowEndTime)
            .apply()
    }

    fun resetQuitDate(newTimestamp: Long = System.currentTimeMillis()) {
        prefs.edit().putLong(KEY_QUIT_TIME, newTimestamp).apply()
    }
}
