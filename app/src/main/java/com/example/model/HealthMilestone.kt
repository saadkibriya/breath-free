package com.example.model

data class HealthMilestone(
    val id: String,
    val durationMillis: Long,
    val title: String,
    val timeLabel: String,
    val description: String,
    val category: String, // "Heart", "Lungs", "Senses", "Longevity"
    val iconName: String
) {
    fun progress(elapsedMillis: Long): Float {
        if (elapsedMillis >= durationMillis) return 1.0f
        if (elapsedMillis <= 0L) return 0.0f
        return (elapsedMillis.toFloat() / durationMillis.toFloat()).coerceIn(0f, 1f)
    }

    fun isAchieved(elapsedMillis: Long): Boolean {
        return elapsedMillis >= durationMillis
    }

    fun remainingTimeFormatted(elapsedMillis: Long): String {
        if (isAchieved(elapsedMillis)) return "Completed!"
        val remaining = durationMillis - elapsedMillis
        val days = remaining / (1000L * 60 * 60 * 24)
        val hours = (remaining / (1000L * 60 * 60)) % 24
        val minutes = (remaining / (1000L * 60)) % 60
        return when {
            days > 0 -> "${days}d ${hours}h remaining"
            hours > 0 -> "${hours}h ${minutes}m remaining"
            else -> "${minutes}m remaining"
        }
    }

    companion object {
        val ALL_MILESTONES = listOf(
            HealthMilestone(
                id = "20_min",
                durationMillis = 20 * 60 * 1000L,
                title = "Pulse & Blood Pressure",
                timeLabel = "20 Minutes",
                description = "Your heart rate and blood pressure drop back to normal healthy resting levels.",
                category = "Heart",
                iconName = "favorite"
            ),
            HealthMilestone(
                id = "8_hours",
                durationMillis = 8 * 3600 * 1000L,
                title = "Carbon Monoxide Clears",
                timeLabel = "8 Hours",
                description = "Carbon monoxide levels in your blood decrease by half. Blood oxygen levels return to normal.",
                category = "Lungs",
                iconName = "air"
            ),
            HealthMilestone(
                id = "24_hours",
                durationMillis = 24 * 3600 * 1000L,
                title = "Heart Attack Risk Drops",
                timeLabel = "24 Hours",
                description = "Nicotine is almost completely eliminated from your bloodstream. Immediate heart attack risk begins falling.",
                category = "Heart",
                iconName = "health_and_safety"
            ),
            HealthMilestone(
                id = "48_hours",
                durationMillis = 48 * 3600 * 1000L,
                title = "Taste & Smell Reborn",
                timeLabel = "48 Hours",
                description = "Damaged nerve endings start regenerating. Your senses of taste and smell dramatically improve.",
                category = "Senses",
                iconName = "restaurant"
            ),
            HealthMilestone(
                id = "72_hours",
                durationMillis = 72 * 3600 * 1000L,
                title = "Bronchial Tubes Relax",
                timeLabel = "72 Hours",
                description = "Airway bronchial tubes relax, making breathing effortless. Overall energy levels significantly jump.",
                category = "Lungs",
                iconName = "spa"
            ),
            HealthMilestone(
                id = "1_week",
                durationMillis = 7 * 24 * 3600 * 1000L,
                title = "Physical Peak Defeated",
                timeLabel = "1 Week",
                description = "You made it through the hardest phase. Most intense physical withdrawal symptoms are now over.",
                category = "Longevity",
                iconName = "military_tech"
            ),
            HealthMilestone(
                id = "2_weeks",
                durationMillis = 14 * 24 * 3600 * 1000L,
                title = "Circulation & Lung Boost",
                timeLabel = "2 Weeks",
                description = "Blood circulation accelerates and lung function increases by up to 30%. Walking and stairs feel light.",
                category = "Lungs",
                iconName = "directions_run"
            ),
            HealthMilestone(
                id = "1_month",
                durationMillis = 30 * 24 * 3600 * 1000L,
                title = "Airway Cilia Regrowth",
                timeLabel = "1 Month",
                description = "Cilia in your airways start functioning again, cleaning out old debris and drastically cutting infection risk.",
                category = "Lungs",
                iconName = "cleaning_services"
            ),
            HealthMilestone(
                id = "3_months",
                durationMillis = 90 * 24 * 3600 * 1000L,
                title = "Coughing & Shortness Cleared",
                timeLabel = "3 Months",
                description = "Chronic smoker's cough and sinus congestion vanish. Deep inhalations feel effortless and clean.",
                category = "Lungs",
                iconName = "self_improvement"
            ),
            HealthMilestone(
                id = "1_year",
                durationMillis = 365 * 24 * 3600 * 1000L,
                title = "Heart Disease Risk Cut by 50%",
                timeLabel = "1 Year",
                description = "Your excess risk of coronary heart disease drops by half compared to when you smoked.",
                category = "Heart",
                iconName = "shield"
            ),
            HealthMilestone(
                id = "5_years",
                durationMillis = 5 * 365 * 24 * 3600 * 1000L,
                title = "Stroke Risk Normalized",
                timeLabel = "5 Years",
                description = "Your risk of having a stroke is reduced to that of a lifelong non-smoker.",
                category = "Longevity",
                iconName = "emoji_events"
            )
        )
    }
}
