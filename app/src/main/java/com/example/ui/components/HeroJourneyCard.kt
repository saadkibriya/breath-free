package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
import com.example.ui.theme.PixelSunYellow

@Composable
fun HeroJourneyCard(
    profile: JourneyProfile,
    currentTime: Long,
    onEditClicked: () -> Unit,
    onSetQuitDateTimeClicked: () -> Unit,
    onResetClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val days = profile.days(currentTime)
    val hours = profile.hours(currentTime)
    val minutes = profile.minutes(currentTime)
    val seconds = profile.seconds(currentTime)

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    // Pixel Asymmetric Squircle Container
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        PixelDarkSurfaceVariant,
                        PixelDarkSurface,
                        Color(0xFF130E18)
                    )
                )
            )
            .border(1.5.dp, PixelDarkCardBorder, RoundedCornerShape(32.dp))
            .padding(20.dp)
            .testTag("hero_journey_card")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header: Live Breathing badge + Edit settings
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(PixelMint.copy(alpha = 0.12f))
                        .border(1.dp, PixelMint.copy(alpha = 0.3f), RoundedCornerShape(24.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(9.dp)
                            .scale(pulseScale)
                            .clip(CircleShape)
                            .background(PixelMint)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "SMOKE FREE TIME PASSED",
                        color = PixelMintLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.6.sp
                    )
                }

                IconButton(
                    onClick = onEditClicked,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(PixelDarkSurface)
                        .border(1.dp, PixelDarkCardBorder, CircleShape)
                        .testTag("edit_journey_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Journey Parameters",
                        tint = PixelPinkSoft,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Main Pixel Counter Grid: Days, Hours, Mins, Secs
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PixelCounterTile(
                    value = days.toString(),
                    label = if (days == 1L) "DAY" else "DAYS",
                    color = PixelMint,
                    isHero = true,
                    modifier = Modifier.weight(1.25f)
                )

                PixelCounterTile(
                    value = String.format("%02d", hours),
                    label = "HOURS",
                    color = PixelPinkSoft,
                    modifier = Modifier.weight(1f)
                )

                PixelCounterTile(
                    value = String.format("%02d", minutes),
                    label = "MINS",
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )

                PixelCounterTile(
                    value = String.format("%02d", seconds),
                    label = "SECS",
                    color = PixelMintLight,
                    modifier = Modifier.weight(0.95f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Actions: Set Date & Time + Reset Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Set Quit Date & Time Button
                Box(
                    modifier = Modifier
                        .weight(1.3f)
                        .clip(RoundedCornerShape(20.dp))
                        .background(PixelDarkSurface)
                        .border(1.dp, PixelDarkCardBorder, RoundedCornerShape(20.dp))
                        .clickable { onSetQuitDateTimeClicked() }
                        .padding(horizontal = 12.dp, vertical = 9.dp)
                        .testTag("hero_set_date_time_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = null,
                            tint = PixelPinkSoft,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Set Quit Date & Time",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                // Reset Button
                Box(
                    modifier = Modifier
                        .weight(0.8f)
                        .clip(RoundedCornerShape(20.dp))
                        .background(PixelPinkContainer)
                        .border(1.dp, PixelPink.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                        .clickable { onResetClicked() }
                        .padding(horizontal = 10.dp, vertical = 9.dp)
                        .testTag("hero_reset_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.RestartAlt,
                            contentDescription = null,
                            tint = PixelPinkVibrant,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Reset",
                            color = PixelPinkSoft,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Savings & Avoided Quick Mini-Badges inside Card 1
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0xFF16111B))
                    .border(1.dp, PixelDarkCardBorder, RoundedCornerShape(18.dp))
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val avoided = profile.cigarettesAvoided(currentTime)
                val totalSaved = profile.totalMoneySaved(currentTime)
                val currency = profile.currencySymbol

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "🚭 $avoided avoided",
                        color = PixelMintLight,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "💰 $currency${String.format("%.2f", totalSaved)} saved",
                        color = PixelSunYellow,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun PixelCounterTile(
    value: String,
    label: String,
    color: Color,
    isHero: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(PixelDarkSurface)
            .border(1.dp, PixelDarkCardBorder, RoundedCornerShape(20.dp))
            .padding(vertical = 12.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                color = color,
                fontSize = if (isHero) 32.sp else 23.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-0.5).sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                color = Color(0xFFC0B5C6),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
        }
    }
}
