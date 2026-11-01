package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.CoralSos
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LogCravingDialog(
    onDismiss: () -> Unit,
    onSaveCraving: (intensity: Int, trigger: String, note: String, technique: String, overcome: Boolean) -> Unit
) {
    var intensity by remember { mutableFloatStateOf(6f) }
    var selectedTrigger by remember { mutableStateOf("Stress / Work") }
    var selectedTechnique by remember { mutableStateOf("Deep Breathing") }
    var note by remember { mutableStateOf("") }
    var didOvercome by remember { mutableStateOf(true) }

    val triggers = listOf(
        "Stress / Work", "After Meal", "Social / Friends",
        "Coffee Break", "Boredom", "Driving", "Alcohol", "Fatigue"
    )

    val techniques = listOf(
        "Deep Breathing", "Drank Cold Water", "Brisk Walk",
        "Chewed Gum", "Urge Surfing", "Distracted Mind"
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(24.dp))
                .border(1.5.dp, Color(0xFF1F4E40), RoundedCornerShape(24.dp))
                .testTag("log_craving_dialog"),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0C241D))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(22.dp)
            ) {
                // Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF163E32)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = null,
                                tint = if (intensity > 7) CoralSos else EmeraldLight,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Record a Craving",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Log this urge to add it to your resisted history",
                                color = Color(0xFF94A3B8),
                                fontSize = 11.sp
                            )
                        }
                    }

                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Resisted status toggle (defaults to TRUE with celebratory styling)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (didOvercome) Color(0xFF103A2F) else Color(0xFF2E1319))
                        .border(
                            1.dp,
                            if (didOvercome) Color(0xFF1E5B4A) else Color(0xFF5C1E2B),
                            RoundedCornerShape(14.dp)
                        )
                        .clickable { didOvercome = !didOvercome }
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (didOvercome) Icons.Default.CheckCircle else Icons.Default.Shield,
                            contentDescription = null,
                            tint = if (didOvercome) EmeraldLight else CoralSos,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (didOvercome) "Successfully Resisted!" else "Gave in / Smoked",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (didOvercome) "Adds +1 to your resisted count" else "We will still support you!",
                                color = if (didOvercome) Color(0xFFA7F3D0) else Color(0xFFFDA4AF),
                                fontSize = 11.sp
                            )
                        }
                    }

                    Switch(
                        checked = didOvercome,
                        onCheckedChange = { didOvercome = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Black,
                            checkedTrackColor = EmeraldPrimary,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color(0xFF4C131E)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Intensity quick buttons + slider
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CRAVING INTENSITY: ${intensity.toInt()}/10",
                        color = if (intensity > 7) CoralSos else EmeraldLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = when {
                            intensity <= 3 -> "Mild Urge"
                            intensity <= 6 -> "Moderate"
                            intensity <= 8 -> "Strong Urge"
                            else -> "Severe Urge"
                        },
                        color = Color(0xFFCBD5E1),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Slider(
                    value = intensity,
                    onValueChange = { intensity = it },
                    valueRange = 1f..10f,
                    steps = 8,
                    colors = SliderDefaults.colors(
                        thumbColor = if (intensity > 7) CoralSos else EmeraldPrimary,
                        activeTrackColor = if (intensity > 7) CoralSos else EmeraldPrimary,
                        inactiveTrackColor = Color(0xFF183D33)
                    )
                )

                // Quick Intensity Presets
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("Mild (3)" to 3f, "Medium (5)" to 5f, "Strong (8)" to 8f, "Peak (10)" to 10f).forEach { (label, value) ->
                        val isSelected = intensity.toInt() == value.toInt()
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) Color(0xFF1A4E40) else Color(0xFF102E26))
                                .border(1.dp, if (isSelected) EmeraldLight else Color(0xFF1B4538), RoundedCornerShape(8.dp))
                                .clickable { intensity = value }
                                .padding(vertical = 5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                color = if (isSelected) EmeraldLight else Color(0xFF94A3B8),
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Trigger chips
                Text(
                    text = "WHAT TRIGGERED THIS CRAVING?",
                    color = EmeraldLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(6.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    triggers.forEach { trigger ->
                        val isSelected = selectedTrigger == trigger
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) EmeraldPrimary else Color(0xFF13362C))
                                .border(1.dp, if (isSelected) EmeraldLight else Color(0xFF205244), RoundedCornerShape(8.dp))
                                .clickable { selectedTrigger = trigger }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = trigger,
                                color = if (isSelected) Color.Black else Color.White,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Coping Technique chips
                Text(
                    text = "HOW DID YOU RESIST IT?",
                    color = EmeraldLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(6.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    techniques.forEach { technique ->
                        val isSelected = selectedTechnique == technique
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) Color(0xFF1D5444) else Color(0xFF13362C))
                                .border(1.dp, if (isSelected) EmeraldLight else Color(0xFF205244), RoundedCornerShape(8.dp))
                                .clickable { selectedTechnique = technique }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = technique,
                                color = if (isSelected) Color.White else Color(0xFFCBD5E1),
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Optional note input
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Note / thoughts (optional)", fontSize = 11.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = EmeraldPrimary,
                        unfocusedBorderColor = Color(0xFF235547),
                        focusedLabelColor = EmeraldLight,
                        unfocusedLabelColor = Color(0xFF94A3B8),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Submit Button
                Button(
                    onClick = {
                        onSaveCraving(
                            intensity.toInt(),
                            selectedTrigger,
                            note,
                            selectedTechnique,
                            didOvercome
                        )
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("save_craving_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (didOvercome) EmeraldPrimary else CoralSos,
                        contentColor = if (didOvercome) Color.Black else Color.White
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(
                        imageVector = if (didOvercome) Icons.Default.CheckCircle else Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (didOvercome) "Record Resisted Craving" else "Log Incident & Move Forward",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
