package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.SmokeFree
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.JourneyProfile
import com.example.ui.theme.PixelDarkBackground
import com.example.ui.theme.PixelDarkCardBorder
import com.example.ui.theme.PixelDarkSurface
import com.example.ui.theme.PixelMint
import com.example.ui.theme.PixelMintLight
import com.example.ui.theme.PixelPink
import com.example.ui.theme.PixelPinkContainer
import com.example.ui.theme.PixelPinkSoft
import com.example.ui.theme.PixelPinkVibrant
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AiOnboardingDialog(
    currentProfile: JourneyProfile,
    onDismiss: () -> Unit,
    onComplete: (JourneyProfile) -> Unit
) {
    // Conversational Wizard Steps: 0 to 5
    // 0: Intro & Daily Cigarettes
    // 1: Pack Price & Currency
    // 2: Height & Weight -> BMI
    // 3: Pre-existing Conditions
    // 4: Quit Timestamp / Mode
    // 5: Generated AI Health Blueprint & Confirmation
    var step by remember { mutableIntStateOf(0) }

    var cigsPerDay by remember { mutableIntStateOf(currentProfile.cigarettesPerDay) }
    var pricePerPack by remember { mutableDoubleStateOf(currentProfile.pricePerPack) }
    var currencySymbol by remember { mutableStateOf(currentProfile.currencySymbol) }
    var heightCm by remember { mutableFloatStateOf(currentProfile.heightCm) }
    var weightKg by remember { mutableFloatStateOf(currentProfile.weightKg) }
    var selectedConditions by remember { mutableStateOf(currentProfile.healthConditions.toMutableSet()) }
    var quitTimestamp by remember { mutableStateOf(currentProfile.quitTimestamp) }

    // Temporary profile for calculating BMI and projections
    val previewProfile = remember(cigsPerDay, pricePerPack, currencySymbol, heightCm, weightKg, selectedConditions, quitTimestamp) {
        JourneyProfile(
            quitTimestamp = quitTimestamp,
            cigarettesPerDay = cigsPerDay,
            pricePerPack = pricePerPack,
            cigarettesPerPack = currentProfile.cigarettesPerPack,
            currencySymbol = currencySymbol,
            goalTitle = currentProfile.goalTitle,
            goalCost = currentProfile.goalCost,
            heightCm = heightCm,
            weightKg = weightKg,
            healthConditions = selectedConditions,
            hasCompletedAiOnboarding = true,
            smokeWindowEndTime = currentProfile.smokeWindowEndTime
        )
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.92f)
                .clip(RoundedCornerShape(32.dp))
                .border(2.dp, PixelPink.copy(alpha = 0.5f), RoundedCornerShape(32.dp))
                .testTag("ai_onboarding_dialog"),
            color = PixelDarkBackground
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Top Bar with Bot Persona & Close Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(PixelPinkVibrant),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "BreatheAI Clinical Intake",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(
                                text = "Step ${step + 1} of 6 • Personalized Health Model",
                                color = Color(0xFFA59AA8),
                                fontSize = 11.sp
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color(0xFFA59AA8)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Progress Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    for (i in 0..5) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(
                                    if (i <= step) PixelPinkVibrant else Color(0xFF382342)
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Scrollable Content Area
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    when (step) {
                        0 -> StepCigarettesPerDay(
                            count = cigsPerDay,
                            onCountChange = { cigsPerDay = it }
                        )

                        1 -> StepPriceAndCurrency(
                            currency = currencySymbol,
                            price = pricePerPack,
                            onCurrencyChange = { currencySymbol = it },
                            onPriceChange = { pricePerPack = it }
                        )

                        2 -> StepBiometricsBmi(
                            heightCm = heightCm,
                            weightKg = weightKg,
                            bmi = previewProfile.bmi,
                            bmiCategory = previewProfile.bmiCategory,
                            onHeightChange = { heightCm = it },
                            onWeightChange = { weightKg = it }
                        )

                        3 -> StepHealthConditions(
                            selectedConditions = selectedConditions,
                            onToggleCondition = { cond ->
                                if (selectedConditions.contains(cond)) {
                                    selectedConditions = (selectedConditions - cond).toMutableSet()
                                } else {
                                    selectedConditions = (selectedConditions + cond).toMutableSet()
                                }
                            }
                        )

                        4 -> StepQuitMilestone(
                            currentQuitTime = quitTimestamp,
                            onSelectTimestamp = { quitTimestamp = it }
                        )

                        5 -> StepProfileCardPreview(
                            profile = previewProfile
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Bottom Nav Buttons (Back & Continue / Complete)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (step > 0) {
                        Button(
                            onClick = { step-- },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PixelDarkSurface,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Back", fontSize = 13.sp)
                        }
                    } else {
                        Spacer(modifier = Modifier.width(1.dp))
                    }

                    if (step < 5) {
                        Button(
                            onClick = { step++ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PixelPinkVibrant,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.testTag("onboarding_continue_button")
                        ) {
                            Text("Continue", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    } else {
                        Button(
                            onClick = { onComplete(previewProfile) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PixelMint,
                                contentColor = Color(0xFF0F1A14)
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.testTag("onboarding_launch_button")
                        ) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Launch My Health Journey", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BotBubble(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF261530))
            .border(1.dp, PixelPink.copy(alpha = 0.35f), RoundedCornerShape(20.dp))
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Text(
                text = "🤖",
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = message,
                color = Color(0xFFE9DCF0),
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun StepCigarettesPerDay(
    count: Int,
    onCountChange: (Int) -> Unit
) {
    Column {
        BotBubble(
            message = "Hello! I am BreatheAI, your clinical smoking cessation coach. To personalize your biological recovery timelines, how many cigarettes do you typically smoke per day?"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "DAILY CIGARETTES:",
            color = Color(0xFFA59AA8),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Large Number Counter Stepper
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(PixelDarkSurface)
                .border(1.5.dp, PixelDarkCardBorder, RoundedCornerShape(24.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { if (count > 1) onCountChange(count - 1) },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2B1A35))
                ) {
                    Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease", tint = PixelPink)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$count",
                        color = PixelPinkVibrant,
                        fontSize = 44.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = if (count == 1) "cigarette / day" else "cigarettes / day",
                        color = Color(0xFFA59AA8),
                        fontSize = 12.sp
                    )
                }

                IconButton(
                    onClick = { if (count < 80) onCountChange(count + 1) },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2B1A35))
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Increase", tint = PixelMint)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quick Preset Chips
        Text(
            text = "Quick Presets:",
            color = Color(0xFFA59AA8),
            fontSize = 11.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(5, 10, 15, 20, 30).forEach { preset ->
                val isSelected = count == preset
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) PixelPinkVibrant else PixelDarkSurface)
                        .border(1.dp, if (isSelected) PixelPink else PixelDarkCardBorder, RoundedCornerShape(12.dp))
                        .clickable { onCountChange(preset) }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$preset",
                        color = if (isSelected) Color.White else Color(0xFFDDD2E2),
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun StepPriceAndCurrency(
    currency: String,
    price: Double,
    onCurrencyChange: (String) -> Unit,
    onPriceChange: (Double) -> Unit
) {
    Column {
        BotBubble(
            message = "Smoking takes a severe financial toll. What currency do you use, and approximately how much does one pack (20 cigarettes) cost?"
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "SELECT CURRENCY:",
            color = Color(0xFFA59AA8),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOf("$", "€", "£", "₹", "¥", "C$").forEach { cur ->
                val isSelected = currency == cur
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) PixelMint else PixelDarkSurface)
                        .clickable { onCurrencyChange(cur) }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = cur,
                        color = if (isSelected) Color(0xFF0F1A14) else Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "PRICE PER PACK (20 CIGARETTES):",
            color = Color(0xFFA59AA8),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(PixelDarkSurface)
                .border(1.dp, PixelDarkCardBorder, RoundedCornerShape(20.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = String.format(Locale.US, "%s%.2f", currency, price),
                    color = PixelMintLight,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Slider(
                    value = price.toFloat(),
                    onValueChange = { onPriceChange(it.toDouble()) },
                    valueRange = 2f..40f,
                    steps = 75,
                    colors = SliderDefaults.colors(
                        thumbColor = PixelMint,
                        activeTrackColor = PixelMint,
                        inactiveTrackColor = Color(0xFF382342)
                    ),
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Text(
                    text = "Slide to adjust pack cost",
                    color = Color(0xFFA59AA8),
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
private fun StepBiometricsBmi(
    heightCm: Float,
    weightKg: Float,
    bmi: Float,
    bmiCategory: String,
    onHeightChange: (Float) -> Unit,
    onWeightChange: (Float) -> Unit
) {
    Column {
        BotBubble(
            message = "Nicotine artificially speeds up metabolism while constricting blood vessels. By calculating your BMI, I can tailor metabolic rebound insights and physical recovery projections for you."
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Live Calculated BMI Highlight Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(Color(0xFF261530))
                .border(1.5.dp, PixelMint.copy(alpha = 0.5f), RoundedCornerShape(18.dp))
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "CALCULATED BMI",
                        color = Color(0xFFA59AA8),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = String.format(Locale.US, "%.1f", bmi),
                        color = PixelMintLight,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(PixelMint.copy(alpha = 0.2f))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = bmiCategory.uppercase(),
                        color = PixelMint,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Height Slider
        Text(
            text = "HEIGHT: ${heightCm.toInt()} cm (${(heightCm / 30.48f).toInt()}'${((heightCm % 30.48f) / 2.54f).toInt()}\")",
            color = Color(0xFFDDD2E2),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Slider(
            value = heightCm,
            onValueChange = onHeightChange,
            valueRange = 130f..220f,
            colors = SliderDefaults.colors(
                thumbColor = PixelPink,
                activeTrackColor = PixelPink,
                inactiveTrackColor = Color(0xFF382342)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Weight Slider
        Text(
            text = "WEIGHT: ${weightKg.toInt()} kg (${(weightKg * 2.20462f).toInt()} lbs)",
            color = Color(0xFFDDD2E2),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Slider(
            value = weightKg,
            onValueChange = onWeightChange,
            valueRange = 40f..160f,
            colors = SliderDefaults.colors(
                thumbColor = PixelPink,
                activeTrackColor = PixelPink,
                inactiveTrackColor = Color(0xFF382342)
            )
        )
    }
}

@Composable
private fun StepHealthConditions(
    selectedConditions: Set<String>,
    onToggleCondition: (String) -> Unit
) {
    val conditions = listOf(
        "High Blood Pressure" to "Cardiovascular & arterial elasticity recovery",
        "Type 2 Diabetes" to "Reversal of nicotine-induced insulin resistance",
        "Asthma / Respiratory" to "Pulmonary cilia regeneration & airway dilation",
        "Heart Disease / Cholesterol" to "Platelet aggregation drop & 50% clot risk reduction",
        "General Health & Metabolism" to "Full VO2 max endurance & athletic capacity"
    )

    Column {
        BotBubble(
            message = "Do you have any pre-existing health conditions? Select all that apply. I will map your personal recovery to clinical medical literature (AHA, ADA, WHO)."
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "SELECT MEDICAL CONDITIONS:",
            color = Color(0xFFA59AA8),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            conditions.forEach { (title, subtitle) ->
                val isSelected = selectedConditions.contains(title)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSelected) Color(0xFF36183C) else PixelDarkSurface)
                        .border(
                            1.5.dp,
                            if (isSelected) PixelPinkVibrant else PixelDarkCardBorder,
                            RoundedCornerShape(16.dp)
                        )
                        .clickable { onToggleCondition(title) }
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = title,
                                color = if (isSelected) PixelPink else Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = subtitle,
                                color = Color(0xFFA59AA8),
                                fontSize = 11.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) PixelPinkVibrant else Color(0xFF281830)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StepQuitMilestone(
    currentQuitTime: Long,
    onSelectTimestamp: (Long) -> Unit
) {
    val now = System.currentTimeMillis()
    Column {
        BotBubble(
            message = "Almost done! When did your smoke-free journey begin? You can start right this second or log an existing quit time."
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "JOURNEY START TIMELINE:",
            color = Color(0xFFA59AA8),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        val options = listOf(
            "Starting Right Now!" to now,
            "Today (3 hours ago)" to (now - 3L * 3600 * 1000L),
            "Yesterday (24 hours ago)" to (now - 24L * 3600 * 1000L),
            "3 Days Ago (In withdrawal peak)" to (now - 3L * 24 * 3600 * 1000L),
            "15 Days Ago (Receptor reset achieved)" to (now - 15L * 24 * 3600 * 1000L)
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            options.forEach { (label, timestamp) ->
                // Approximate match check within 10 minutes
                val isSelected = Math.abs(currentQuitTime - timestamp) < 10 * 60 * 1000L
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (isSelected) PixelMint.copy(alpha = 0.15f) else PixelDarkSurface)
                        .border(
                            1.dp,
                            if (isSelected) PixelMint else PixelDarkCardBorder,
                            RoundedCornerShape(14.dp)
                        )
                        .clickable { onSelectTimestamp(timestamp) }
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = label,
                            color = if (isSelected) PixelMintLight else Color.White,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )

                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = PixelMint,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StepProfileCardPreview(
    profile: JourneyProfile
) {
    Column {
        BotBubble(
            message = "Intake complete! Based on your biology, smoking habits, and health background, here is your Tailored AI Cessation Blueprint. Review your numbers and launch!"
        )

        Spacer(modifier = Modifier.height(14.dp))

        AiHealthProfileCard(
            profile = profile,
            onRecalibrateIntake = { /* already in intake */ }
        )
    }
}
