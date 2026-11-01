package com.example.ui.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.JourneyProfile
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldTertiary
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun EditJourneyDialog(
    initialProfile: JourneyProfile,
    onDismiss: () -> Unit,
    onSave: (JourneyProfile) -> Unit,
    onResetQuitDate: () -> Unit
) {
    val context = LocalContext.current
    var quitTimestamp by remember { mutableLongStateOf(initialProfile.quitTimestamp) }
    var cigsPerDayText by remember { mutableStateOf(initialProfile.cigarettesPerDay.toString()) }
    var pricePerPackText by remember { mutableStateOf(String.format(Locale.US, "%.2f", initialProfile.pricePerPack)) }
    var cigsPerPackText by remember { mutableStateOf(initialProfile.cigarettesPerPack.toString()) }
    var currencySymbol by remember { mutableStateOf(initialProfile.currencySymbol) }
    var goalTitle by remember { mutableStateOf(initialProfile.goalTitle) }
    var goalCostText by remember { mutableStateOf(String.format(Locale.US, "%.2f", initialProfile.goalCost)) }

    val dateFormat = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }
    val timeFormat = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }

    val calendar = remember { Calendar.getInstance().apply { timeInMillis = quitTimestamp } }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(24.dp))
                .border(1.5.dp, Color(0xFF1D5344), RoundedCornerShape(24.dp))
                .testTag("edit_journey_dialog"),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0C241D))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(22.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Customize Journey",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }
                }

                Text(
                    text = "Update your quit date, habits, and savings goal. Changes immediately sync with all home screen widgets.",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                )

                // Date & Time Picker Triggers
                Text(
                    text = "QUIT DATE & TIME",
                    color = EmeraldLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Date Button
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF13362C))
                            .border(1.dp, Color(0xFF225747), RoundedCornerShape(12.dp))
                            .clickable {
                                DatePickerDialog(
                                    context,
                                    { _, year, month, dayOfMonth ->
                                        calendar.set(Calendar.YEAR, year)
                                        calendar.set(Calendar.MONTH, month)
                                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                                        quitTimestamp = calendar.timeInMillis
                                    },
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)
                                ).show()
                            }
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CalendarToday, contentDescription = null, tint = EmeraldLight, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = dateFormat.format(Date(quitTimestamp)),
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Time Button
                    Row(
                        modifier = Modifier
                            .weight(0.9f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF13362C))
                            .border(1.dp, Color(0xFF225747), RoundedCornerShape(12.dp))
                            .clickable {
                                TimePickerDialog(
                                    context,
                                    { _, hourOfDay, minute ->
                                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                                        calendar.set(Calendar.MINUTE, minute)
                                        calendar.set(Calendar.SECOND, 0)
                                        quitTimestamp = calendar.timeInMillis
                                    },
                                    calendar.get(Calendar.HOUR_OF_DAY),
                                    calendar.get(Calendar.MINUTE),
                                    false
                                ).show()
                            }
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Schedule, contentDescription = null, tint = EmeraldLight, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = timeFormat.format(Date(quitTimestamp)),
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Smoking habit parameters
                Text(
                    text = "HABIT & PRICING",
                    color = EmeraldLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = cigsPerDayText,
                        onValueChange = { cigsPerDayText = it.filter { c -> c.isDigit() } },
                        label = { Text("Cigs/Day", fontSize = 11.sp) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        colors = outlinedTextFieldColors()
                    )

                    OutlinedTextField(
                        value = pricePerPackText,
                        onValueChange = { pricePerPackText = it },
                        label = { Text("Pack Price", fontSize = 11.sp) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f),
                        colors = outlinedTextFieldColors()
                    )

                    OutlinedTextField(
                        value = cigsPerPackText,
                        onValueChange = { cigsPerPackText = it.filter { c -> c.isDigit() } },
                        label = { Text("Per Pack", fontSize = 11.sp) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(0.9f),
                        colors = outlinedTextFieldColors()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Currency selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text("Currency:", color = Color(0xFF94A3B8), fontSize = 12.sp)
                    listOf("$", "€", "£", "₹", "¥", "C$").forEach { symbol ->
                        val isSelected = currencySymbol == symbol
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) EmeraldPrimary else Color(0xFF143B30))
                                .border(1.dp, if (isSelected) EmeraldLight else Color(0xFF225747), RoundedCornerShape(8.dp))
                                .clickable { currencySymbol = symbol }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = symbol,
                                color = if (isSelected) Color.Black else Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Savings Goal
                Text(
                    text = "CUSTOM SAVINGS GOAL",
                    color = GoldTertiary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = goalTitle,
                        onValueChange = { goalTitle = it },
                        label = { Text("Goal Title", fontSize = 11.sp) },
                        modifier = Modifier.weight(1.3f),
                        colors = outlinedTextFieldColors()
                    )

                    OutlinedTextField(
                        value = goalCostText,
                        onValueChange = { goalCostText = it },
                        label = { Text("Target Cost", fontSize = 11.sp) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f),
                        colors = outlinedTextFieldColors()
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Reset quit date to NOW option
                OutlinedButton(
                    onClick = {
                        val now = System.currentTimeMillis()
                        quitTimestamp = now
                        onResetQuitDate()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.RestartAlt, contentDescription = null, tint = Color(0xFFF87171), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Reset Quit Date to Right Now", color = Color(0xFFF87171), fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Save Button
                Button(
                    onClick = {
                        val cigsDay = cigsPerDayText.toIntOrNull() ?: initialProfile.cigarettesPerDay
                        val pricePack = pricePerPackText.toDoubleOrNull() ?: initialProfile.pricePerPack
                        val cigsPack = cigsPerPackText.toIntOrNull() ?: initialProfile.cigarettesPerPack
                        val goalCost = goalCostText.toDoubleOrNull() ?: initialProfile.goalCost

                        val updated = initialProfile.copy(
                            quitTimestamp = quitTimestamp,
                            cigarettesPerDay = cigsDay.coerceAtLeast(1),
                            pricePerPack = pricePack.coerceAtLeast(0.1),
                            cigarettesPerPack = cigsPack.coerceAtLeast(1),
                            currencySymbol = currencySymbol,
                            goalTitle = goalTitle.ifBlank { "Reward" },
                            goalCost = goalCost.coerceAtLeast(1.0)
                        )
                        onSave(updated)
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("save_journey_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmeraldPrimary,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Save & Sync Changes", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }
        }
    }
}

@Composable
private fun outlinedTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = EmeraldPrimary,
    unfocusedBorderColor = Color(0xFF235547),
    focusedLabelColor = EmeraldLight,
    unfocusedLabelColor = Color(0xFF94A3B8),
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    cursorColor = EmeraldPrimary
)
