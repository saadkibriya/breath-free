package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.TrendingDown
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.JourneyProfile
import com.example.ui.theme.PixelDarkCardBorder
import com.example.ui.theme.PixelDarkSurface
import com.example.ui.theme.PixelDarkSurfaceVariant
import com.example.ui.theme.PixelMint
import com.example.ui.theme.PixelMintLight
import com.example.ui.theme.PixelPink
import com.example.ui.theme.PixelPinkContainer
import com.example.ui.theme.PixelPinkSoft
import com.example.ui.theme.PixelPinkVibrant
import kotlin.math.min

@Composable
fun ExpectedTargetDaysCard(
    profile: JourneyProfile,
    currentTime: Long,
    onOpenSlipSupport: () -> Unit,
    modifier: Modifier = Modifier
) {
    val elapsedDays = profile.days(currentTime)
    val targetDays = 15L // 15-day neurochemical target cliff
    val remainingDays = (targetDays - elapsedDays).coerceAtLeast(0L)
    val progress = min(1f, elapsedDays.toFloat() / targetDays.toFloat())
    val percent = (progress * 100).toInt()

    var showWindowExplanation by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        PixelDarkSurfaceVariant,
                        PixelDarkSurface,
                        Color(0xFF16101D)
                    )
                )
            )
            .border(1.5.dp, PixelDarkCardBorder, RoundedCornerShape(32.dp))
            .padding(20.dp)
            .testTag("expected_target_days_card")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(PixelPinkContainer)
                            .border(1.dp, PixelPink.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Flag,
                            contentDescription = null,
                            tint = PixelPink,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = "TARGETED GOAL",
                            color = PixelPinkSoft,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                        Text(
                            text = "15-Day Craving Peak",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                // Days Remaining Pill Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(PixelPink.copy(alpha = 0.15f))
                        .border(1.dp, PixelPink.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = if (remainingDays == 0L) "Target Reached! 🎉" else "$remainingDays days to go",
                        color = PixelPinkSoft,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Stat Row: Day X of 15 + Percentage
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "Day ${min(elapsedDays + 1, targetDays)}",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "of 15 days",
                            color = Color(0xFFC0B5C6),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 3.dp)
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.TrendingDown,
                        contentDescription = null,
                        tint = PixelMintLight,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$percent% reset",
                        color = PixelMintLight,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Progress Bar
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = PixelMint,
                trackColor = Color(0xFF281F30)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Research reassurance
            Text(
                text = if (elapsedDays >= 15L) {
                    "🏆 Phenomenal victory! You crossed the 15-day neurochemical cliff. Your nicotine acetylcholine receptors have down-regulated. The hardest physiological hurdle is behind you!"
                } else {
                    "Hold on till Day 15! Science shows physical cravings peak early and drop drastically by day 15 as brain receptors normalize. Lifetime feels scary, but 15 days is totally doable."
                },
                color = Color(0xFFE2DCE6),
                fontSize = 12.sp,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Compassionate Smoke Window / Stumble Buffer Trigger
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1E1524))
                    .border(1.dp, PixelDarkCardBorder, RoundedCornerShape(16.dp))
                    .clickable { showWindowExplanation = !showWindowExplanation }
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = PixelPinkSoft,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Emergency Window & Compassionate Recovery",
                            color = PixelPinkSoft,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = if (showWindowExplanation) "Hide" else "Learn More",
                        color = PixelMintLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (showWindowExplanation) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(PixelPinkContainer.copy(alpha = 0.5f))
                        .border(1.dp, PixelPink.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Text(
                            text = "💡 The 'Compassionate Target' Principle:",
                            color = PixelPinkSoft,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "A strict lifetime target can feel so high-pressure that one slip feels like total failure. Here, we know it's not a one-time battle — it's who keeps fighting.\n\n" +
                                    "If an urge feels suffocating, take a 10-minute grace window. If you stumble, you haven't destroyed your lung recovery. Don't quit quitting!",
                            color = Color(0xFFF3EDF5),
                            fontSize = 11.sp,
                            lineHeight = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(PixelPinkVibrant)
                                .clickable { onOpenSlipSupport() }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "Open Slip Support Protocol",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
