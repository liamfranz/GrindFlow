package com.example.grindflow.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ProgressScreen(
    onBackToHome: () -> Unit
) {

    BackHandler {
        onBackToHome()
    }

    // =========================================================
    // THEME COLORS
    // =========================================================

    val backgroundColor = MaterialTheme.colorScheme.background
    val cardColor = MaterialTheme.colorScheme.surface
    val primaryText = MaterialTheme.colorScheme.onSurface
    val secondaryText =
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)

    val purple = MaterialTheme.colorScheme.primary

    val progressTrack =
        MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)

    val achievementBackground =
        MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)


    // =========================================================
    // MAIN SCREEN
    // =========================================================

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {

        // ---------------------------------------------------------
        // HEADER
        // ---------------------------------------------------------

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color(0xFF7B2CBF),
                            Color(0xFFE83E8C)
                        )
                    )
                )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = onBackToHome
                ) {

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(
                    text = "Progress",
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }


        // ---------------------------------------------------------
        // CONTENT
        // ---------------------------------------------------------

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // =====================================================
            // TODAY'S PROGRESS
            // =====================================================

            item {

                Card(
                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(18.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = cardColor
                    ),

                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {

                        Text(
                            text = "Today's Progress",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = primaryText
                        )

                        Spacer(
                            modifier = Modifier.height(15.dp)
                        )

                        Text(
                            text = "3 of 5 tasks completed",
                            fontSize = 15.sp,
                            color = secondaryText
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        LinearProgressIndicator(
                            progress = 0.60f,

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(9.dp),

                            color = purple,
                            trackColor = progressTrack
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Text(
                            text = "60% complete",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = purple
                        )
                    }
                }
            }


            // =====================================================
            // STATISTICS
            // =====================================================

            item {

                Row(
                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    ProgressStatCard(
                        modifier = Modifier.weight(1f),
                        value = "7",
                        label = "Day Streak"
                    )

                    ProgressStatCard(
                        modifier = Modifier.weight(1f),
                        value = "120",
                        label = "Points"
                    )
                }
            }


            // =====================================================
            // WEEKLY PROGRESS
            // =====================================================

            item {

                Card(
                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(18.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = cardColor
                    ),

                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {

                        Text(
                            text = "Weekly Progress",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = primaryText
                        )

                        Spacer(
                            modifier = Modifier.height(18.dp)
                        )

                        WeeklyProgressRow(
                            day = "Monday",
                            percentage = 80
                        )

                        WeeklyProgressRow(
                            day = "Tuesday",
                            percentage = 60
                        )

                        WeeklyProgressRow(
                            day = "Wednesday",
                            percentage = 100
                        )

                        WeeklyProgressRow(
                            day = "Thursday",
                            percentage = 40
                        )

                        WeeklyProgressRow(
                            day = "Friday",
                            percentage = 70
                        )

                        WeeklyProgressRow(
                            day = "Saturday",
                            percentage = 50
                        )

                        WeeklyProgressRow(
                            day = "Sunday",
                            percentage = 60
                        )
                    }
                }
            }


            // =====================================================
            // ACHIEVEMENTS
            // =====================================================

            item {

                Card(
                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(18.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = cardColor
                    ),

                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {

                        Text(
                            text = "Achievements",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = primaryText
                        )

                        Spacer(
                            modifier = Modifier.height(15.dp)
                        )

                        AchievementRow(
                            icon = "🔥",
                            title = "7 Day Streak",
                            description = "Completed tasks for 7 days"
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        AchievementRow(
                            icon = "⭐",
                            title = "Task Master",
                            description = "Completed 25 tasks"
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        AchievementRow(
                            icon = "🎯",
                            title = "Goal Getter",
                            description = "Completed your first goal"
                        )
                    }
                }
            }


            item {

                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            }
        }
    }
}


// =============================================================
// STATISTICS CARD
// =============================================================

@Composable
fun ProgressStatCard(
    modifier: Modifier = Modifier,
    value: String,
    label: String
) {

    val cardColor = MaterialTheme.colorScheme.surface
    val primaryText = MaterialTheme.colorScheme.onSurface
    val secondaryText =
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)

    val purple = MaterialTheme.colorScheme.primary

    Card(
        modifier = modifier,

        shape = RoundedCornerShape(18.dp),

        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = value,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = purple
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = label,
                fontSize = 14.sp,
                color = secondaryText
            )
        }
    }
}


// =============================================================
// WEEKLY PROGRESS ROW
// =============================================================

@Composable
fun WeeklyProgressRow(
    day: String,
    percentage: Int
) {

    val primaryText = MaterialTheme.colorScheme.onSurface
    val secondaryText =
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)

    val purple = MaterialTheme.colorScheme.primary

    val progressTrack =
        MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            Text(
                text = day,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = primaryText
            )

            Text(
                text = "$percentage%",
                fontSize = 13.sp,
                color = secondaryText
            )
        }

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        LinearProgressIndicator(
            progress = percentage / 100f,

            modifier = Modifier
                .fillMaxWidth()
                .height(7.dp),

            color = purple,
            trackColor = progressTrack
        )
    }
}


// =============================================================
// ACHIEVEMENT ROW
// =============================================================

@Composable
fun AchievementRow(
    icon: String,
    title: String,
    description: String
) {

    val primaryText = MaterialTheme.colorScheme.onSurface
    val secondaryText =
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)

    val achievementBackground =
        MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)

    Row(
        modifier = Modifier.fillMaxWidth(),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(50.dp)
                .background(
                    color = achievementBackground,
                    shape = RoundedCornerShape(15.dp)
                ),

            contentAlignment = Alignment.Center
        ) {

            Text(
                text = icon,
                fontSize = 25.sp
            )
        }

        Spacer(
            modifier = Modifier.width(14.dp)
        )

        Column {

            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = primaryText
            )

            Spacer(
                modifier = Modifier.height(3.dp)
            )

            Text(
                text = description,
                fontSize = 13.sp,
                color = secondaryText
            )
        }
    }
}