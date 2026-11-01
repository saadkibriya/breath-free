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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CravingRecord
import com.example.data.JourneyProfile
import com.example.model.Trophy
import com.example.ui.components.AiHealthProfileCard
import com.example.ui.components.DividedInfoTiles
import com.example.ui.components.ExpectedTargetDaysCard
import com.example.ui.components.HeroJourneyCard
import com.example.ui.components.RecoveryMilestonesSection
import com.example.ui.components.SmokeWindowBanner
import com.example.ui.theme.PixelDarkBackground
import com.example.ui.theme.PixelDarkCardBorder
import com.example.ui.theme.PixelDarkSurface
import com.example.ui.theme.PixelMint
import com.example.ui.theme.PixelMintLight
import com.example.ui.theme.PixelPink
import com.example.ui.theme.PixelPinkContainer
import com.example.ui.theme.PixelPinkSoft
import com.example.ui.theme.PixelPinkVibrant

@Composable
fun DashboardScreen(
    profile: JourneyProfile,
    currentTime: Long,
    cravings: List<CravingRecord>,
    overcomeCount: Int,
    onOpenEditJourney: () -> Unit,
    onOpenSetQuitDateTime: () -> Unit,
    onResetQuitDate: () -> Unit,
    onOpenSosBreathing: () -> Unit,
    onOpenSlipSupport: () -> Unit,
    onOpenLogCraving: () -> Unit,
    onOpenAiOnboarding: () -> Unit,
    onOvercameSmokeWindow: () -> Unit,
    onNavigateToStories: () -> Unit,
    onNavigateToChat: () -> Unit,
    onSelectTrophy: (Trophy) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PixelDarkBackground)
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp) // Bottom padding for floating pill dock
            .testTag("dashboard_screen")
    ) {
        // App Top Bar: Pixel Style Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(PixelPinkContainer)
                        .border(1.dp, PixelPink.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Spa,
                        contentDescription = "BreatheFree",
                        tint = PixelPink,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = "BreatheFree",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-0.3).sp
                    )
                    Text(
                        text = "Your science-backed freedom journey",
                        color = Color(0xFFA59AA8),
                        fontSize = 11.sp
                    )
                }
            }

            // Right Actions: AI Intake Chip & Urge Counter
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(PixelPinkContainer)
                        .border(1.dp, PixelPink.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                        .clickable { onOpenAiOnboarding() }
                        .padding(horizontal = 9.dp, vertical = 6.dp)
                        .testTag("top_ai_intake_button")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = PixelPink,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "AI Intake",
                            color = PixelPink,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(PixelDarkSurface)
                        .border(1.dp, PixelDarkCardBorder, RoundedCornerShape(20.dp))
                        .clickable { onOpenLogCraving() }
                        .padding(horizontal = 9.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = PixelMintLight,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "$overcomeCount",
                            color = PixelMintLight,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // ==========================================
        // TILE 1: Time Passed Hero Card
        // ==========================================
        HeroJourneyCard(
            profile = profile,
            currentTime = currentTime,
            onEditClicked = onOpenEditJourney,
            onSetQuitDateTimeClicked = onOpenSetQuitDateTime,
            onResetClicked = onResetQuitDate
        )

        Spacer(modifier = Modifier.height(14.dp))

        // ==========================================
        // SMOKE WINDOW & CRAVING WAVE DELAY ALARM
        // ==========================================
        SmokeWindowBanner(
            profile = profile,
            currentTime = currentTime,
            onOvercameWindow = onOvercameSmokeWindow
        )

        Spacer(modifier = Modifier.height(14.dp))

        // ==========================================
        // TILE 2: Expected Targeted Days Card
        // ==========================================
        ExpectedTargetDaysCard(
            profile = profile,
            currentTime = currentTime,
            onOpenSlipSupport = onOpenSlipSupport
        )

        Spacer(modifier = Modifier.height(14.dp))

        // ==========================================
        // TILE 3: Divided Side-by-Side Tiles (Bio-Marker & Latest Benefit)
        // ==========================================
        DividedInfoTiles(
            profile = profile,
            currentTime = currentTime,
            onNavigateToBenefits = onNavigateToStories
        )

        Spacer(modifier = Modifier.height(14.dp))

        // ==========================================
        // TILE 4: Merged Real Recovery Milestones & Trophies Section
        // ==========================================
        RecoveryMilestonesSection(
            profile = profile,
            currentTime = currentTime,
            overcomeCount = overcomeCount,
            onSelectTrophy = onSelectTrophy
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ==========================================
        // PERSONALIZED AI HEALTH & CESSATION BLUEPRINT
        // ==========================================
        AiHealthProfileCard(
            profile = profile,
            onRecalibrateIntake = onOpenAiOnboarding
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Fast Quick-Action Ribbon (SOS Breathing + Ask AI Buddy)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = onOpenSosBreathing,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("sos_craving_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PixelPinkVibrant,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.SelfImprovement,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "SOS CRAVING",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    letterSpacing = 0.5.sp
                )
            }

            Button(
                onClick = onNavigateToChat,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("chat_coach_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PixelDarkSurface,
                    contentColor = PixelMintLight
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = PixelMint,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Ask BreatheAI",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = PixelMintLight
                )
            }
        }
    }
}
