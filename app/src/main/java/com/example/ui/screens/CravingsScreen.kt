package com.example.ui.screens

import android.text.format.DateUtils
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import com.example.data.CravingRecord
import com.example.ui.theme.CoralSos
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldTertiary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class CravingHistoryFilter { ALL, RESISTED_ONLY, HIGH_INTENSITY }

@Composable
fun CravingsScreen(
    cravings: List<CravingRecord>,
    overcomeCount: Int,
    onOpenSosBreathing: () -> Unit,
    onOpenLogCraving: () -> Unit,
    onQuickResistCraving: () -> Unit,
    onDeleteCraving: (CravingRecord) -> Unit
) {
    val dateFormat = remember { SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault()) }
    var currentFilter by remember { mutableStateOf(CravingHistoryFilter.ALL) }

    val filteredList = when (currentFilter) {
        CravingHistoryFilter.ALL -> cravings
        CravingHistoryFilter.RESISTED_ONLY -> cravings.filter { it.overcome }
        CravingHistoryFilter.HIGH_INTENSITY -> cravings.filter { it.intensity >= 7 }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("cravings_screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
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
                        .background(Color(0xFF0F362C)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = EmeraldLight,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Craving Resistance",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Record urge moments & track resisted cravings",
                        color = Color(0xFF94A3B8),
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 1. Resisted Cravings Hero Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF0F362C),
                                Color(0xFF0A241D),
                                Color(0xFF061813)
                            )
                        )
                    )
                    .border(1.5.dp, Color(0xFF1E5B4B), RoundedCornerShape(24.dp))
                    .padding(20.dp)
                    .testTag("resisted_cravings_hero_card")
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ThumbUp,
                                contentDescription = null,
                                tint = EmeraldLight,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "SUCCESSFULLY RESISTED",
                                color = EmeraldLight,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF144235))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            val resistanceRate = if (cravings.isNotEmpty()) {
                                "${((overcomeCount.toFloat() / cravings.size.toFloat()) * 100).toInt()}% Won"
                            } else "100% Won"

                            Text(
                                text = resistanceRate,
                                color = Color(0xFFA7F3D0),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = overcomeCount.toString(),
                            color = Color.White,
                            fontSize = 44.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = (-1).sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (overcomeCount == 1) "Craving Resisted" else "Cravings Resisted",
                            color = Color(0xFFA7F3D0),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    Text(
                        text = if (overcomeCount > 0) {
                            "Every resisted urge rewires your brain and weakens nicotine receptors permanently."
                        } else {
                            "Record your very first craving when it strikes. Each resisted urge makes you stronger."
                        },
                        color = Color(0xFFCBD5E1),
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Mini stats bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF0B211A))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total Logged: ${cravings.size}",
                            color = Color(0xFF94A3B8),
                            fontSize = 11.sp
                        )

                        val avgIntensity = if (cravings.isNotEmpty()) {
                            String.format(Locale.US, "%.1f/10", cravings.map { it.intensity }.average())
                        } else "None"
                        Text(
                            text = "Avg Urge: $avgIntensity",
                            color = Color(0xFFFDE68A),
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 2. Logging Tools Action Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Record craving detailed tool
                Button(
                    onClick = onOpenLogCraving,
                    modifier = Modifier
                        .weight(1.2f)
                        .height(48.dp)
                        .testTag("record_craving_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmeraldPrimary,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Record Craving", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                // 1-Tap Quick Resist Button
                Button(
                    onClick = onQuickResistCraving,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("quick_resist_craving_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF13382D),
                        contentColor = EmeraldLight
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.FlashOn, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Quick Resist +1", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                // SOS Breathing Button
                Button(
                    onClick = onOpenSosBreathing,
                    modifier = Modifier
                        .size(48.dp)
                        .testTag("cravings_sos_quick_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF38141B),
                        contentColor = CoralSos
                    ),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.SelfImprovement,
                        contentDescription = "Breathing SOS",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 3. History Filter Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "RESISTED HISTORY (${filteredList.size})",
                    color = EmeraldLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    FilterTab(
                        label = "All (${cravings.size})",
                        isSelected = currentFilter == CravingHistoryFilter.ALL,
                        onClick = { currentFilter = CravingHistoryFilter.ALL }
                    )
                    FilterTab(
                        label = "Resisted ($overcomeCount)",
                        isSelected = currentFilter == CravingHistoryFilter.RESISTED_ONLY,
                        onClick = { currentFilter = CravingHistoryFilter.RESISTED_ONLY }
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
        }

        // Empty state
        if (filteredList.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("empty_cravings_card"),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F2620)),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1B493D))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF143B30)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = null,
                                tint = EmeraldLight,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "No cravings recorded yet",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "When you feel an urge to smoke, tap \"Record Craving\" to document it and celebrate resisting it!",
                            color = Color(0xFF94A3B8),
                            fontSize = 12.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = onOpenLogCraving,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = EmeraldPrimary,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Record A Craving Now", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }
        } else {
            // History items
            items(filteredList, key = { it.id }) { craving ->
                CravingHistoryCard(
                    craving = craving,
                    formattedDate = dateFormat.format(Date(craving.timestamp)),
                    relativeTime = DateUtils.getRelativeTimeSpanString(
                        craving.timestamp,
                        System.currentTimeMillis(),
                        DateUtils.MINUTE_IN_MILLIS
                    ).toString(),
                    onDelete = { onDeleteCraving(craving) }
                )
            }
        }
    }
}

@Composable
private fun CravingHistoryCard(
    craving: CravingRecord,
    formattedDate: String,
    relativeTime: String,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("craving_record_${craving.id}"),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0D251F)),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (craving.overcome) Color(0xFF1E5343) else Color(0xFF4C1D26)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row: Status & Intensity pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Resisted badge
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (craving.overcome) Icons.Default.CheckCircle else Icons.Default.Shield,
                        contentDescription = null,
                        tint = if (craving.overcome) EmeraldLight else CoralSos,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (craving.overcome) "Resisted Successfully ✓" else "Urge Incident",
                        color = if (craving.overcome) EmeraldLight else CoralSos,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Intensity pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (craving.intensity > 7) Color(0xFF38141B) else Color(0xFF143B30))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Urge ${craving.intensity}/10",
                        color = if (craving.intensity > 7) CoralSos else Color(0xFFA7F3D0),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Main Trigger & Details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Trigger: ${craving.trigger}",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Coping strategy: ${craving.techniqueUsed}",
                        color = Color(0xFFA7F3D0),
                        fontSize = 12.sp
                    )

                    if (craving.note.isNotBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF0A1E19))
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "\"${craving.note}\"",
                                color = Color(0xFFE2E8F0),
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "$relativeTime • $formattedDate",
                        color = Color(0xFF64748B),
                        fontSize = 10.sp
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Delete record",
                        tint = Color(0xFF64748B),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterTab(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) EmeraldPrimary else Color(0xFF13362C))
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            color = if (isSelected) Color.Black else Color(0xFFCBD5E1),
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
