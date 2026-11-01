package com.example.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.Trophy
import com.example.ui.components.AiOnboardingDialog
import com.example.ui.components.EditJourneyDialog
import com.example.ui.components.LogCravingDialog
import com.example.ui.components.SetQuitDateTimeDialog
import com.example.ui.components.SlipSupportDialog
import com.example.ui.components.SosBreathingDialog
import com.example.ui.components.TrophyUnlockDialog
import com.example.ui.theme.PixelBayBlue
import com.example.ui.theme.PixelCoral
import com.example.ui.theme.PixelDarkBackground
import com.example.ui.theme.PixelDarkCardBorder
import com.example.ui.theme.PixelDarkSurface
import com.example.ui.theme.PixelLimoncello
import com.example.ui.theme.PixelMint
import com.example.ui.theme.PixelPink
import com.example.ui.theme.PixelPinkVibrant
import com.example.viewmodel.ChatViewModel
import com.example.viewmodel.JourneyViewModel

enum class AppTab(
    val icon: ImageVector,
    val description: String
) {
    HOME(Icons.Default.Home, "Home Journey"),
    STORIES(Icons.Default.Forum, "Real Ex-Smoker Tips & Reddit"),
    CHAT(Icons.Default.AutoAwesome, "Gemini AI Coach"),
    WIDGETS(Icons.Default.Widgets, "Home Screen Widgets")
}

@Composable
fun MainAppScaffold(
    viewModel: JourneyViewModel,
    chatViewModel: ChatViewModel = viewModel()
) {
    val context = LocalContext.current
    val profile by viewModel.profile.collectAsState()
    val currentTime by viewModel.currentTime.collectAsState()
    val cravings by viewModel.cravings.collectAsState()
    val overcomeCount by viewModel.cravingsOvercomeCount.collectAsState()

    var currentTab by remember { mutableStateOf(AppTab.HOME) }
    var showAiOnboardingDialog by remember { mutableStateOf(!profile.hasCompletedAiOnboarding) }
    var showEditDialog by remember { mutableStateOf(false) }
    var showSetQuitDateTimeDialog by remember { mutableStateOf(false) }
    var showSosDialog by remember { mutableStateOf(false) }
    var showLogCravingDialog by remember { mutableStateOf(false) }
    var showSlipSupportDialog by remember { mutableStateOf(false) }
    var activeTrophyToCelebrate by remember { mutableStateOf<Trophy?>(null) }

    // Check newly unlocked trophies for celebratory popups
    val trophyPrefs = remember { context.getSharedPreferences("trophy_celebration_prefs", Context.MODE_PRIVATE) }
    LaunchedEffect(currentTime, overcomeCount) {
        val allTrophies = Trophy.ALL_TROPHIES
        val seenTrophies = trophyPrefs.getStringSet("seen_trophies", emptySet()) ?: emptySet()
        val newlyUnlocked = allTrophies.firstOrNull { trophy ->
            trophy.isUnlocked(profile, overcomeCount, currentTime) && !seenTrophies.contains(trophy.id)
        }
        if (newlyUnlocked != null && activeTrophyToCelebrate == null) {
            activeTrophyToCelebrate = newlyUnlocked
            val updatedSet = seenTrophies.toMutableSet().apply { add(newlyUnlocked.id) }
            trophyPrefs.edit().putStringSet("seen_trophies", updatedSet).apply()
        }
    }

    // System back handler returns to Home if in another tab
    if (currentTab != AppTab.HOME) {
        BackHandler {
            currentTab = AppTab.HOME
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(PixelDarkBackground),
        containerColor = PixelDarkBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            // Main Content Area
            when (currentTab) {
                AppTab.HOME -> DashboardScreen(
                    profile = profile,
                    currentTime = currentTime,
                    cravings = cravings,
                    overcomeCount = overcomeCount,
                    onOpenEditJourney = { showEditDialog = true },
                    onOpenSetQuitDateTime = { showSetQuitDateTimeDialog = true },
                    onResetQuitDate = {
                        viewModel.resetQuitDate()
                        Toast.makeText(context, "Journey reset to right now. Stay strong!", Toast.LENGTH_SHORT).show()
                    },
                    onOpenSosBreathing = { showSosDialog = true },
                    onOpenSlipSupport = { showSlipSupportDialog = true },
                    onOpenLogCraving = { showLogCravingDialog = true },
                    onOpenAiOnboarding = { showAiOnboardingDialog = true },
                    onOvercameSmokeWindow = {
                        viewModel.logCraving(
                            intensity = 7,
                            trigger = "10-Min Wave Window",
                            note = "Successfully rode out the craving spike during smoke window",
                            technique = "Smoke Wave Buffer & Alarm",
                            overcome = true
                        )
                        Toast.makeText(context, "Outstanding! You proved mastery over nicotine (+1 Resisted)!", Toast.LENGTH_LONG).show()
                    },
                    onNavigateToStories = { currentTab = AppTab.STORIES },
                    onNavigateToChat = { currentTab = AppTab.CHAT },
                    onSelectTrophy = { trophy ->
                        activeTrophyToCelebrate = trophy
                    }
                )

                AppTab.STORIES -> TipsAndStoriesScreen()

                AppTab.CHAT -> GeminiChatScreen(
                    chatViewModel = chatViewModel,
                    onOpenBreathingExercise = { showSosDialog = true },
                    onLogResistedCraving = {
                        viewModel.logCraving(
                            intensity = 6,
                            trigger = "AI Distraction",
                            note = "Craving beat using BreatheAI distraction protocol",
                            technique = "Music & Urge Surfing",
                            overcome = true
                        )
                        Toast.makeText(context, "Great job! Resisted craving logged (+1)", Toast.LENGTH_SHORT).show()
                    }
                )

                AppTab.WIDGETS -> WidgetScreen(
                    profile = profile,
                    currentTime = currentTime,
                    onForceRefresh = { viewModel.syncWidgets() }
                )
            }

            // ==========================================================
            // Modern Floating Pill Navigation Bar (Icons Only, No Text)
            // ==========================================================
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(bottom = 14.dp)
                    .testTag("floating_pill_bottom_bar")
            ) {
                Box(
                    modifier = Modifier
                        .shadow(elevation = 16.dp, shape = RoundedCornerShape(36.dp), spotColor = PixelPink.copy(alpha = 0.3f))
                        .clip(RoundedCornerShape(36.dp))
                        .background(PixelDarkSurface.copy(alpha = 0.95f))
                        .border(1.5.dp, PixelDarkCardBorder, RoundedCornerShape(36.dp))
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AppTab.entries.forEach { tab ->
                            val isSelected = currentTab == tab
                            val animatedIndicatorColor by animateColorAsState(
                                targetValue = if (isSelected) {
                                    when (tab) {
                                        AppTab.HOME -> PixelMint
                                        AppTab.STORIES -> PixelCoral
                                        AppTab.CHAT -> PixelBayBlue
                                        AppTab.WIDGETS -> PixelLimoncello
                                    }
                                } else Color.Transparent,
                                animationSpec = tween(250),
                                label = "pillIndicator"
                            )

                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(animatedIndicatorColor)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        currentTab = tab
                                    }
                                    .testTag("nav_${tab.name.lowercase()}"),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tab.description,
                                    tint = if (isSelected) Color(0xFF0F0814) else Color(0xFF9E92A5),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Dialogs
            if (showSetQuitDateTimeDialog) {
                SetQuitDateTimeDialog(
                    currentQuitTimestamp = profile.quitTimestamp,
                    onDismiss = { showSetQuitDateTimeDialog = false },
                    onConfirm = { newTimestamp ->
                        viewModel.updateProfile(profile.copy(quitTimestamp = newTimestamp))
                        Toast.makeText(context, "Quit date & time updated!", Toast.LENGTH_SHORT).show()
                    },
                    onResetToNow = {
                        viewModel.resetQuitDate()
                        Toast.makeText(context, "Journey reset to right now. You've got this!", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            if (showSlipSupportDialog) {
                SlipSupportDialog(
                    onDismiss = { showSlipSupportDialog = false },
                    onKeepProgressAfterSlip = {
                        viewModel.logCraving(
                            intensity = 7,
                            trigger = "Slip / Relapse Urge",
                            note = "Temporary stumble. Progressive biological healing remains intact!",
                            technique = "Compassionate Reframing",
                            overcome = false
                        )
                        Toast.makeText(context, "Progress preserved! 1 slip does not erase days of biological repair. Stand tall!", Toast.LENGTH_LONG).show()
                    },
                    onFreshReset = {
                        viewModel.resetQuitDate()
                        Toast.makeText(context, "Fresh restart initiated. Failure is expected data on the way to permanent victory!", Toast.LENGTH_LONG).show()
                    },
                    onStart10MinGraceWindow = {
                        showSosDialog = true
                        Toast.makeText(context, "10-Minute grace window started! Breathe deeply before deciding.", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            if (showEditDialog) {
                EditJourneyDialog(
                    initialProfile = profile,
                    onDismiss = { showEditDialog = false },
                    onSave = { updated ->
                        viewModel.updateProfile(updated)
                    },
                    onResetQuitDate = {
                        viewModel.resetQuitDate()
                    }
                )
            }

            if (showSosDialog) {
                SosBreathingDialog(
                    onDismiss = { showSosDialog = false },
                    onOvercameCraving = { trigger, note ->
                        viewModel.logCraving(
                            intensity = 8,
                            trigger = trigger,
                            note = note,
                            technique = "4-7-8 Breathing",
                            overcome = true
                        )
                        Toast.makeText(context, "Great job! Craving resisted & logged (+1)", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            if (showLogCravingDialog) {
                LogCravingDialog(
                    onDismiss = { showLogCravingDialog = false },
                    onSaveCraving = { intensity, trigger, note, technique, overcome ->
                        viewModel.logCraving(
                            intensity = intensity,
                            trigger = trigger,
                            note = note,
                            technique = technique,
                            overcome = overcome
                        )
                        val msg = if (overcome) "Craving successfully resisted! (+1)" else "Craving logged."
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }
                )
            }

            // Trophy Unlock Celebratory Dialog
            activeTrophyToCelebrate?.let { trophy ->
                TrophyUnlockDialog(
                    trophy = trophy,
                    onDismiss = { activeTrophyToCelebrate = null }
                )
            }

            // AI Health Intake & Onboarding Dialog
            if (showAiOnboardingDialog) {
                AiOnboardingDialog(
                    currentProfile = profile,
                    onDismiss = { showAiOnboardingDialog = false },
                    onComplete = { updatedProfile ->
                        viewModel.updateProfile(updatedProfile)
                        showAiOnboardingDialog = false
                        Toast.makeText(context, "AI Health Blueprint personalized & saved!", Toast.LENGTH_LONG).show()
                    }
                )
            }
        }
    }
}
