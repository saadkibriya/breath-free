package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.CoralSos
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import kotlinx.coroutines.delay

enum class BreathingPhase(val label: String, val seconds: Int, val instruction: String) {
    INHALE("Breathe In", 4, "Inhale deeply through your nose..."),
    HOLD("Hold Breath", 7, "Hold gently, keeping your chest open..."),
    EXHALE("Exhale Slowly", 8, "Release steadily through your mouth...")
}

@Composable
fun SosBreathingDialog(
    onDismiss: () -> Unit,
    onOvercameCraving: (trigger: String, note: String) -> Unit
) {
    var currentPhase by remember { mutableStateOf(BreathingPhase.INHALE) }
    var secondsLeft by remember { mutableIntStateOf(4) }
    var cycleCount by remember { mutableIntStateOf(1) }
    var isRunning by remember { mutableStateOf(true) }
    val circleScale = remember { Animatable(0.7f) }

    LaunchedEffect(isRunning, currentPhase) {
        if (!isRunning) return@LaunchedEffect

        when (currentPhase) {
            BreathingPhase.INHALE -> {
                circleScale.animateTo(
                    targetValue = 1.3f,
                    animationSpec = tween(durationMillis = 4000, easing = LinearEasing)
                )
            }
            BreathingPhase.HOLD -> {
                // Keep steady at expanded size
            }
            BreathingPhase.EXHALE -> {
                circleScale.animateTo(
                    targetValue = 0.7f,
                    animationSpec = tween(durationMillis = 8000, easing = LinearEasing)
                )
            }
        }
    }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            // INHALE (4s)
            currentPhase = BreathingPhase.INHALE
            for (i in 4 downTo 1) {
                secondsLeft = i
                delay(1000L)
            }

            // HOLD (7s)
            currentPhase = BreathingPhase.HOLD
            for (i in 7 downTo 1) {
                secondsLeft = i
                delay(1000L)
            }

            // EXHALE (8s)
            currentPhase = BreathingPhase.EXHALE
            for (i in 8 downTo 1) {
                secondsLeft = i
                delay(1000L)
            }

            cycleCount++
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(28.dp))
                .border(1.5.dp, Color(0xFF6B1D2A), RoundedCornerShape(28.dp))
                .testTag("sos_breathing_dialog"),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF16090D))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(CoralSos)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "SOS CRAVING RELIEF",
                            color = CoralSos,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "4-7-8 Deep Breathing",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Cravings peak for 3 minutes and then fade. Ride the wave with deep oxygen.",
                    color = Color(0xFFFDA4AF),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Pulsing Breathing Circle
                Box(
                    modifier = Modifier
                        .size(170.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Outer pulsating ripple ring
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .scale(circleScale.value)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFFE11D48).copy(alpha = 0.45f),
                                        Color(0xFFBE123C).copy(alpha = 0.15f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )

                    // Inner core circle
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFFBE123C),
                                        Color(0xFF881337)
                                    )
                                )
                            )
                            .border(2.dp, CoralSos, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$secondsLeft",
                                color = Color.White,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(
                                text = currentPhase.label,
                                color = Color(0xFFFFE4E6),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Cycle info & instruction
                Text(
                    text = currentPhase.instruction,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Cycle $cycleCount of 4",
                    color = Color(0xFFFDA4AF),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Distraction quick tip box
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF261016))
                        .border(1.dp, Color(0xFF4C1D26), RoundedCornerShape(14.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = "💡 EMERGENCY DISTRACTION TIPS",
                        color = CoralSos,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "• Drink a tall glass of ice-cold water slowly\n• Chew mint gum or suck on a hard candy\n• Do 15 push-ups or take a brisk 2-minute walk\n• Call or message a supportive friend",
                        color = Color(0xFFFECDD3),
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Action Buttons
                Button(
                    onClick = {
                        onOvercameCraving("Urge Surfed", "Used 4-7-8 breathing")
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("resisted_craving_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmeraldPrimary,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "I Beat The Craving!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = "Close For Now",
                        color = Color(0xFFCBD5E1),
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}
