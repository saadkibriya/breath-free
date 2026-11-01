package com.example.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Stars
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.JourneyProfile
import com.example.model.Trophy
import com.example.ui.theme.PixelBayBlue
import com.example.ui.theme.PixelCoral
import com.example.ui.theme.PixelDarkBackground
import com.example.ui.theme.PixelDarkCardBorder
import com.example.ui.theme.PixelDarkSurface
import com.example.ui.theme.PixelDarkSurfaceVariant
import com.example.ui.theme.PixelLimoncello
import com.example.ui.theme.PixelMint
import com.example.ui.theme.PixelMintLight
import com.example.ui.theme.PixelPink
import com.example.ui.theme.PixelPinkContainer
import com.example.ui.theme.PixelPinkSoft

data class BiologicalMilestone(
    val id: String,
    val title: String,
    val hoursRequired: Long,
    val description: String,
    val bodyEffect: String
)

object BiologicalMilestonesData {
    val MILESTONES = listOf(
        BiologicalMilestone(
            id = "m_8h",
            title = "8 Hours",
            hoursRequired = 8,
            description = "Carbon monoxide levels in blood drop by 50%",
            bodyEffect = "Blood oxygen returns to normal. Brain gets crisp circulation."
        ),
        BiologicalMilestone(
            id = "m_24h",
            title = "24 Hours",
            hoursRequired = 24,
            description = "Carbon monoxide completely cleared from your system",
            bodyEffect = "Lungs start coughing out cellular debris and old mucus."
        ),
        BiologicalMilestone(
            id = "m_48h",
            title = "48 Hours",
            hoursRequired = 48,
            description = "100% of nicotine is physically eliminated from the body",
            bodyEffect = "Nerve endings begin regrowing. Taste and smell intensify dramatically."
        ),
        BiologicalMilestone(
            id = "m_72h",
            title = "72 Hours",
            hoursRequired = 72,
            description = "Bronchial breathing tubes relax; lung capacity jumps 10%",
            bodyEffect = "Physical withdrawal peak has passed. From here, cravings decline."
        ),
        BiologicalMilestone(
            id = "m_15d",
            title = "15 Days",
            hoursRequired = 15 * 24,
            description = "Nicotine acetylcholine brain receptors drop down by 80%",
            bodyEffect = "Cravings become occasional background thoughts instead of physical urgencies."
        ),
        BiologicalMilestone(
            id = "m_30d",
            title = "1 Month",
            hoursRequired = 30 * 24,
            description = "Lung cilia regenerated; overall energy and circulation restored",
            bodyEffect = "Sinus congestion and shortness of breath almost completely disappear."
        )
    )
}

@Composable
fun RecoveryMilestonesSection(
    profile: JourneyProfile,
    currentTime: Long,
    overcomeCount: Int,
    onSelectTrophy: (Trophy) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) } // 0 = Biological Milestones, 1 = Trophies
    val elapsedHours = (profile.elapsedMillis(currentTime) / (3600 * 1000L)).coerceAtLeast(0)
    val allTrophies = remember { Trophy.ALL_TROPHIES }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .border(1.dp, PixelDarkCardBorder, RoundedCornerShape(24.dp))
            .animateContentSize(animationSpec = tween(250)),
        colors = CardDefaults.cardColors(containerColor = PixelDarkSurface)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Header with Pixel Toggle Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(PixelMint.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (selectedTab == 0) Icons.Default.Schedule else Icons.Default.EmojiEvents,
                            contentDescription = null,
                            tint = if (selectedTab == 0) PixelMint else PixelLimoncello,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (selectedTab == 0) "Real Body Milestones" else "Freedom Trophies",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (selectedTab == 0) "Science-verified timeline" else "Unlockable game badges",
                            color = Color(0xFFA59AA8),
                            fontSize = 11.sp
                        )
                    }
                }

                // Pixel Sub-tab selector pill
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(PixelDarkSurfaceVariant)
                        .padding(3.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (selectedTab == 0) PixelMint else Color.Transparent)
                            .clickable { selectedTab = 0 }
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "Timeline",
                            color = if (selectedTab == 0) Color.Black else Color(0xFFC0BAC6),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (selectedTab == 1) PixelLimoncello else Color.Transparent)
                            .clickable { selectedTab = 1 }
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "Trophies",
                            color = if (selectedTab == 1) Color.Black else Color(0xFFC0BAC6),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            if (selectedTab == 0) {
                // Biological Timeline Carousel
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    BiologicalMilestonesData.MILESTONES.forEach { milestone ->
                        val isAchieved = elapsedHours >= milestone.hoursRequired
                        val progress = (elapsedHours.toFloat() / milestone.hoursRequired.toFloat()).coerceIn(0f, 1f)

                        Box(
                            modifier = Modifier
                                .width(220.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .background(PixelDarkBackground)
                                .border(
                                    1.dp,
                                    if (isAchieved) PixelMint.copy(alpha = 0.5f) else PixelDarkCardBorder,
                                    RoundedCornerShape(18.dp)
                                )
                                .padding(14.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = milestone.title,
                                        color = if (isAchieved) PixelMintLight else Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )

                                    if (isAchieved) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(PixelMint.copy(alpha = 0.2f))
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = Icons.Default.CheckCircle,
                                                    contentDescription = null,
                                                    tint = PixelMintLight,
                                                    modifier = Modifier.size(11.dp)
                                                )
                                                Spacer(modifier = Modifier.width(3.dp))
                                                Text(
                                                    text = "Achieved",
                                                    color = PixelMintLight,
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    } else {
                                        Text(
                                            text = "${(progress * 100).toInt()}%",
                                            color = Color(0xFFA59AA8),
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                LinearProgressIndicator(
                                    progress = { progress },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(4.dp)
                                        .clip(RoundedCornerShape(2.dp)),
                                    color = PixelMint,
                                    trackColor = Color(0xFF2C2434)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = milestone.description,
                                    color = Color(0xFFE2DCE6),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    lineHeight = 16.sp
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = milestone.bodyEffect,
                                    color = Color(0xFF9F93A3),
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }
                }
            } else {
                // Trophies Badges Horizontal Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    allTrophies.forEach { trophy ->
                        val isUnlocked = trophy.isUnlocked(profile, overcomeCount, currentTime)
                        val progress = trophy.progress(profile, overcomeCount, currentTime)

                        Box(
                            modifier = Modifier
                                .width(180.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .background(PixelDarkBackground)
                                .border(
                                    1.dp,
                                    if (isUnlocked) PixelLimoncello.copy(alpha = 0.6f) else PixelDarkCardBorder,
                                    RoundedCornerShape(18.dp)
                                )
                                .clickable { onSelectTrophy(trophy) }
                                .padding(12.dp)
                                .testTag("trophy_item_${trophy.id}")
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isUnlocked) PixelLimoncello.copy(alpha = 0.2f)
                                            else PixelDarkSurfaceVariant
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (isUnlocked) Icons.Default.EmojiEvents else Icons.Default.Lock,
                                        contentDescription = null,
                                        tint = if (isUnlocked) PixelLimoncello else Color(0xFF6B6072),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = trophy.title,
                                    color = if (isUnlocked) Color.White else Color(0xFF9A8E9E),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = trophy.requirementText,
                                    color = Color(0xFFA59AA8),
                                    fontSize = 10.sp,
                                    maxLines = 1
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                LinearProgressIndicator(
                                    progress = { progress },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(4.dp)
                                        .clip(RoundedCornerShape(2.dp)),
                                    color = if (isUnlocked) PixelLimoncello else PixelPink,
                                    trackColor = Color(0xFF2C2434)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
