package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.JourneyProfile
import com.example.model.ResearchBenefitsData
import com.example.model.Trophy
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldTertiary

data class LifestyleBenefit(
    val title: String,
    val category: String,
    val icon: ImageVector,
    val description: String,
    val requiredDurationMillis: Long
)

val LIFESTYLE_BENEFITS = listOf(
    LifestyleBenefit(
        title = "Clean Breath & Fresh Clothes",
        category = "Lifestyle",
        icon = Icons.Default.Spa,
        description = "Lingering stale cigarette smoke is completely gone from your hair, skin, car, and clothes.",
        requiredDurationMillis = 24 * 3600 * 1000L
    ),
    LifestyleBenefit(
        title = "Sharpened Taste & Smell",
        category = "Senses",
        icon = Icons.Default.Restaurant,
        description = "Foods taste richer, spices pop with flavor, and subtle aromas are recognized again.",
        requiredDurationMillis = 48 * 3600 * 1000L
    ),
    LifestyleBenefit(
        title = "Easier Breathing on Stairs",
        category = "Fitness",
        icon = Icons.Default.DirectionsRun,
        description = "Bronchial airways relax and expand, delivering noticeably more oxygen on stairs and hills.",
        requiredDurationMillis = 72 * 3600 * 1000L
    ),
    LifestyleBenefit(
        title = "Deeper, Unbroken Sleep",
        category = "Wellness",
        icon = Icons.Default.SelfImprovement,
        description = "Without nighttime nicotine withdrawal, you experience uninterrupted REM cycles and wake rested.",
        requiredDurationMillis = 5 * 24 * 3600 * 1000L
    ),
    LifestyleBenefit(
        title = "Whiter Teeth & Healthy Gums",
        category = "Appearance",
        icon = Icons.Default.Shield,
        description = "Gum blood circulation normalizes, cutting infection risk and preventing new tobacco stains.",
        requiredDurationMillis = 7 * 24 * 3600 * 1000L
    ),
    LifestyleBenefit(
        title = "Natural Energy (No Nicotine Crash)",
        category = "Energy",
        icon = Icons.Default.FitnessCenter,
        description = "Blood oxygen peaks at healthy levels. You no longer suffer hourly energy dips.",
        requiredDurationMillis = 14 * 24 * 3600 * 1000L
    ),
    LifestyleBenefit(
        title = "Radiant Skin Tone",
        category = "Appearance",
        icon = Icons.Default.Eco,
        description = "Capillaries bring hydration and nutrients back to facial skin, restoring healthy color.",
        requiredDurationMillis = 21 * 24 * 3600 * 1000L
    ),
    LifestyleBenefit(
        title = "Reduced Daily Stress & Anxiety",
        category = "Mind",
        icon = Icons.Default.Psychology,
        description = "Smoking actually creates anxiety between doses. True baseline tranquility returns.",
        requiredDurationMillis = 30 * 24 * 3600 * 1000L
    ),
    LifestyleBenefit(
        title = "Compound Wealth & Freedom",
        category = "Finance",
        icon = Icons.Default.MonetizationOn,
        description = "Thousands of dollars preserved annually for travel, hobbies, family, and investments.",
        requiredDurationMillis = 60 * 24 * 3600 * 1000L
    )
)

enum class BenefitsViewMode { RESEARCH_AND_15DAY, PERKS, TROPHIES }

@Composable
fun BenefitsScreen(
    profile: JourneyProfile,
    cravingsResistedCount: Int,
    currentTime: Long,
    onOpenSetQuitDateTime: () -> Unit,
    onOpenSlipSupport: () -> Unit,
    onSelectTrophy: (Trophy) -> Unit
) {
    val elapsed = profile.elapsedMillis(currentTime)
    val days = profile.days(currentTime)
    val hours = profile.hours(currentTime)
    val trophies = Trophy.ALL_TROPHIES
    val unlockedTrophiesCount = trophies.count { it.isUnlocked(profile, cravingsResistedCount, currentTime) }

    var viewMode by remember { mutableStateOf(BenefitsViewMode.RESEARCH_AND_15DAY) }

    val currentPhase = ResearchBenefitsData.getResearchPhase(elapsed)

    // 15-Day Milestone Target Calculation
    val fifteenDaysMillis = 15L * 24 * 3600 * 1000L
    val target15Progress = (elapsed.toFloat() / fifteenDaysMillis.toFloat()).coerceIn(0f, 1f)
    val is15DaysAchieved = elapsed >= fifteenDaysMillis
    val remainingDaysTo15 = ((fifteenDaysMillis - elapsed) / (24 * 3600 * 1000L)).coerceAtLeast(0)
    val remainingHoursTo15 = (((fifteenDaysMillis - elapsed) / (3600 * 1000L)) % 24).coerceAtLeast(0)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("benefits_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF3B2A08)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = GoldLight,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Research & Benefits",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Scientific recovery timeline & 15-day target",
                        color = Color(0xFF94A3B8),
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 1. THE 15-DAY NEUROCHEMICAL RECEPTOR TARGET HERO CARD
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF0C382D),
                                Color(0xFF08271F),
                                Color(0xFF041712)
                            )
                        )
                    )
                    .border(1.5.dp, Color(0xFF1E5B4B), RoundedCornerShape(24.dp))
                    .padding(20.dp)
                    .testTag("fifteen_day_target_hero")
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Stars,
                                contentDescription = null,
                                tint = EmeraldLight,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "TARGET 1: THE 15-DAY CRAVING CLIFF",
                                color = EmeraldLight,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF134236))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (is15DaysAchieved) "RECEPTOR CLIFF MET ✓" else "Day ${days + 1} of 15",
                                color = if (is15DaysAchieved) EmeraldLight else Color(0xFFFDE68A),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = if (is15DaysAchieved) {
                            "15 Days Achieved! Physical Addiction Broken"
                        } else {
                            "Hold on until Day 15!"
                        },
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = if (is15DaysAchieved) {
                            "Neuroscience shows your nicotinic receptors have down-regulated to non-smoker levels. Cravings are now 80% weaker and purely psychological habit memories."
                        } else {
                            "A lifetime target feels impossible. You don't have to quit forever right now — just reach Day 15! Research shows by Day 15, your brain's nicotinic receptors drop significantly, making cravings vastly easier."
                        },
                        color = Color(0xFFCBD5E1),
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LinearProgressIndicator(
                        progress = { target15Progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = EmeraldPrimary,
                        trackColor = Color(0xFF13362C)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (is15DaysAchieved) "Day 15 Reached!" else "${(target15Progress * 100).toInt()}% completed",
                            color = Color(0xFFA7F3D0),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = if (is15DaysAchieved) "Physical Peak Conquered" else "${remainingDaysTo15}d ${remainingHoursTo15}h remaining",
                            color = Color(0xFFFDE68A),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 2. View Mode Tabs: Research & 15-Day vs Perks vs Trophies
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ViewModeButton(
                    label = "Research Status",
                    isSelected = viewMode == BenefitsViewMode.RESEARCH_AND_15DAY,
                    onClick = { viewMode = BenefitsViewMode.RESEARCH_AND_15DAY },
                    modifier = Modifier.weight(1.2f)
                )

                ViewModeButton(
                    label = "Perks",
                    isSelected = viewMode == BenefitsViewMode.PERKS,
                    onClick = { viewMode = BenefitsViewMode.PERKS },
                    modifier = Modifier.weight(0.9f)
                )

                ViewModeButton(
                    label = "Trophies ($unlockedTrophiesCount)",
                    isSelected = viewMode == BenefitsViewMode.TROPHIES,
                    onClick = { viewMode = BenefitsViewMode.TROPHIES },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
        }

        if (viewMode == BenefitsViewMode.RESEARCH_AND_15DAY) {
            // Live Research-Backed Biological Status
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("research_biological_card"),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F2620)),
                    shape = RoundedCornerShape(18.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1D5445))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "YOUR BODY AT ${days}D ${hours}H SMOKE-FREE",
                                color = EmeraldLight,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.6.sp
                            )

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF143B30))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "Peer-Reviewed",
                                    color = Color(0xFFA7F3D0),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = currentPhase.headline,
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = currentPhase.bodyStatus,
                            color = Color(0xFFCBD5E1),
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Craving insight callout
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF091C17))
                                .border(1.dp, Color(0xFF164437), RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Column {
                                Text(
                                    text = "💡 INSIGHT ON YOUR CRAVINGS RIGHT NOW",
                                    color = GoldLight,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = currentPhase.cravingInsight,
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    lineHeight = 16.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Source: ${currentPhase.scientificSource}",
                            color = Color(0xFF64748B),
                            fontSize = 10.sp
                        )
                    }
                }
            }

            // Compassionate Grace Window Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("grace_window_card"),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF221706)),
                    shape = RoundedCornerShape(18.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF5A3909))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = null,
                                tint = GoldLight,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "THE COMPASSIONATE GRACE PROTOCOL",
                                color = GoldLight,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.6.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Don't choke on perfection. A slip is not defeat.",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Rigid rules often cause people to surrender at the first sign of struggle. If you have an overwhelming urge or had a momentary slip, the war is NOT lost. Your body has already healed thousands of cells.",
                            color = Color(0xFFE2E8F0),
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = onOpenSlipSupport,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GoldTertiary,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Open Grace Room / Refocus After a Slip", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }

            // Warrior Motivational Quotes Section
            item {
                Text(
                    text = "RESILIENCE & WARRIOR WISDOM",
                    color = EmeraldLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            items(ResearchBenefitsData.QUOTES, key = { it.quote }) { quoteItem ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0D251F)),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1C4D3E))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.FormatQuote,
                            contentDescription = null,
                            tint = EmeraldLight,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "\"${quoteItem.quote}\"",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                lineHeight = 18.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "— ${quoteItem.author} (${quoteItem.context})",
                                color = Color(0xFFA7F3D0),
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        } else if (viewMode == BenefitsViewMode.PERKS) {
            // Lifestyle perks
            items(LIFESTYLE_BENEFITS, key = { it.title }) { benefit ->
                val isUnlocked = elapsed >= benefit.requiredDurationMillis
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isUnlocked) Color(0xFF0F2B23) else Color(0xFF0C1F1A)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isUnlocked) Color(0xFF1E5B4B) else Color(0xFF163C32)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(if (isUnlocked) Color(0xFF134236) else Color(0xFF122C25)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = benefit.icon,
                                contentDescription = null,
                                tint = if (isUnlocked) EmeraldLight else Color(0xFF64748B),
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = benefit.title,
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                if (isUnlocked) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Unlocked",
                                        tint = EmeraldLight,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = benefit.description,
                                color = Color(0xFFCBD5E1),
                                fontSize = 12.sp,
                                lineHeight = 17.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isUnlocked) Color(0xFF144537) else Color(0xFF132D26))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                val statusText = if (isUnlocked) {
                                    "Benefit active right now ✓"
                                } else {
                                    val remaining = benefit.requiredDurationMillis - elapsed
                                    val remDays = remaining / (24 * 3600 * 1000L)
                                    val remHours = (remaining / (3600 * 1000L)) % 24
                                    "Unlocks in ${if (remDays > 0) "${remDays}d " else ""}${remHours}h"
                                }

                                Text(
                                    text = statusText,
                                    color = if (isUnlocked) EmeraldLight else Color(0xFF94A3B8),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        } else {
            // Trophies list
            items(trophies, key = { it.id }) { trophy ->
                val isUnlocked = trophy.isUnlocked(profile, cravingsResistedCount, currentTime)
                val progress = trophy.progress(profile, cravingsResistedCount, currentTime)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectTrophy(trophy) }
                        .testTag("trophy_item_${trophy.id}"),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isUnlocked) Color(0xFF261905) else Color(0xFF120E05)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isUnlocked) Color(0xFF6B450C) else Color(0xFF2E200B)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(if (isUnlocked) Color(0xFF4C3007) else Color(0xFF1F1607)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isUnlocked) Icons.Default.EmojiEvents else Icons.Default.Lock,
                                contentDescription = null,
                                tint = if (isUnlocked) GoldLight else Color(0xFF785117),
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = trophy.title,
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = trophy.tier.label.uppercase(),
                                    color = Color(trophy.tier.colorHex),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = trophy.description,
                                color = Color(0xFFCBD5E1),
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = trophy.requirementText,
                                    color = Color(0xFF94A3B8),
                                    fontSize = 11.sp
                                )

                                Text(
                                    text = if (isUnlocked) "UNLOCKED ✓" else "${(progress * 100).toInt()}%",
                                    color = if (isUnlocked) EmeraldLight else GoldLight,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp)),
                                color = if (isUnlocked) GoldLight else GoldTertiary,
                                trackColor = Color(0xFF2D1B05)
                            )
                        }
                    }
                }
            }
        }

        // Bottom section: Set Quit Date / Time & Reset Button
        item {
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("benefits_date_reset_card"),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0B211A)),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1A4E40))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "MANAGE QUIT TIMELINE",
                        color = EmeraldLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Need to adjust the exact date and time you stopped smoking or reset after a slip?",
                        color = Color(0xFFCBD5E1),
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onOpenSetQuitDateTime,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF13382D),
                            contentColor = EmeraldLight
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Select Date & Time / Reset Journey", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(88.dp))
        }
    }
}

@Composable
private fun ViewModeButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) EmeraldPrimary else Color(0xFF102E26))
            .border(1.dp, if (isSelected) EmeraldLight else Color(0xFF1E4E40), RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = if (isSelected) Color.Black else Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
