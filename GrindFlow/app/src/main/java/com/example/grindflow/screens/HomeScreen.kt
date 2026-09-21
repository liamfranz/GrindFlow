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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HomeScreen(
    onScheduleClick: () -> Unit,
    onTasksClick: () -> Unit,
    onProgressClick: () -> Unit,
    onSettingsClick: () -> Unit
) {

    // Theme-aware colours
    val backgroundColor = MaterialTheme.colorScheme.background
    val cardColor = MaterialTheme.colorScheme.surface
    val textColor = MaterialTheme.colorScheme.onSurface
    val secondaryTextColor =
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)

    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary

    BackHandler {
        // Prevents accidental exit from Home
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Greeting
                item {
                    Column {

                        Text(
                            text = "Good morning 👋",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = "Let's get things done!",
                            fontSize = 15.sp,
                            color = secondaryTextColor
                        )
                    }
                }

                // Today's Progress
                item {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = cardColor
                        ),
                        elevation = CardDefaults.cardElevation(3.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {

                            Text(
                                text = "Today's Progress",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "3 of 5 tasks completed",
                                    color = secondaryTextColor,
                                    fontSize = 14.sp
                                )

                                Text(
                                    text = "60%",
                                    color = primaryColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }

                            Spacer(
                                modifier = Modifier.height(10.dp)
                            )

                            // Progress bar background
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(10.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.onSurface
                                            .copy(alpha = 0.12f),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                            ) {

                                // Progress bar
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.6f)
                                        .height(10.dp)
                                        .background(
                                            brush = Brush.horizontalGradient(
                                                listOf(
                                                    primaryColor,
                                                    secondaryColor
                                                )
                                            ),
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                )
                            }
                        }
                    }
                }

                // Today's Schedule title
                item {

                    Text(
                        text = "Today's Schedule",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                }

                // Schedule item 1
                item {

                    SchedulePreviewCard(
                        time = "09:00 AM",
                        title = "Software Development",
                        category = "College",
                        cardColor = cardColor,
                        textColor = textColor,
                        secondaryTextColor = secondaryTextColor
                    )
                }

                // Schedule item 2
                item {

                    SchedulePreviewCard(
                        time = "02:00 PM",
                        title = "Complete GrindFlow",
                        category = "Project",
                        cardColor = cardColor,
                        textColor = textColor,
                        secondaryTextColor = secondaryTextColor
                    )
                }

                // Quick Actions title
                item {

                    Text(
                        text = "Quick Actions",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                }

                // Quick Action buttons
                item {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        QuickActionButton(
                            icon = "📅",
                            label = "Schedule",
                            modifier = Modifier.weight(1f),
                            onClick = onScheduleClick
                        )

                        QuickActionButton(
                            icon = "✓",
                            label = "Tasks",
                            modifier = Modifier.weight(1f),
                            onClick = onTasksClick
                        )
                    }
                }
            }

            // Bottom navigation
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(cardColor)
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                NavigationItem(
                    icon = "⌂",
                    label = "Home",
                    selected = true,
                    onClick = {}
                )

                NavigationItem(
                    icon = "📅",
                    label = "Schedule",
                    selected = false,
                    onClick = onScheduleClick
                )

                NavigationItem(
                    icon = "✓",
                    label = "Tasks",
                    selected = false,
                    onClick = onTasksClick
                )

                NavigationItem(
                    icon = "📊",
                    label = "Progress",
                    selected = false,
                    onClick = onProgressClick
                )

                NavigationItem(
                    icon = "⚙",
                    label = "Settings",
                    selected = false,
                    onClick = onSettingsClick
                )
            }
        }
    }
}


@Composable
fun SchedulePreviewCard(
    time: String,
    title: String,
    category: String,
    cardColor: Color,
    textColor: Color,
    secondaryTextColor: Color
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Schedule indicator
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(8.dp)
                    )
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = category,
                    fontSize = 13.sp,
                    color = secondaryTextColor
                )
            }

            Text(
                text = time,
                fontSize = 13.sp,
                color = secondaryTextColor
            )
        }
    }
}


@Composable
fun QuickActionButton(
    icon: String,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = modifier.height(90.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = icon,
                fontSize = 24.sp
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = label,
                fontSize = 13.sp
            )
        }
    }
}


@Composable
fun NavigationItem(
    icon: String,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    TextButton(
        onClick = onClick
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = icon,
                fontSize = 22.sp,
                color = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                }
            )

            Text(
                text = label,
                fontSize = 11.sp,
                color = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                }
            )
        }
    }
}