
package com.example.grindflow.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.example.grindflow.data.GrindFlowDatabase
import com.example.grindflow.data.ScheduleEntity
import kotlinx.coroutines.launch
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ScheduleScreen(
    onBackToHome: () -> Unit
) {
    val context = LocalContext.current
    val database = remember(context) {
        GrindFlowDatabase.getDatabase(context)
    }
    val scheduleDao = database.scheduleDao()
    val schedules by scheduleDao.getAllSchedules()
        .collectAsState(initial = emptyList())

    val scope = rememberCoroutineScope()

    var showForm by remember { mutableStateOf(false) }
    var editingSchedule by remember {
        mutableStateOf<ScheduleEntity?>(null)
    }

    BackHandler {
        if (showForm) {
            showForm = false
            editingSchedule = null
        } else {
            onBackToHome()
        }
    }

    if (showForm) {
        ScheduleFormScreen(
            existingSchedule = editingSchedule,
            onBack = {
                showForm = false
                editingSchedule = null
            },
            onSave = { schedule ->
                scope.launch {
                    if (schedule.id == 0) {
                        scheduleDao.insertSchedule(schedule)
                    } else {
                        scheduleDao.updateSchedule(schedule)
                    }

                    showForm = false
                    editingSchedule = null
                }
            }
        )
    } else {
        ScheduleListScreen(
            schedules = schedules,
            onAddSchedule = {
                editingSchedule = null
                showForm = true
            },
            onEditSchedule = { schedule ->
                editingSchedule = schedule
                showForm = true
            },
            onDeleteSchedule = { schedule ->
                scope.launch {
                    scheduleDao.deleteSchedule(schedule)
                }
            },
            onBackToHome = onBackToHome
        )
    }
}

@Composable
private fun ScheduleListScreen(
    schedules: List<ScheduleEntity>,
    onAddSchedule: () -> Unit,
    onEditSchedule: (ScheduleEntity) -> Unit,
    onDeleteSchedule: (ScheduleEntity) -> Unit,
    onBackToHome: () -> Unit
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val textColor = MaterialTheme.colorScheme.onSurface
    val secondaryTextColor =
        textColor.copy(alpha = 0.65f)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryColor)
                    .padding(horizontal = 20.dp, vertical = 18.dp)
            ) {
                TextButton(onClick = onBackToHome) {
                    Text(
                        text = "← Back to Home",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Text(
                    text = "Schedule",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Plan and organise your activities",
                    color = MaterialTheme.colorScheme.onPrimary
                        .copy(alpha = 0.85f),
                    fontSize = 14.sp
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "All Activities",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                Text(
                    text = "${schedules.size} activities",
                    fontSize = 13.sp,
                    color = secondaryTextColor
                )
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (schedules.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor =
                                    MaterialTheme.colorScheme.surface
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "No activities yet",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = textColor
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "Add an activity to start organising your schedule.",
                                    color = secondaryTextColor,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }

                items(
                    items = schedules,
                    key = { it.id }
                ) { schedule ->
                    ScheduleActivityCard(
                        schedule = schedule,
                        onEdit = { onEditSchedule(schedule) },
                        onDelete = { onDeleteSchedule(schedule) }
                    )
                }

                item {
                    Button(
                        onClick = onAddSchedule,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor
                        )
                    ) {
                        Text(
                            text = "+  ADD ACTIVITY",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun ScheduleActivityCard(
    schedule: ScheduleEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showMenu by remember(schedule.id) {
        mutableStateOf(false)
    }

    val primaryColor = MaterialTheme.colorScheme.primary
    val textColor = MaterialTheme.colorScheme.onSurface
    val secondaryTextColor =
        textColor.copy(alpha = 0.65f)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.width(76.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = schedule.startTime,
                    color = primaryColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = schedule.endTime,
                    color = secondaryTextColor,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = schedule.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                Text(
                    text = schedule.category,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor
                )

                if (schedule.description.isNotBlank()) {
                    Text(
                        text = schedule.description,
                        fontSize = 13.sp,
                        color = secondaryTextColor
                    )
                }

                Text(
                    text = "Date: ${displayScheduleDate(schedule.date)}",
                    fontSize = 12.sp,
                    color = secondaryTextColor
                )

                if (schedule.reminder != "No reminder") {
                    Text(
                        text = "🔔 ${schedule.reminder}",
                        fontSize = 12.sp,
                        color = secondaryTextColor
                    )
                }
            }

            Column {
                TextButton(onClick = { showMenu = true }) {
                    Text("More", color = primaryColor)
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Edit") },
                        onClick = {
                            showMenu = false
                            onEdit()
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Delete") },
                        onClick = {
                            showMenu = false
                            onDelete()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ScheduleFormScreen(
    existingSchedule: ScheduleEntity?,
    onBack: () -> Unit,
    onSave: (ScheduleEntity) -> Unit
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val textColor = MaterialTheme.colorScheme.onSurface

    var title by remember(existingSchedule?.id) {
        mutableStateOf(existingSchedule?.title ?: "")
    }
    var description by remember(existingSchedule?.id) {
        mutableStateOf(existingSchedule?.description ?: "")
    }
    var date by remember(existingSchedule?.id) {
        mutableStateOf(
            existingSchedule?.date?.let {
                displayScheduleDate(it)
            } ?: ""
        )
    }
    var startTime by remember(existingSchedule?.id) {
        mutableStateOf(existingSchedule?.startTime ?: "")
    }
    var endTime by remember(existingSchedule?.id) {
        mutableStateOf(existingSchedule?.endTime ?: "")
    }
    var category by remember(existingSchedule?.id) {
        mutableStateOf(existingSchedule?.category ?: "Personal")
    }
    var reminder by remember(existingSchedule?.id) {
        mutableStateOf(
            existingSchedule?.reminder ?: "No reminder"
        )
    }
    var errorMessage by remember(existingSchedule?.id) {
        mutableStateOf("")
    }

    val categories = listOf(
        "College", "Study", "Work", "Personal", "Other"
    )
    val reminders = listOf(
        "No reminder",
        "5 minutes before",
        "10 minutes before",
        "15 minutes before",
        "30 minutes before",
        "1 hour before"
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryColor)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                TextButton(onClick = onBack) {
                    Text(
                        text = "← Back",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Text(
                    text = if (existingSchedule == null) {
                        "Add Activity"
                    } else {
                        "Edit Activity"
                    },
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        errorMessage = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Activity Title") },
                    placeholder = { Text("e.g. Study for test") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Description") },
                    placeholder = { Text("Add some details") },
                    minLines = 3,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = date,
                    onValueChange = {
                        date = it
                        errorMessage = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Date") },
                    placeholder = { Text("2026-10-26 or 26 October 2026") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = startTime,
                    onValueChange = {
                        startTime = it
                        errorMessage = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Start Time (24-hour format)") },
                    placeholder = { Text("e.g. 08:00") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = endTime,
                    onValueChange = {
                        endTime = it
                        errorMessage = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("End Time (24-hour format)") },
                    placeholder = { Text("e.g. 10:00") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Category") },
                    placeholder = { Text(categories.joinToString(", ")) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                Text(
                    text = "Suggested categories: ${categories.joinToString(", ")}",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f),
                    fontSize = 12.sp
                )

                OutlinedTextField(
                    value = reminder,
                    onValueChange = { reminder = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Reminder") },
                    placeholder = { Text(reminders.joinToString(", ")) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                Text(
                    text = "Suggested reminders: ${reminders.joinToString(", ")}",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f),
                    fontSize = 12.sp
                )

                if (errorMessage.isNotBlank()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 14.sp
                    )
                }

                Button(
                    onClick = {
                        if (title.isBlank()) {
                            errorMessage = "Please enter an activity title."
                            return@Button
                        }

                        val normalizedDate = parseScheduleDate(date)
                        if (normalizedDate == null) {
                            errorMessage =
                                "Enter a valid date, such as 2026-10-26."
                            return@Button
                        }

                        val startMinutes = parseTimeMinutes(startTime)
                        if (startMinutes == null) {
                            errorMessage =
                                "Enter a valid start time in HH:mm format, e.g. 08:00."
                            return@Button
                        }

                        val endMinutes = parseTimeMinutes(endTime)
                        if (endMinutes == null) {
                            errorMessage =
                                "Enter a valid end time in HH:mm format, e.g. 10:00."
                            return@Button
                        }

                        if (endMinutes <= startMinutes) {
                            errorMessage =
                                "End time must be later than start time."
                            return@Button
                        }

                        if (category.isBlank()) {
                            errorMessage = "Please enter a category."
                            return@Button
                        }

                        onSave(
                            ScheduleEntity(
                                id = existingSchedule?.id ?: 0,
                                title = title.trim(),
                                description = description.trim(),
                                date = normalizedDate,
                                startTime = startTime.trim(),
                                endTime = endTime.trim(),
                                category = category.trim(),
                                reminder = reminder.trim().ifBlank {
                                    "No reminder"
                                }
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor
                    )
                ) {
                    Text(
                        text = if (existingSchedule == null) {
                            "SAVE ACTIVITY"
                        } else {
                            "UPDATE ACTIVITY"
                        },
                        fontWeight = FontWeight.Bold
                    )
                }

                TextButton(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancel", color = textColor)
                }
            }
        }
    }
}

private fun parseScheduleDate(input: String): String? {
    val trimmed = input.trim()

    val patterns = listOf(
        "yyyy-MM-dd",
        "d MMMM yyyy",
        "dd MMMM yyyy",
        "d MMM yyyy",
        "dd/MM/yyyy"
    )

    for (pattern in patterns) {
        val formatter = SimpleDateFormat(pattern, Locale.ENGLISH)
        formatter.isLenient = false

        val position = ParsePosition(0)
        val parsed = formatter.parse(trimmed, position)

        if (parsed != null && position.index == trimmed.length) {
            return SimpleDateFormat("yyyy-MM-dd", Locale.US)
                .format(parsed)
        }
    }

    return null
}

private fun parseTimeMinutes(input: String): Int? {
    val time = input.trim()
    if (!time.matches(Regex("\\d{2}:\\d{2}"))) return null

    val parts = time.split(":")
    val hour = parts[0].toIntOrNull() ?: return null
    val minute = parts[1].toIntOrNull() ?: return null

    if (hour !in 0..23 || minute !in 0..59) return null

    return hour * 60 + minute
}

private fun displayScheduleDate(isoDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US).apply {
        isLenient = false
    }

    val position = ParsePosition(0)
    val parsed = inputFormat.parse(isoDate, position)
        ?: return isoDate

    if (position.index != isoDate.length) return isoDate

    return SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
        .format(parsed)
}
