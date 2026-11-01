package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Lightbulb
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.RealStoriesData
import com.example.model.RealStory
import com.example.ui.theme.PixelBayBlue
import com.example.ui.theme.PixelBayBlueContainer
import com.example.ui.theme.PixelCoral
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
import com.example.ui.theme.PixelRedditOrange

@Composable
fun TipsAndStoriesScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val allStories = remember { RealStoriesData.ALL_STORIES }
    var selectedCategory by remember { mutableStateOf("All") }
    var favoriteStories by remember { mutableStateOf(setOf<String>()) }
    var expandedStoryIds by remember { mutableStateOf(setOf<String>()) }

    val categories = remember {
        listOf("All", "Oral Fixation", "Craving Surge", "First 72 Hours", "Morning Routine", "Mindset Shift", "Inconvenience Barrier")
    }

    val filteredStories = remember(selectedCategory, allStories) {
        if (selectedCategory == "All") allStories
        else allStories.filter { it.category == selectedCategory }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PixelDarkBackground)
            .padding(bottom = 88.dp) // Space for floating bottom pill
    ) {
        // Pixel Expressive Header Banner
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF3B1500))
                            .border(1.dp, PixelRedditOrange.copy(alpha = 0.6f), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Forum,
                            contentDescription = "Real Ex-Smoker Community",
                            tint = PixelRedditOrange,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Real Ex-Smoker Hacks",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = (-0.3).sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(PixelRedditOrange)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "r/stopsmoking",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "Battle-tested survival tricks from people who actually quit",
                            color = Color(0xFFA59AA8),
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Pixel Asymmetric Category Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    val isSelected = selectedCategory == category
                    val chipBackground = if (isSelected) PixelCoral else PixelDarkSurface
                    val chipTextColor = if (isSelected) Color.White else Color(0xFFC0BAC6)
                    val chipBorderColor = if (isSelected) PixelCoral else PixelDarkCardBorder

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(chipBackground)
                            .border(1.dp, chipBorderColor, RoundedCornerShape(16.dp))
                            .clickable { selectedCategory = category }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                            .testTag("filter_chip_$category")
                    ) {
                        Text(
                            text = category,
                            color = chipTextColor,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }

        // List of Real Ex-Smoker Stories & Lifehacks
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                // Top Community Tip Card (Pixel Hero Pill)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(22.dp))
                        .border(1.dp, PixelBayBlue.copy(alpha = 0.4f), RoundedCornerShape(22.dp)),
                    colors = CardDefaults.cardColors(containerColor = PixelBayBlueContainer.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(PixelBayBlue.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = PixelBayBlue,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Every tip below was posted by someone who smoked for years and is now 100% smoke-free. Tap any story to read full steps or jump straight to the source thread.",
                            color = Color(0xFFE2EDFD),
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            items(filteredStories, key = { it.id }) { story ->
                val isExpanded = expandedStoryIds.contains(story.id)
                val isFavorited = favoriteStories.contains(story.id)

                PixelStoryCard(
                    story = story,
                    isExpanded = isExpanded,
                    isFavorited = isFavorited,
                    onToggleExpand = {
                        expandedStoryIds = if (isExpanded) {
                            expandedStoryIds - story.id
                        } else {
                            expandedStoryIds + story.id
                        }
                    },
                    onToggleFavorite = {
                        favoriteStories = if (isFavorited) {
                            favoriteStories - story.id
                        } else {
                            favoriteStories + story.id
                        }
                    },
                    onOpenReddit = {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(story.redditUrl))
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            // Fallback to r/stopsmoking home if thread URL cannot be resolved
                            val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.reddit.com/r/stopsmoking/"))
                            fallbackIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(fallbackIntent)
                        }
                    },
                    onCopyTip = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Ex-Smoker Tip", "${story.title}\n${story.hackSummary}\n\nSteps:\n" + story.actionSteps.joinToString("\n") { "• $it" })
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, "Tip copied to clipboard!", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun PixelStoryCard(
    story: RealStory,
    isExpanded: Boolean,
    isFavorited: Boolean,
    onToggleExpand: () -> Unit,
    onToggleFavorite: () -> Unit,
    onOpenReddit: () -> Unit,
    onCopyTip: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .border(1.dp, PixelDarkCardBorder, RoundedCornerShape(24.dp))
            .testTag("story_card_${story.id}")
            .animateContentSize(animationSpec = tween(250)),
        colors = CardDefaults.cardColors(containerColor = PixelDarkSurface)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Author + Clean Badge Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(PixelRedditOrange.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "u/",
                            color = PixelRedditOrange,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = story.author,
                        color = Color(0xFFDDD3E3),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(PixelMint.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = story.timeClean,
                        color = PixelMintLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Title
            Text(
                text = story.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Former smoker history pill + Category
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(PixelDarkSurfaceVariant)
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = story.yearsSmoked,
                        color = Color(0xFFA59AA8),
                        fontSize = 11.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(PixelPinkContainer)
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = story.category,
                        color = PixelPinkSoft,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Hack Summary (Expressive Pill Box)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(PixelDarkBackground)
                    .border(1.dp, PixelDarkCardBorder.copy(alpha = 0.7f), RoundedCornerShape(14.dp))
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        tint = PixelLimoncello,
                        modifier = Modifier
                            .size(18.dp)
                            .padding(top = 1.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = story.hackSummary,
                        color = Color(0xFFF1EDF4),
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Expanded Section (Full Story + Action Steps)
            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 14.dp)) {
                    // Highlight Quote
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(PixelCoral.copy(alpha = 0.1f))
                            .border(1.dp, PixelCoral.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = story.highlightQuote,
                            color = PixelCoral,
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic,
                            lineHeight = 17.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Real Experience:",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = story.fullStory,
                        color = Color(0xFFC7BDCD),
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Exact Action Steps:",
                        color = PixelMintLight,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    story.actionSteps.forEachIndexed { index, step ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(18.dp)
                                    .clip(CircleShape)
                                    .background(PixelMint.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${index + 1}",
                                    color = PixelMintLight,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = step,
                                color = Color(0xFFE5DEEC),
                                fontSize = 12.sp,
                                lineHeight = 17.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Row: Expand / Collapse + Copy + Reddit Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Expand/Collapse toggle button
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(PixelDarkSurfaceVariant)
                            .clickable { onToggleExpand() }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (isExpanded) "Less" else "Full Story",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    // Copy button
                    IconButton(
                        onClick = onCopyTip,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy Tip",
                            tint = Color(0xFFA59AA8),
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    // Favorite / Helpful button
                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = if (isFavorited) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Save Tip",
                            tint = if (isFavorited) PixelPink else Color(0xFFA59AA8),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                // Explicit Clickable Link to Reddit
                Button(
                    onClick = onOpenReddit,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PixelRedditOrange,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier
                        .height(36.dp)
                        .testTag("open_reddit_button_${story.id}")
                ) {
                    Text(
                        text = "Read on Reddit",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                        contentDescription = "Open Reddit Discussion",
                        modifier = Modifier.size(13.dp)
                    )
                }
            }
        }
    }
}
