package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.PixelBayBlue
import com.example.ui.theme.PixelBayBlueContainer
import com.example.ui.theme.PixelCoral
import com.example.ui.theme.PixelCoralContainer
import com.example.ui.theme.PixelDarkBackground
import com.example.ui.theme.PixelDarkCardBorder
import com.example.ui.theme.PixelDarkSurface
import com.example.ui.theme.PixelDarkSurfaceVariant
import com.example.ui.theme.PixelLimoncello
import com.example.ui.theme.PixelMint
import com.example.ui.theme.PixelMintLight
import com.example.ui.theme.PixelPink
import com.example.ui.theme.PixelPinkContainer
import com.example.ui.theme.PixelPinkSoft
import com.example.ui.theme.PixelPinkVibrant
import com.example.viewmodel.ChatMessage
import com.example.viewmodel.ChatUiState
import com.example.viewmodel.ChatViewModel
import com.example.viewmodel.MessageSender

@Composable
fun GeminiChatScreen(
    chatViewModel: ChatViewModel,
    onOpenBreathingExercise: () -> Unit,
    onLogResistedCraving: () -> Unit,
    modifier: Modifier = Modifier
) {
    val messages by chatViewModel.messages.collectAsState()
    val uiState by chatViewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    var inputText by remember { mutableStateOf("") }

    val quickChips = listOf(
        "🌊 3-Minute Urge Wave",
        "🥤 Cut Straw & Ice Trick",
        "☕ Break Coffee + Smoke Trigger",
        "⚡ Bad Craving SOS Right Now",
        "🎯 Why the 15-Day Target?",
        "🛡️ I had a slip, encourage me"
    )

    // Auto-scroll to bottom on new message
    LaunchedEffect(messages.size, uiState) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PixelDarkBackground)
            .padding(bottom = 88.dp) // Bottom padding for floating navigation pill
    ) {
        // Pixel Expressive Header with Gemini branding & gradient pill
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Pixel Avatar with Psychology AI symbol
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(PixelBayBlue, PixelPinkVibrant)
                            )
                        )
                        .padding(2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(14.dp))
                            .background(PixelDarkSurface),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = "Gemini AI Mind",
                            tint = PixelBayBlue,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "BreatheAI",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = (-0.3).sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(PixelBayBlue.copy(alpha = 0.2f))
                                .border(1.dp, PixelBayBlue.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Gemini 2.0",
                                color = PixelBayBlue,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Text(
                        text = "Real cessation neuroscience & Reddit hacks",
                        color = Color(0xFFA59AA8),
                        fontSize = 11.sp
                    )
                }
            }

            // Clear chat button
            IconButton(
                onClick = { chatViewModel.clearChat() },
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(PixelDarkSurfaceVariant)
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = "Reset Conversation",
                    tint = Color(0xFFC7BDCD),
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        // Quick Pixel Pill Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            quickChips.forEach { prompt ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))
                        .background(PixelDarkSurface)
                        .border(1.dp, PixelDarkCardBorder, RoundedCornerShape(18.dp))
                        .clickable {
                            chatViewModel.sendMessage(prompt)
                        }
                        .padding(horizontal = 12.dp, vertical = 7.dp)
                        .testTag("quick_chip_${prompt.take(10)}")
                ) {
                    Text(
                        text = prompt,
                        color = Color(0xFFECE5F0),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Messages List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(6.dp))
            }

            items(messages) { message ->
                PixelChatMessageItem(
                    message = message,
                    onOpenBreathing = onOpenBreathingExercise,
                    onLogResisted = onLogResistedCraving,
                    onQuickPrompt = { chatViewModel.sendMessage(it) }
                )
            }

            if (uiState is ChatUiState.Loading) {
                item {
                    PixelTypingIndicator()
                }
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        // Error message if any
        if (uiState is ChatUiState.Error) {
            val errorMsg = (uiState as ChatUiState.Error).message
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(PixelPinkContainer)
                    .border(1.dp, PixelPinkVibrant.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                    .padding(8.dp)
            ) {
                Text(
                    text = "Note: $errorMsg (Defaulting to built-in neuro-distraction engine)",
                    color = PixelPinkSoft,
                    fontSize = 11.sp
                )
            }
        }

        // Pixel Floating Input Bar
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            color = Color.Transparent
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(PixelDarkSurface)
                    .border(1.dp, PixelDarkCardBorder, RoundedCornerShape(28.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = {
                        Text(
                            "Type your urge, trigger, or question...",
                            color = Color(0xFF786E80),
                            fontSize = 13.sp
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("chat_input_field"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = PixelBayBlue
                    ),
                    singleLine = false,
                    maxLines = 4,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            if (inputText.isNotBlank()) {
                                chatViewModel.sendMessage(inputText.trim())
                                inputText = ""
                                focusManager.clearFocus()
                            }
                        }
                    )
                )

                Spacer(modifier = Modifier.width(6.dp))

                val isSendEnabled = inputText.isNotBlank() && uiState !is ChatUiState.Loading

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSendEnabled) Brush.linearGradient(listOf(PixelCoral, PixelPinkVibrant))
                            else Brush.linearGradient(listOf(PixelDarkSurfaceVariant, PixelDarkSurfaceVariant))
                        )
                        .clickable(enabled = isSendEnabled) {
                            if (inputText.isNotBlank()) {
                                chatViewModel.sendMessage(inputText.trim())
                                inputText = ""
                                focusManager.clearFocus()
                            }
                        }
                        .testTag("chat_send_button"),
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState is ChatUiState.Loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(18.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send Message",
                            tint = if (isSendEnabled) Color.White else Color(0xFF6A5A68),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PixelChatMessageItem(
    message: ChatMessage,
    onOpenBreathing: () -> Unit,
    onLogResisted: () -> Unit,
    onQuickPrompt: (String) -> Unit
) {
    val isUser = message.sender == MessageSender.USER

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        // Expressive Pixel bubble shape: user has tapered bottom-right corner; AI has tapered bottom-left corner
        val bubbleShape = if (isUser) {
            RoundedCornerShape(
                topStart = 22.dp,
                topEnd = 22.dp,
                bottomEnd = 4.dp,
                bottomStart = 22.dp
            )
        } else {
            RoundedCornerShape(
                topStart = 22.dp,
                topEnd = 22.dp,
                bottomEnd = 22.dp,
                bottomStart = 4.dp
            )
        }

        val bubbleBackground = if (isUser) {
            Brush.linearGradient(
                listOf(PixelCoral, PixelPinkVibrant)
            )
        } else {
            Brush.linearGradient(
                listOf(PixelDarkSurface, PixelDarkSurfaceVariant)
            )
        }

        val bubbleBorder = if (isUser) {
            Color.Transparent
        } else {
            PixelDarkCardBorder
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(if (isUser) 0.82f else 0.92f)
                .clip(bubbleShape)
                .background(bubbleBackground)
                .border(1.dp, bubbleBorder, bubbleShape)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Column {
                if (!isUser) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            tint = PixelLimoncello,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "BreatheAI",
                            color = PixelLimoncello,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Text(
                    text = message.text,
                    color = Color.White,
                    fontSize = 14.sp,
                    lineHeight = 21.sp
                )
            }
        }

        // Pixel Quick-Action Pill buttons underneath AI suggestions
        if (!isUser) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // SOS Breathing Pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(PixelPinkContainer)
                        .border(1.dp, PixelPink.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                        .clickable { onOpenBreathing() }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.SelfImprovement,
                            contentDescription = null,
                            tint = PixelPink,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "4-7-8 Breathing",
                            color = PixelPink,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Resisted Craving +1 Pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF0C3829))
                        .border(1.dp, PixelMint.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                        .clickable { onLogResisted() }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = PixelMintLight,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "I Resisted (+1)",
                            color = PixelMintLight,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Straw trick shortcut
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(PixelBayBlueContainer)
                        .border(1.dp, PixelBayBlue.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                        .clickable { onQuickPrompt("Tell me the exact straw hack steps") }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = PixelBayBlue,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Straw Hack Steps",
                            color = PixelBayBlue,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PixelTypingIndicator() {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .background(PixelDarkSurface)
            .border(1.dp, PixelDarkCardBorder, RoundedCornerShape(18.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            color = PixelBayBlue,
            strokeWidth = 2.dp,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "BreatheAI is analyzing neuroscience tricks...",
            color = Color(0xFFA59AA8),
            fontSize = 12.sp
        )
    }
}
