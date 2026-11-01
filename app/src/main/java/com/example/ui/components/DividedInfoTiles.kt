package com.example.ui.components

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.JourneyProfile
import com.example.model.HealthMilestone
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

data class BioMarkerInfo(
    val name: String,
    val valueString: String,
    val status: String,
    val note: String,
    val icon: ImageVector,
    val iconColor: Color
)

@Composable
fun DividedInfoTiles(
    profile: JourneyProfile,
    currentTime: Long,
    onNavigateToBenefits: () -> Unit,
    modifier: Modifier = Modifier
) {
    val elapsed = profile.elapsedMillis(currentTime)

    // Bio-markers collection to cycle through on tap or display
    val bioMarkers = listOf(
        BioMarkerInfo(
            name = "Blood Sugar",
            valueString = "Normal Baseline",
            status = "Stabilized",
            note = "Metabolism rebalanced without nicotine spikes",
            icon = Icons.Default.Bloodtype,
            iconColor = PixelPinkVibrant
        ),
        BioMarkerInfo(
            name = "Carbon Monoxide",
            valueString = if (elapsed > 12 * 3600 * 1000L) "0 ppm" else "Dropping fast",
            status = if (elapsed > 12 * 3600 * 1000L) "100% Cleared" else "Detoxifying",
            note = "Blood oxygen capacity fully restored",
            icon = Icons.Default.Air,
            iconColor = PixelMint
        ),
        BioMarkerInfo(
            name = "Blood Pressure",
            valueString = if (elapsed > 20 * 60 * 1000L) "120/80 mmHg" else "Settling",
            status = "Normalized",
            note = "Heart strain reduced by ~15%",
            icon = Icons.Default.MonitorHeart,
            iconColor = PixelPink
        ),
        BioMarkerInfo(
            name = "Oxygen Saturation",
            valueString = "98% - 99%",
            status = "Peak Vitality",
            note = "Cells receiving full cellular oxygen",
            icon = Icons.Default.Spa,
            iconColor = PixelMintLight
        )
    )

    var currentBioIndex by remember { mutableIntStateOf(0) }
    val currentBio = bioMarkers[currentBioIndex % bioMarkers.size]

    // Determine latest unlocked benefit or earliest milestone
    val achievedMilestones = HealthMilestone.ALL_MILESTONES.filter { it.isAchieved(elapsed) }
    val latestBenefit = achievedMilestones.lastOrNull() ?: HealthMilestone.ALL_MILESTONES.first()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .testTag("divided_info_tiles_row"),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // --- Tile 3A (Left): Vital Bio-Marker Status (Interactive Tap to cycle) ---
        Box(
            modifier = Modifier
                .weight(1f)
                .height(180.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(PixelDarkSurfaceVariant)
                .border(1.5.dp, PixelDarkCardBorder, RoundedCornerShape(28.dp))
                .clickable {
                    currentBioIndex++
                }
                .padding(14.dp)
                .testTag("biomarker_status_tile")
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(PixelPinkContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = currentBio.icon,
                            contentDescription = null,
                            tint = currentBio.iconColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(PixelMint.copy(alpha = 0.15f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = currentBio.status,
                            color = PixelMintLight,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column {
                    Text(
                        text = currentBio.name,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = currentBio.valueString,
                        color = PixelPinkSoft,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = currentBio.note,
                        color = Color(0xFFAFA4B5),
                        fontSize = 10.sp,
                        lineHeight = 13.sp,
                        maxLines = 2
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Tap to switch ↻",
                        color = Color(0xFF887D90),
                        fontSize = 9.sp
                    )
                }
            }
        }

        // --- Tile 3B (Right): Latest Biological Benefit Gained ---
        Box(
            modifier = Modifier
                .weight(1f)
                .height(180.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(PixelDarkSurfaceVariant)
                .border(1.5.dp, PixelDarkCardBorder, RoundedCornerShape(28.dp))
                .clickable { onNavigateToBenefits() }
                .padding(14.dp)
                .testTag("latest_benefit_tile")
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(PixelMint.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = PixelMint,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(PixelSunYellow.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = if (achievedMilestones.isEmpty()) "In Progress" else "Benefit Unlocked",
                            color = PixelSunYellow,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column {
                    Text(
                        text = "Latest Benefit",
                        color = PixelMintLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = latestBenefit.title,
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = latestBenefit.description,
                        color = Color(0xFFAFA4B5),
                        fontSize = 10.sp,
                        lineHeight = 13.sp,
                        maxLines = 2
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "View all benefits →",
                        color = PixelPinkSoft,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
