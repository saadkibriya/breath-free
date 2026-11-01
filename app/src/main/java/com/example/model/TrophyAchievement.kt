package com.example.model

import com.example.data.JourneyProfile

enum class TrophyTier(val label: String, val colorHex: Long) {
    BRONZE("Bronze", 0xFFCD7F32),
    SILVER("Silver", 0xFFC0C0C0),
    GOLD("Gold", 0xFFFFD700),
    PLATINUM("Platinum", 0xFFE5E4E2)
}

enum class TrophyCategory(val label: String) {
    TIME("Time Clean"),
    SAVINGS("Money Saved"),
    CRAVINGS("Urge Control"),
    HEALTH("Body Recovery")
}

data class Trophy(
    val id: String,
    val title: String,
    val description: String,
    val category: TrophyCategory,
    val tier: TrophyTier,
    val requirementText: String,
    val isUnlocked: (profile: JourneyProfile, cravingsResisted: Int, now: Long) -> Boolean,
    val progress: (profile: JourneyProfile, cravingsResisted: Int, now: Long) -> Float
) {
    companion object {
        val ALL_TROPHIES = listOf(
            // Time Milestones
            Trophy(
                id = "clean_24h",
                title = "First 24 Hours",
                description = "Completed your first full day smoke-free! Carbon monoxide cleared.",
                category = TrophyCategory.TIME,
                tier = TrophyTier.BRONZE,
                requirementText = "24 hours smoke-free",
                isUnlocked = { profile, _, now -> profile.elapsedMillis(now) >= 24 * 3600 * 1000L },
                progress = { profile, _, now ->
                    (profile.elapsedMillis(now).toFloat() / (24 * 3600 * 1000f)).coerceIn(0f, 1f)
                }
            ),
            Trophy(
                id = "clean_3d",
                title = "72h Peak Crusher",
                description = "Pushed through the 3-day nicotine withdrawal peak like a champion.",
                category = TrophyCategory.TIME,
                tier = TrophyTier.BRONZE,
                requirementText = "3 days smoke-free",
                isUnlocked = { profile, _, now -> profile.elapsedMillis(now) >= 72 * 3600 * 1000L },
                progress = { profile, _, now ->
                    (profile.elapsedMillis(now).toFloat() / (72 * 3600 * 1000f)).coerceIn(0f, 1f)
                }
            ),
            Trophy(
                id = "clean_1w",
                title = "One Week Warrior",
                description = "7 unbroken days without smoking. Your taste and smell have revitalized.",
                category = TrophyCategory.TIME,
                tier = TrophyTier.SILVER,
                requirementText = "7 days smoke-free",
                isUnlocked = { profile, _, now -> profile.elapsedMillis(now) >= 7 * 24 * 3600 * 1000L },
                progress = { profile, _, now ->
                    (profile.elapsedMillis(now).toFloat() / (7 * 24 * 3600 * 1000f)).coerceIn(0f, 1f)
                }
            ),
            Trophy(
                id = "clean_2w",
                title = "Two Weeks Strong",
                description = "14 days clean! Lung function and blood circulation significantly boosted.",
                category = TrophyCategory.TIME,
                tier = TrophyTier.SILVER,
                requirementText = "14 days smoke-free",
                isUnlocked = { profile, _, now -> profile.elapsedMillis(now) >= 14 * 24 * 3600 * 1000L },
                progress = { profile, _, now ->
                    (profile.elapsedMillis(now).toFloat() / (14 * 24 * 3600 * 1000f)).coerceIn(0f, 1f)
                }
            ),
            Trophy(
                id = "clean_1m",
                title = "One Month Titan",
                description = "30 full days smoke-free. Airway cilia are actively cleansing your lungs.",
                category = TrophyCategory.TIME,
                tier = TrophyTier.GOLD,
                requirementText = "30 days smoke-free",
                isUnlocked = { profile, _, now -> profile.elapsedMillis(now) >= 30 * 24 * 3600 * 1000L },
                progress = { profile, _, now ->
                    (profile.elapsedMillis(now).toFloat() / (30 * 24 * 3600 * 1000f)).coerceIn(0f, 1f)
                }
            ),
            Trophy(
                id = "clean_3m",
                title = "Quarter-Year Master",
                description = "90 days of freedom! Coughing and shortness of breath are history.",
                category = TrophyCategory.TIME,
                tier = TrophyTier.PLATINUM,
                requirementText = "90 days smoke-free",
                isUnlocked = { profile, _, now -> profile.elapsedMillis(now) >= 90 * 24 * 3600 * 1000L },
                progress = { profile, _, now ->
                    (profile.elapsedMillis(now).toFloat() / (90 * 24 * 3600 * 1000f)).coerceIn(0f, 1f)
                }
            ),

            // Money Saved Milestones
            Trophy(
                id = "saved_25",
                title = "Pocket Money",
                description = "Saved your first 25 currency units! That's a good meal earned.",
                category = TrophyCategory.SAVINGS,
                tier = TrophyTier.BRONZE,
                requirementText = "Save $25 in cigarette money",
                isUnlocked = { profile, _, now -> profile.totalMoneySaved(now) >= 25.0 },
                progress = { profile, _, now ->
                    (profile.totalMoneySaved(now).toFloat() / 25f).coerceIn(0f, 1f)
                }
            ),
            Trophy(
                id = "saved_100",
                title = "Century Club",
                description = "Surpassed 100 in total savings! Your bank balance is thanking you.",
                category = TrophyCategory.SAVINGS,
                tier = TrophyTier.SILVER,
                requirementText = "Save $100 in cigarette money",
                isUnlocked = { profile, _, now -> profile.totalMoneySaved(now) >= 100.0 },
                progress = { profile, _, now ->
                    (profile.totalMoneySaved(now).toFloat() / 100f).coerceIn(0f, 1f)
                }
            ),
            Trophy(
                id = "saved_500",
                title = "Wealth Builder",
                description = "Accumulated over 500 saved! Enough for a tech upgrade or getaway.",
                category = TrophyCategory.SAVINGS,
                tier = TrophyTier.GOLD,
                requirementText = "Save $500 in cigarette money",
                isUnlocked = { profile, _, now -> profile.totalMoneySaved(now) >= 500.0 },
                progress = { profile, _, now ->
                    (profile.totalMoneySaved(now).toFloat() / 500f).coerceIn(0f, 1f)
                }
            ),

            // Craving Control Milestones
            Trophy(
                id = "craving_first",
                title = "First Urge Conquered",
                description = "Felt an urge and chose your health. The first victory is the sweetest.",
                category = TrophyCategory.CRAVINGS,
                tier = TrophyTier.BRONZE,
                requirementText = "Resist 1 craving",
                isUnlocked = { _, cravings, _ -> cravings >= 1 },
                progress = { _, cravings, _ -> (cravings.toFloat() / 1f).coerceIn(0f, 1f) }
            ),
            Trophy(
                id = "craving_5",
                title = "Iron Will",
                description = "Overcame 5 tough cravings without yielding. You are in control.",
                category = TrophyCategory.CRAVINGS,
                tier = TrophyTier.SILVER,
                requirementText = "Resist 5 cravings",
                isUnlocked = { _, cravings, _ -> cravings >= 5 },
                progress = { _, cravings, _ -> (cravings.toFloat() / 5f).coerceIn(0f, 1f) }
            ),
            Trophy(
                id = "craving_15",
                title = "Craving Slayer",
                description = "Resisted 15 urges! Nicotine triggers are losing their power over you.",
                category = TrophyCategory.CRAVINGS,
                tier = TrophyTier.GOLD,
                requirementText = "Resist 15 cravings",
                isUnlocked = { _, cravings, _ -> cravings >= 15 },
                progress = { _, cravings, _ -> (cravings.toFloat() / 15f).coerceIn(0f, 1f) }
            ),

            // Health & Avoided Milestones
            Trophy(
                id = "cigs_50",
                title = "50 Smoke-Free",
                description = "Avoided 50 cigarettes! Over 9 hours of clean life added back.",
                category = TrophyCategory.HEALTH,
                tier = TrophyTier.BRONZE,
                requirementText = "Pass 50 cigarettes",
                isUnlocked = { profile, _, now -> profile.cigarettesAvoided(now) >= 50 },
                progress = { profile, _, now ->
                    (profile.cigarettesAvoided(now).toFloat() / 50f).coerceIn(0f, 1f)
                }
            ),
            Trophy(
                id = "cigs_200",
                title = "Ten Packs Avoided",
                description = "Over 200 cigarettes passed! Your lungs are thanking you every second.",
                category = TrophyCategory.HEALTH,
                tier = TrophyTier.SILVER,
                requirementText = "Pass 200 cigarettes",
                isUnlocked = { profile, _, now -> profile.cigarettesAvoided(now) >= 200 },
                progress = { profile, _, now ->
                    (profile.cigarettesAvoided(now).toFloat() / 200f).coerceIn(0f, 1f)
                }
            ),
            Trophy(
                id = "cigs_500",
                title = "Breath of Freedom",
                description = "500 cigarettes not smoked! Your cardiovascular system is transformed.",
                category = TrophyCategory.HEALTH,
                tier = TrophyTier.GOLD,
                requirementText = "Pass 500 cigarettes",
                isUnlocked = { profile, _, now -> profile.cigarettesAvoided(now) >= 500 },
                progress = { profile, _, now ->
                    (profile.cigarettesAvoided(now).toFloat() / 500f).coerceIn(0f, 1f)
                }
            )
        )
    }
}
