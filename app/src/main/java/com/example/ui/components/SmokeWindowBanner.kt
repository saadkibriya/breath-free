package com.example.ui.components

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.JourneyProfile
import com.example.service.SmokeWindowHelper
import com.example.ui.theme.PixelDarkCardBorder
import com.example.ui.theme.PixelDarkSurface
import com.example.ui.theme.PixelMint
import com.example.ui.theme.PixelMintLight
import com.example.ui.theme.PixelPink
import com.example.ui.theme.PixelPinkContainer
import com.example.ui.theme.PixelPinkVibrant

@Composable
fun SmokeWindowBanner(
    profile: JourneyProfile,
    currentTime: Long,
    onOvercameWindow: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isActive = profile.isSmokeWindowActive(currentTime)
    val remainingSec = profile.smokeWindowRemainingSeconds(currentTime)

    // Notification permission launcher for Android 13+
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { /* Proceed whether granted or denied */ }
    )

    fun triggerStartWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        SmokeWindowHelper.startSmokeWindow(context, durationMinutes = 10)
    }

    fun cancelWindow() {
        SmokeWindowHelper.cancelSmokeWindow(context)
    }

    if (isActive) {
        // ACTIVE COUNTDOWN CARD
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF2A1530))
                .border(2.dp, PixelPinkVibrant, RoundedCornerShape(24.dp))
                .padding(16.dp)
                .testTag("active_smoke_window_banner")
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(PixelPinkVibrant),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.NotificationsActive,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "10-MIN CRAVING WINDOW ACTIVE",
                                color = PixelPink,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Alarm scheduled when wave ends",
                                color = Color(0xFFA59AA8),
                                fontSize = 11.sp
                            )
                        }
                    }

                    // Remaining time countdown
                    val mins = remainingSec / 60
                    val secs = remainingSec % 60
                    Text(
                        text = String.format("%02d:%02d", mins, secs),
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Ride the wave! Clinical science proves nicotine craving spikes peak for 3-5 minutes and collapse by minute 10. Your phone will sound an alarm when safe.",
                    color = Color(0xFFE4DAE8),
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            cancelWindow()
                            onOvercameWindow()
                        },
                        modifier = Modifier
                            .weight(1.5f)
                            .height(42.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PixelMint,
                            contentColor = Color(0xFF0F1A14)
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("I Resisted! (+1 Shield)", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }

                    OutlinedButton(
                        onClick = { cancelWindow() },
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFA59AA8)
                        )
                    ) {
                        Text("Cancel", fontSize = 12.sp)
                    }
                }
            }
        }
    } else {
        // IDLE PROMPT: Start 10-Minute Craving Wave Buffer
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(PixelDarkSurface)
                .border(1.dp, PixelDarkCardBorder, RoundedCornerShape(20.dp))
                .padding(14.dp)
                .testTag("idle_smoke_window_banner")
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(PixelPinkContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Alarm,
                            contentDescription = null,
                            tint = PixelPink,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Craving Wave Buffer & Alarm",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Pause for 10 mins before smoking",
                            color = Color(0xFFA59AA8),
                            fontSize = 11.sp
                        )
                    }
                }

                Button(
                    onClick = { triggerStartWindow() },
                    modifier = Modifier.height(38.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PixelPinkVibrant,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(imageVector = Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("10-Min Wave", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
