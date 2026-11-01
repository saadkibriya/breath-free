package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldTertiary

@Composable
fun SavingsScreen(
    profile: JourneyProfile,
    currentTime: Long,
    onOpenEditJourney: () -> Unit
) {
    val dailySaved = profile.dailyCost
    val totalSaved = profile.totalMoneySaved(currentTime)
    val goalProgress = if (profile.goalCost > 0) (totalSaved / profile.goalCost).toFloat().coerceIn(0f, 1f) else 1f
    val goalPercent = (goalProgress * 100).toInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("savings_screen")
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF382305)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocalAtm,
                    contentDescription = null,
                    tint = GoldLight,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Money Saved Daily",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Your smoke-free wealth accumulation",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hero Cumulative & Daily Saved Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF332005),
                            Color(0xFF221503),
                            Color(0xFF140D02)
                        )
                    )
                )
                .border(1.5.dp, Color(0xFF6B450C), RoundedCornerShape(24.dp))
                .padding(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TOTAL SAVED TO DATE",
                        color = GoldLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF4A2F07))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${profile.formatCurrency(dailySaved)} / day",
                            color = Color(0xFFFDE68A),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = profile.formatCurrency(totalSaved),
                    color = Color.White,
                    fontSize = 38.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = "Based on ${profile.cigarettesPerDay} cigs/day at ${profile.formatCurrency(profile.pricePerPack)} per pack",
                    color = Color(0xFFE2E8F0),
                    fontSize = 11.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Primary Goal status
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF261905))
                        .border(1.dp, Color(0xFF4E3309), RoundedCornerShape(14.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Stars, contentDescription = null, tint = GoldLight, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = profile.goalTitle, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(
                            text = if (goalProgress >= 1f) "UNLOCKED!" else "$goalPercent%",
                            color = if (goalProgress >= 1f) EmeraldLight else GoldLight,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { goalProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = if (goalProgress >= 1f) EmeraldLight else GoldTertiary,
                        trackColor = Color(0xFF3F2706)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "${profile.formatCurrency(totalSaved)} saved", color = Color(0xFF94A3B8), fontSize = 10.sp)
                        Text(text = "Target ${profile.formatCurrency(profile.goalCost)}", color = Color(0xFF94A3B8), fontSize = 10.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Projected Savings Horizons
        Text(
            text = "FUTURE SAVINGS HORIZONS",
            color = GoldLight,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.8.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SavingsHorizonRow(period = "1 Week", amount = profile.formatCurrency(dailySaved * 7))
            SavingsHorizonRow(period = "1 Month", amount = profile.formatCurrency(dailySaved * 30))
            SavingsHorizonRow(period = "3 Months", amount = profile.formatCurrency(dailySaved * 90))
            SavingsHorizonRow(period = "1 Year", amount = profile.formatCurrency(dailySaved * 365))
            SavingsHorizonRow(period = "5 Years", amount = profile.formatCurrency(dailySaved * 365 * 5), isHighlight = true)
            SavingsHorizonRow(period = "10 Years", amount = profile.formatCurrency(dailySaved * 365 * 10), isHighlight = true)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // What You Can Buy Equivalents
        Text(
            text = "REAL-WORLD EQUIVALENCIES",
            color = EmeraldLight,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.8.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        val coffeeCount = (totalSaved / 4.50).toInt()
        val dinnerCount = (totalSaved / 45.0).toInt()
        val flightsCount = (totalSaved / 250.0).toInt()

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            EquivalenceCard(
                icon = Icons.Default.Coffee,
                title = "$coffeeCount Artisanal Coffees",
                desc = "Equivalent to $coffeeCount fresh gourmet morning coffees not burned into smoke."
            )
            EquivalenceCard(
                icon = Icons.Default.Restaurant,
                title = "$dinnerCount Fine Dining Dinners",
                desc = "Enough for $dinnerCount memorable nights out with your loved ones."
            )
            EquivalenceCard(
                icon = Icons.Default.Flight,
                title = if (flightsCount > 0) "$flightsCount Vacation Flights" else "On track for a flight ticket!",
                desc = "Saving ${profile.formatCurrency(dailySaved * 30)}/month effortlessly funds your dream trips."
            )
        }
    }
}

@Composable
private fun SavingsHorizonRow(
    period: String,
    amount: String,
    isHighlight: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isHighlight) Color(0xFF2E1C05) else Color(0xFF161108))
            .border(1.dp, if (isHighlight) Color(0xFF6B450C) else Color(0xFF2E200B), RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = period,
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = amount,
            color = if (isHighlight) GoldLight else Color(0xFFFDE68A),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun EquivalenceCard(
    icon: ImageVector,
    title: String,
    desc: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F2620)),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1B493D))
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF173E33)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = EmeraldLight, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(text = desc, color = Color(0xFF94A3B8), fontSize = 11.sp, lineHeight = 15.sp)
            }
        }
    }
}
