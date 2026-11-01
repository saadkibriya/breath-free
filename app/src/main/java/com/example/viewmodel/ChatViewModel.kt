package com.example.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.util.UUID

private const val TAG = "ChatViewModel"

enum class MessageSender {
    USER,
    BOT
}

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val sender: MessageSender,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isDistractionAction: Boolean = false,
    val actionType: String? = null // e.g., "BREATHE", "MUSIC", "WATER"
)

sealed interface ChatUiState {
    data object Idle : ChatUiState
    data object Loading : ChatUiState
    data class Success(val lastMessage: String) : ChatUiState
    data class Error(val message: String) : ChatUiState
}

class ChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _uiState = MutableStateFlow<ChatUiState>(ChatUiState.Idle)
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    // Lazy initialization of Firebase AI Logic model with Gemini 3.8 Flash
    private val generativeModel by lazy {
        try {
            Firebase.ai.generativeModel("gemini-3.8-flash")
        } catch (e: Exception) {
            Log.w(TAG, "Firebase.ai could not be initialized lazily: ${e.message}")
            null
        }
    }

    private val systemPrompt = """
        You are BreatheAI, a warm, modern, compassionate quit-smoking companion.
        Your wisdom is deeply rooted in peer-tested experiences from ex-smokers (r/stopsmoking community on Reddit) and clinical addiction science:
        1. Cravings peak and dissipate within 3 to 5 minutes. Urge surfing works: ride the wave without giving in.
        2. Sensory disruptions: Listening to high-energy music via headphones, drinking ice-cold water through a straw (mimics inhalation + vagus nerve reset), chewing cinnamon gum or sour candy, taking 10 deep diaphragmatic breaths.
        3. The 15-day neurochemical cliff: In the first 15 days, acetylcholine receptors down-regulate. Remind the user that after day 15, cravings drop exponentially.
        4. Slips vs Relapses: If someone slipped, do not judge or catastrophize. One slip doesn't erase days of biological repair (blood pressure, oxygen, cilia regeneration). Remind them: It's not who avoids every stumble, it's who keeps fighting the war.
        5. Tone: Concise, authentic, energetic, empathetic, like a supportive friend who has walked the same path.
    """.trimIndent()

    init {
        // Welcome message with community warmth
        val welcome = ChatMessage(
            sender = MessageSender.BOT,
            text = "Hey warrior! 👋 I'm BreatheAI, your quit-smoking companion. Whenever an urge hits or you feel anxious, reach out here.\n\n" +
                    "Got a sudden craving right now? Try putting on your favorite upbeat song 🎧 or taking a tall sip of ice water. Tell me how you're feeling!",
            timestamp = System.currentTimeMillis()
        )
        _messages.value = listOf(welcome)
    }

    fun clearChat() {
        val welcome = ChatMessage(
            sender = MessageSender.BOT,
            text = "Chat reset! Ready for a fresh round. How can I help you conquer cravings right now?",
            timestamp = System.currentTimeMillis()
        )
        _messages.value = listOf(welcome)
        _uiState.value = ChatUiState.Idle
    }

    fun sendMessage(userText: String) {
        val trimmed = userText.trim()
        if (trimmed.isEmpty()) return

        val userMessage = ChatMessage(
            sender = MessageSender.USER,
            text = trimmed,
            timestamp = System.currentTimeMillis()
        )

        _messages.value = _messages.value + userMessage
        _uiState.value = ChatUiState.Loading

        viewModelScope.launch {
            try {
                var responseText: String? = null

                // Attempt live Firebase AI Logic generation if available
                val model = generativeModel
                if (model != null) {
                    try {
                        val fullPrompt = "$systemPrompt\n\nUser situation: $trimmed\n\nBreatheAI Response:"
                        val result = withTimeout(30_000L) {
                            model.generateContent(fullPrompt)
                        }
                        responseText = result.text
                    } catch (e: CancellationException) {
                        throw e
                    } catch (e: Exception) {
                        Log.w(TAG, "Firebase AI generation error, utilizing smart peer response engine: ${e.message}")
                    }
                }

                // If live AI call was null/unavailable or offline, generate authentic Reddit community guidance
                val finalReply = responseText?.takeIf { it.isNotBlank() } ?: generatePeerCommunityAdvice(trimmed)

                val botMessage = ChatMessage(
                    sender = MessageSender.BOT,
                    text = finalReply,
                    timestamp = System.currentTimeMillis(),
                    isDistractionAction = detectDistractionType(trimmed) != null,
                    actionType = detectDistractionType(trimmed)
                )

                _messages.value = _messages.value + botMessage
                _uiState.value = ChatUiState.Success(finalReply)

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, "Chat dispatch failed", e)
                val fallback = generatePeerCommunityAdvice(trimmed)
                val botMessage = ChatMessage(
                    sender = MessageSender.BOT,
                    text = fallback,
                    timestamp = System.currentTimeMillis()
                )
                _messages.value = _messages.value + botMessage
                _uiState.value = ChatUiState.Success(fallback)
            }
        }
    }

    private fun detectDistractionType(text: String): String? {
        val lower = text.lowercase()
        return when {
            "music" in lower || "song" in lower -> "MUSIC"
            "breathe" in lower || "panic" in lower || "anxious" in lower -> "BREATHE"
            "water" in lower || "thirst" in lower -> "WATER"
            else -> null
        }
    }

    /**
     * Rich, peer-curated guidance synthesized directly from Reddit (r/stopsmoking)
     * and behavioral cessation neuroscience.
     */
    private fun generatePeerCommunityAdvice(prompt: String): String {
        val q = prompt.lowercase()
        return when {
            "music" in q || "song" in q || "playlist" in q -> {
                "🎵 **The Reddit 3-Minute Music Trick:**\n\n" +
                "Put in your headphones and blast your favorite high-tempo or cinematic track right now! Cravings have a physiological peak that lasts only about 180 to 300 seconds.\n\n" +
                "When you flood your brain's auditory cortex with music you love, dopamine releases naturally, hijacking the same reward pathways the cigarette was demanding. Try tapping your foot or singing along — your hands and mouth stay busy, and by song's end, the wave has passed!"
            }
            "craving" in q || "urge" in q || "want one" in q || "bad" in q || "hard" in q -> {
                "⚡ **Urge Surfing Protocol (Top r/stopsmoking strategy):**\n\n" +
                "1. **Notice the physical feeling:** Feel where the craving is (throat, chest, fingertips). Don't fight it — just observe it like a wave in the ocean.\n" +
                "2. **The 3-Minute Rule:** A craving cannot physically sustain maximum intensity past 3 minutes without nicotine.\n" +
                "3. **Ice Water Shock:** Grab a glass of freezing ice water and sip it through a straw. It shocks your vagus nerve and recreates the oral soothing loop.\n\n" +
                "You are not giving up something good; you are freeing yourself from an annoying chemical cycle!"
            }
            "coffee" in q || "meal" in q || "after eating" in q || "habit" in q -> {
                "☕ **Breaking the After-Meal / Coffee Trigger:**\n\n" +
                "On Reddit, people call this 'Rewiring the Loop'. For years, your brain paired finishing a meal or morning coffee with nicotine.\n\n" +
                "• **Immediate Action:** The moment you put down your fork, stand up immediately! Walk outside for 60 seconds or brush your teeth with strong mint toothpaste. The fresh menthol taste completely kills the desire for smoke.\n" +
                "• Switch your coffee spot: If you usually drink it on the porch, drink it at your desk or kitchen counter. Altering physical context breaks the autopilot trigger."
            }
            "slip" in q || "failed" in q || "smoke" in q || "reset" in q || "gave in" in q -> {
                "🛡️ **Compassionate Recovery: It's Not Over!**\n\n" +
                "Take a deep breath. A slip is NOT a relapse unless you abandon the fight!\n\n" +
                "Think of it scientifically: Your blood pressure dropped, your carbon monoxide cleared, and your lungs started repairing cilia over the past days. None of that biological healing is cancelled by a momentary stumble.\n\n" +
                "Ask yourself: *'What was the trigger? (stress, alcohol, boredom)?'* Learn from it. That's valuable data. Stand back up, dust off your knees, and keep marching toward the 15-day peak!"
            }
            "15 day" in q || "target" in q || "window" in q || "timeline" in q -> {
                "🎯 **Why the 15-Day Target Is the Holy Grail:**\n\n" +
                "Trying to think of 'never smoking again for the rest of my life' feels overwhelmingly daunting. That's why we focus on the **15-Day Neurochemical Cliff**.\n\n" +
                "During days 1 to 3, nicotine leaves your body and receptors scream the loudest. But by day 14–15, acetylcholine receptors down-regulate to near-normal baseline! Cravings become mild, infrequent thoughts rather than physical emergencies. Hold on until Day 15 — the game changes completely!"
            }
            "reddit" in q || "hack" in q || "tip" in q || "trick" in q -> {
                "🔥 **Top 4 Real-Life Reddit Ex-Smoker Hacks:**\n\n" +
                "1. **Cinnamon Toothpicks & Strong Gum:** Keeps mouth and hands stimulated without calories.\n" +
                "2. **The 10-Second Breathe-Out:** Inhale through your nose for 4s, hold 7s, exhale slowly like you're blowing out a candle for 8s. It activates the parasympathetic nervous system instantly.\n" +
                "3. **Clean Your Environment:** Wash the jackets you used to smoke in. Smelling fresh laundry gives you a huge sensory reward.\n" +
                "4. **Remember N.O.P.E.:** 'Not One Puff, Ever' — because one puff doesn't relieve cravings, it just feeds the monster for another 3 days."
            }
            else -> {
                "💪 **You are stronger than a 3-minute chemical impulse.**\n\n" +
                "Whatever you are doing right now, pause for 60 seconds. Roll your shoulders back, unclench your jaw, and take two deep belly breaths.\n\n" +
                "Every craving you endure without smoking is a vote for your freedom. Do you want to try a quick 3-minute music distraction, grab some ice water, or log a resisted craving to celebrate your victory?"
            }
        }
    }
}
