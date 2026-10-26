
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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.grindflow.data.GrindFlowDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    onScheduleClick: () -> Unit,
    onTasksClick: () -> Unit,
    onProgressClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val context = LocalContext.current

    val database = remember(context) {
        GrindFlowDatabase.getDatabase(context)
    }

    // Observe task and schedule records from Room.
    val tasks by database.taskDao()
        .getAllTasks()
        .collectAsState(initial = emptyList())

    val schedules by database.scheduleDao()
        .getAllSchedules()
        .collectAsState(initial = emptyList())

    // Store dates in the same ISO format as ScheduleEntity.
    val todayIso = SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.US
    ).format(Date())

    val todaysSchedules = schedules
        .filter { it.date == todayIso }
        .sortedBy { it.startTime }

    // Calculate progress from actual saved task records.
    val totalTasks = tasks.size
    val completedTasks = tasks.count { it.completed }

    val completionFraction = if (totalTasks > 0) {
        completedTasks.toFloat() / totalTasks
    } else {
        0f
    }

    val completionPercentage = (completionFraction * 100).toInt()

    val backgroundColor = MaterialTheme.colorScheme.background
    val cardColor = MaterialTheme.colorScheme.surface
    val textColor = MaterialTheme.colorScheme.onSurface
    val secondaryTextColor =
        textColor.copy(alpha = 0.65f)
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary

    BackHandler {
        // Prevent accidental exit from Home.
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

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

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Let's get things done!",
                            fontSize = 15.sp,
                            color = secondaryTextColor
                        )
                    }
                }

                // Dynamic task progress
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = cardColor
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 3.dp
                        )
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                text = "Task Progress",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement =
                                    Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "$completedTasks of $totalTasks tasks completed",
                                    color = secondaryTextColor,
                                    fontSize = 14.sp
                                )

                                Text(
                                    text = "$completionPercentage%",
                                    color = primaryColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(10.dp)
                                    .background(
                                        color = textColor.copy(alpha = 0.12f),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                            ) {
                                if (completionFraction > 0f) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(completionFraction)
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

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = if (totalTasks == 0) {
                                    "Add a task to begin tracking your progress."
                                } else if (completedTasks == totalTasks) {
                                    "Great work! All your tasks are completed."
                                } else {
                                    "${totalTasks - completedTasks} tasks remaining."
                                },
                                fontSize = 13.sp,
                                color = secondaryTextColor
                            )
                        }
                    }
                }

                // Actual activities scheduled for today
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Today's Schedule",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )

                        TextButton(onClick = onScheduleClick) {
                            Text("View all")
                        }
                    }
                }

                if (todaysSchedules.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = cardColor
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp)
                            ) {
                                Text(
                                    text = "Nothing scheduled for today",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textColor
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "Add an activity with today's date to see it here.",
                                    fontSize = 13.sp,
                                    color = secondaryTextColor
                                )

                                TextButton(onClick = onScheduleClick) {
                                    Text("Add an activity")
                                }
                            }
                        }
                    }
                } else {
                    items(
                        items = todaysSchedules,
                        key = { it.id }
                    ) { schedule ->
                        SchedulePreviewCard(
                            time = "${schedule.startTime} - ${schedule.endTime}",
                            title = schedule.title,
                            category = schedule.category,
                            cardColor = cardColor,
                            textColor = textColor,
                            secondaryTextColor = secondaryTextColor
                        )
                    }
                }

                // Quick actions
                item {
                    Text(
                        text = "Quick Actions",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                }

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
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(8.dp)
                    )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = category,
                    fontSize = 13.sp,
                    color = secondaryTextColor
                )
            }

            Text(
                text = time,
                fontSize = 12.sp,
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
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = icon, fontSize = 24.sp)

            Spacer(modifier = Modifier.height(5.dp))

            Text(text = label, fontSize = 13.sp)
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
    TextButton(onClick = onClick) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
