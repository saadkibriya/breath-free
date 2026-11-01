package com.example.model

data class ResearchBenefitPhase(
    val id: String,
    val minDurationMillis: Long,
    val maxDurationMillis: Long,
    val headline: String,
    val bodyStatus: String,
    val cravingInsight: String,
    val scientificSource: String
)

data class MotivationalQuote(
    val quote: String,
    val author: String,
    val context: String
)

object ResearchBenefitsData {

    val QUOTES = listOf(
        MotivationalQuote(
            quote = "It is not the single fight that wins the war; it is the warrior who keeps standing back up.",
            author = "Resilience Principle",
            context = "Overcoming nicotine is a campaign, not a one-time test."
        ),
        MotivationalQuote(
            quote = "A slip is a temporary stumble on a mountain climb. You don't jump down the mountain because you tripped on a pebble.",
            author = "Cognitive Behavioral Psychology",
            context = "One cigarette does not erase 100 hours of biological healing."
        ),
        MotivationalQuote(
            quote = "Research reveals the average permanent non-smoker had several slips before permanent victory. Failure is expected data, not final defeat.",
            author = "Addiction Medicine Review",
            context = "Every attempt rewires your neurochemistry closer to freedom."
        ),
        MotivationalQuote(
            quote = "Don't fight the rest of your life today. You only need to hold the line for the next 15 minutes.",
            author = "Urge Surfing Philosophy",
            context = "Nicotine craving spikes peak at 3-5 minutes and dissolve."
        ),
        MotivationalQuote(
            quote = "The 15-day target is your true physical finish line. Once you reach Day 15, your nicotine receptors dramatically drop. Hold on until Day 15!",
            author = "Neurobiology of Nicotine Receptors",
            context = "Your physical battle gets 80% easier after Day 15."
        )
    )

    fun getResearchPhase(elapsedMillis: Long): ResearchBenefitPhase {
        val hours = elapsedMillis / (3600 * 1000L)
        val days = elapsedMillis / (24 * 3600 * 1000L)

        return when {
            elapsedMillis < 8 * 3600 * 1000L -> ResearchBenefitPhase(
                id = "phase_early",
                minDurationMillis = 0,
                maxDurationMillis = 8 * 3600 * 1000L,
                headline = "Heart Rate & Blood Pressure Normalizing",
                bodyStatus = "Within 20 minutes to 8 hours, your resting heart rate and blood pressure drop back to normal. Peripheral circulation starts warming your fingers and toes.",
                cravingInsight = "First peak urges are chemical signals. Drink cold water and breathe slowly — nicotine is leaving your system.",
                scientificSource = "World Health Organization (WHO) & American Heart Association"
            )
            elapsedMillis < 24 * 3600 * 1000L -> ResearchBenefitPhase(
                id = "phase_16h_24h",
                minDurationMillis = 8 * 3600 * 1000L,
                maxDurationMillis = 24 * 3600 * 1000L,
                headline = "Nicotine Flushing & Blood Oxygen Normalized",
                bodyStatus = "At 16 to 24 hours, carbon monoxide levels in your blood drop to normal non-smoker levels. Blood oxygen levels surge, and nicotine in your bloodstream drops by over 90%.",
                cravingInsight = "Your body is clearing the chemical poison right now. The urge is proof that the addiction is starving.",
                scientificSource = "British Medical Journal & Centers for Disease Control (CDC)"
            )
            elapsedMillis < 72 * 3600 * 1000L -> ResearchBenefitPhase(
                id = "phase_48h_72h",
                minDurationMillis = 24 * 3600 * 1000L,
                maxDurationMillis = 72 * 3600 * 1000L,
                headline = "Nerve Endings Regrowing & Taste/Smell Sharpening",
                bodyStatus = "At 48 to 72 hours, damaged nerve endings in your mouth and nose begin regenerating. Bronchial tubes relax, increasing lung volume and airflow.",
                cravingInsight = "Day 2 and Day 3 are the peak physical withdrawal window. Once you cross 72 hours, 100% of nicotine is eliminated from your tissues!",
                scientificSource = "American Cancer Society & Cochrane Tobacco Addiction Group"
            )
            elapsedMillis < 7 * 24 * 3600 * 1000L -> ResearchBenefitPhase(
                id = "phase_week1",
                minDurationMillis = 72 * 3600 * 1000L,
                maxDurationMillis = 7 * 24 * 3600 * 1000L,
                headline = "Chemical Peak Broken & Circulation Surging",
                bodyStatus = "You have conquered the chemical withdrawal peak. Your blood is carrying clean oxygen to vital organs, reducing cardiac strain.",
                cravingInsight = "The physical addiction is essentially broken. Remaining urges are psychological habit loops (after meals, with coffee). Re-route the habit!",
                scientificSource = "National Institutes of Health (NIH)"
            )
            elapsedMillis < 15 * 24 * 3600 * 1000L -> ResearchBenefitPhase(
                id = "phase_day15_target",
                minDurationMillis = 7 * 24 * 3600 * 1000L,
                maxDurationMillis = 15 * 24 * 3600 * 1000L,
                headline = "The 15-Day Neurochemical Receptor Reset in Progress",
                bodyStatus = "Your brain's extra nicotinic acetylcholine receptors are down-regulating. Day by day, your brain's baseline chemistry is returning to that of a non-smoker.",
                cravingInsight = "Hold on until Day 15! Research shows that by Day 15, physical urge frequency drops by up to 80%. You are almost at the promised land.",
                scientificSource = "Journal of Neuroscience & Mayo Clinic Addiction Research"
            )
            else -> ResearchBenefitPhase(
                id = "phase_post15",
                minDurationMillis = 15 * 24 * 3600 * 1000L,
                maxDurationMillis = Long.MAX_VALUE,
                headline = "15-Day Craving Cliff Achieved! Airway Cilia Cleansing",
                bodyStatus = "You have crossed the 15-day craving cliff! Microscopic cilia in your lungs have regrown and are actively sweeping old mucus and tar deposits outward.",
                cravingInsight = "Urges are now rare and brief. Your neurochemistry has largely reset. Keep guarding your freedom one day at a time.",
                scientificSource = "Harvard Medical School & World Health Organization"
            )
        }
    }

    val DISEASE_BENEFITS = listOf(
        DiseaseBenefit(
            condition = "High Blood Pressure",
            title = "Arterial De-Stiffening & BP Drop",
            clinicalEvidence = "Smoking triggers acute vasoconstriction and arterial stiffening. Within 20 minutes, resting BP drops; within 21 days, mean arterial pressure decreases by 4–9 mmHg, reducing stroke risk by 28%.",
            recoveryTimeline = "Starts in 20 min • Peak improvement in 3 weeks",
            source = "American Heart Association & The Lancet Cardiology",
            badgeColorHex = "#FF7597"
        ),
        DiseaseBenefit(
            condition = "Type 2 Diabetes",
            title = "Insulin Sensitivity Restoration",
            clinicalEvidence = "Nicotine elevates cortisol and directly causes peripheral insulin resistance. Cessation restores cellular insulin responsiveness within 8–12 weeks, stabilizing fasting blood glucose and HbA1c.",
            recoveryTimeline = "Glycemic stability improves in 60–90 days",
            source = "American Diabetes Association (ADA) Clinical Guidelines",
            badgeColorHex = "#00E599"
        ),
        DiseaseBenefit(
            condition = "Asthma / Respiratory",
            title = "Bronchial Cilia Regrowth & Airway Clearance",
            clinicalEvidence = "Chronic smoke paralyzes the pulmonary mucociliary escalator. Stopping allows full cilia regeneration within 1–3 months, cutting coughing spells and bronchial wheezing by over 60%.",
            recoveryTimeline = "Airway dilation in 72h • Mucus clearance in 30 days",
            source = "American Thoracic Society & CHEST Journal",
            badgeColorHex = "#38BDF8"
        ),
        DiseaseBenefit(
            condition = "Heart Disease / Cholesterol",
            title = "Thrombosis & Plaque Stabilization",
            clinicalEvidence = "Smoking hyper-activates platelets, making blood clot rapidly inside coronary arteries. Within 24 hours of quitting, platelet aggregation drops to normal; at 1 year, coronary heart disease risk drops by 50%.",
            recoveryTimeline = "Clot risk plunges in 24h • 50% risk drop at 1 year",
            source = "European Heart Journal & World Heart Federation",
            badgeColorHex = "#F43F5E"
        ),
        DiseaseBenefit(
            condition = "General Health & Metabolism",
            title = "Metabolic & Aerobic Capacity Rebound",
            clinicalEvidence = "Carbon monoxide displaces oxygen in hemoglobin. With carbon monoxide gone in 24 hours, VO2 max and aerobic workout endurance increase by 15–20% within 2 weeks, preventing sluggish metabolism.",
            recoveryTimeline = "Blood oxygen normal in 24h • Endurance surges in 14 days",
            source = "Mayo Clinic Sports Medicine & CDC",
            badgeColorHex = "#FBBF24"
        )
    )

    fun getBenefitsForConditions(conditions: Set<String>): List<DiseaseBenefit> {
        val matches = DISEASE_BENEFITS.filter { conditions.contains(it.condition) }
        return if (matches.isNotEmpty()) matches else listOf(DISEASE_BENEFITS.last())
    }
}

data class DiseaseBenefit(
    val condition: String,
    val title: String,
    val clinicalEvidence: String,
    val recoveryTimeline: String,
    val source: String,
    val badgeColorHex: String
)
