
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.grindflow.data.GrindFlowDatabase

@Composable
fun ProgressScreen(
    onBackToHome: () -> Unit
) {
    val context = LocalContext.current
    val taskDao = remember(context) {
        GrindFlowDatabase.getDatabase(context).taskDao()
    }

    val tasks by taskDao.getAllTasks().collectAsState(initial = emptyList())

    val totalTasks = tasks.size
    val completedTasks = tasks.count { it.completed }
    val remainingTasks = totalTasks - completedTasks

    val completionFraction = if (totalTasks > 0) {
        completedTasks.toFloat() / totalTasks.toFloat()
    } else {
        0f
    }

    val completionPercentage = (completionFraction * 100).toInt()

    val textColor = MaterialTheme.colorScheme.onSurface
    val secondaryTextColor =
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
    val cardColor = MaterialTheme.colorScheme.surface
    val primaryColor = MaterialTheme.colorScheme.primary

    BackHandler {
        onBackToHome()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            TextButton(onClick = onBackToHome) {
                Text(text = "← Back to Home")
            }

            Column {
                Text(
                    text = "Your Progress",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Track your task completion and productivity.",
                    fontSize = 14.sp,
                    color = secondaryTextColor
                )
            }

            // Overall completion
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
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Overall Completion",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "$completedTasks of $totalTasks tasks",
                            fontSize = 14.sp,
                            color = secondaryTextColor
                        )

                        Text(
                            text = "$completionPercentage%",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = primaryColor
                        )
                    }

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
                                        color = primaryColor,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                            )
                        }
                    }

                    Text(
                        text = when {
                            totalTasks == 0 ->
                                "You have no tasks yet. Add a task to begin tracking your progress."

                            completedTasks == totalTasks ->
                                "Excellent work! You have completed all your saved tasks."

                            else ->
                                "You have $remainingTasks task(s) left. Keep going!"
                        },
                        fontSize = 14.sp,
                        color = secondaryTextColor
                    )
                }
            }

            Text(
                text = "Task Summary",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ProgressStatCard(
                    label = "Total",
                    value = totalTasks.toString(),
                    modifier = Modifier.weight(1f)
                )

                ProgressStatCard(
                    label = "Completed",
                    value = completedTasks.toString(),
                    modifier = Modifier.weight(1f)
                )

                ProgressStatCard(
                    label = "Remaining",
                    value = remainingTasks.toString(),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ProgressStatCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = value,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = label,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}
