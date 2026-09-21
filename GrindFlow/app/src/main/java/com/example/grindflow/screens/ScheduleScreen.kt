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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// ============================================================
// SCHEDULE DATA
// ============================================================

data class ScheduleItem(
    val title: String,
    val description: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val category: String,
    val reminder: String
)


// ============================================================
// MAIN SCHEDULE SCREEN
// ============================================================

@Composable
fun ScheduleScreen(
    onBackToHome: () -> Unit
) {

    var showAddSchedule by remember {
        mutableStateOf(false)
    }

    var editingIndex by remember {
        mutableIntStateOf(-1)
    }

    // ========================================================
    // ANDROID SYSTEM BACK BUTTON
    // ========================================================

    BackHandler {

        if (showAddSchedule) {

            showAddSchedule = false
            editingIndex = -1

        } else {

            onBackToHome()
        }
    }

    // ========================================================
    // SAMPLE SCHEDULE DATA
    // ========================================================

    val schedules = remember {

        mutableStateListOf(

            ScheduleItem(
                title = "Software Development",
                description = "Work on the GrindFlow application",
                date = "15 September 2026",
                startTime = "08:00",
                endTime = "10:00",
                category = "College",
                reminder = "10 minutes before"
            ),

            ScheduleItem(
                title = "Complete Assignment",
                description = "Work on software testing assignment",
                date = "15 September 2026",
                startTime = "11:00",
                endTime = "13:00",
                category = "Study",
                reminder = "15 minutes before"
            ),

            ScheduleItem(
                title = "Home Workout",
                description = "Complete home workout session",
                date = "15 September 2026",
                startTime = "17:00",
                endTime = "18:00",
                category = "Personal",
                reminder = "30 minutes before"
            )
        )
    }

    // ========================================================
    // ADD / EDIT SCREEN
    // ========================================================

    if (showAddSchedule) {

        val scheduleToEdit =
            if (
                editingIndex >= 0 &&
                editingIndex < schedules.size
            ) {
                schedules[editingIndex]
            } else {
                null
            }

        AddScheduleScreen(

            existingSchedule = scheduleToEdit,

            onBack = {

                showAddSchedule = false
                editingIndex = -1
            },

            onSave = { updatedSchedule ->

                if (
                    editingIndex >= 0 &&
                    editingIndex < schedules.size
                ) {

                    schedules[editingIndex] = updatedSchedule

                } else {

                    schedules.add(updatedSchedule)
                }

                showAddSchedule = false
                editingIndex = -1
            }
        )

    } else {

        // ====================================================
        // SCHEDULE LIST
        // ====================================================

        ScheduleListScreen(

            schedules = schedules,

            onAddSchedule = {

                editingIndex = -1
                showAddSchedule = true
            },

            onEditSchedule = { index ->

                editingIndex = index
                showAddSchedule = true
            },

            onDeleteSchedule = { index ->

                if (
                    index >= 0 &&
                    index < schedules.size
                ) {

                    schedules.removeAt(index)
                }
            },

            onBackToHome = {

                onBackToHome()
            }
        )
    }
}


// ============================================================
// SCHEDULE LIST
// ============================================================

@Composable
fun ScheduleListScreen(
    schedules: List<ScheduleItem>,
    onAddSchedule: () -> Unit,
    onEditSchedule: (Int) -> Unit,
    onDeleteSchedule: (Int) -> Unit,
    onBackToHome: () -> Unit
) {

    val purple = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    val cardColor = MaterialTheme.colorScheme.surface
    val textColor = MaterialTheme.colorScheme.onSurface
    val secondaryTextColor =
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {

        // ====================================================
        // HEADER
        // ====================================================

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(purple)
                .padding(
                    start = 8.dp,
                    end = 20.dp,
                    top = 15.dp,
                    bottom = 15.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {
                    onBackToHome()
                }
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back to Home",
                    tint = Color.White
                )
            }

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Column {

                Text(
                    text = "Schedule",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Plan and organise your activities",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp
                )
            }
        }

        // ====================================================
        // TITLE
        // ====================================================

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 15.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Today's Activities",
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

        // ====================================================
        // SCHEDULE LIST
        // ====================================================

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {

            itemsIndexed(schedules) { index, schedule ->

                ScheduleItemCard(

                    schedule = schedule,

                    purple = purple,

                    cardColor = cardColor,

                    textColor = textColor,

                    secondaryTextColor = secondaryTextColor,

                    onEdit = {
                        onEditSchedule(index)
                    },

                    onDelete = {
                        onDeleteSchedule(index)
                    }
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }

            // =================================================
            // ADD SCHEDULE BUTTON
            // =================================================

            item {

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Button(

                    onClick = onAddSchedule,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),

                    shape = RoundedCornerShape(14.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = purple
                    )
                ) {

                    Text(
                        text = "+  ADD SCHEDULE",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(25.dp)
                )
            }
        }
    }
}


// ============================================================
// SCHEDULE CARD
// ============================================================

@Composable
fun ScheduleItemCard(
    schedule: ScheduleItem,
    purple: Color,
    cardColor: Color,
    textColor: Color,
    secondaryTextColor: Color,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

    var showMenu by remember {
        mutableStateOf(false)
    }

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

            // =================================================
            // TIME
            // =================================================

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(75.dp)
            ) {

                Text(
                    text = schedule.startTime,
                    color = purple,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = schedule.endTime,
                    color = secondaryTextColor,
                    fontSize = 12.sp
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            // =================================================
            // DETAILS
            // =================================================

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = schedule.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = schedule.category,
                    fontSize = 12.sp,
                    color = purple,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                if (schedule.description.isNotEmpty()) {

                    Text(
                        text = schedule.description,
                        fontSize = 13.sp,
                        color = secondaryTextColor
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )
                }

                Text(
                    text = "Date: ${schedule.date}",
                    fontSize = 12.sp,
                    color = secondaryTextColor
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "🔔 ${schedule.reminder}",
                    fontSize = 12.sp,
                    color = secondaryTextColor
                )
            }

            // =================================================
            // MENU
            // =================================================

            Box {

                IconButton(
                    onClick = {
                        showMenu = true
                    }
                ) {

                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = textColor
                    )
                }

                DropdownMenu(

                    expanded = showMenu,

                    onDismissRequest = {
                        showMenu = false
                    }
                ) {

                    // EDIT
                    DropdownMenuItem(

                        text = {
                            Text("Edit")
                        },

                        leadingIcon = {

                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit"
                            )
                        },

                        onClick = {

                            showMenu = false

                            onEdit()
                        }
                    )

                    // DELETE
                    DropdownMenuItem(

                        text = {
                            Text("Delete")
                        },

                        leadingIcon = {

                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        },

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


// ============================================================
// ADD / EDIT SCHEDULE SCREEN
// ============================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleScreen(
    existingSchedule: ScheduleItem? = null,
    onBack: () -> Unit,
    onSave: (ScheduleItem) -> Unit
) {

    val purple = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    val textColor = MaterialTheme.colorScheme.onSurface
    val secondaryTextColor =
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)

    // ========================================================
    // FORM VALUES
    // ========================================================

    var title by remember {
        mutableStateOf(
            existingSchedule?.title ?: ""
        )
    }

    var description by remember {
        mutableStateOf(
            existingSchedule?.description ?: ""
        )
    }

    var date by remember {
        mutableStateOf(
            existingSchedule?.date ?: ""
        )
    }

    var startTime by remember {
        mutableStateOf(
            existingSchedule?.startTime ?: ""
        )
    }

    var endTime by remember {
        mutableStateOf(
            existingSchedule?.endTime ?: ""
        )
    }

    var category by remember {
        mutableStateOf(
            existingSchedule?.category ?: ""
        )
    }

    var reminder by remember {
        mutableStateOf(
            existingSchedule?.reminder ?: ""
        )
    }

    // ========================================================
    // DROPDOWN STATES
    // ========================================================

    var showCategoryMenu by remember {
        mutableStateOf(false)
    }

    var showReminderMenu by remember {
        mutableStateOf(false)
    }

    // ========================================================
    // ERROR
    // ========================================================

    var errorMessage by remember {
        mutableStateOf("")
    }

    // ========================================================
    // OPTIONS
    // ========================================================

    val categories = listOf(
        "College",
        "Study",
        "Work",
        "Personal",
        "Other"
    )

    val reminders = listOf(
        "No reminder",
        "5 minutes before",
        "10 minutes before",
        "15 minutes before",
        "30 minutes before",
        "1 hour before"
    )

    // ========================================================
    // SCREEN
    // ========================================================

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {

        // ====================================================
        // HEADER
        // ====================================================

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(purple)
                .padding(
                    start = 8.dp,
                    end = 20.dp,
                    top = 10.dp,
                    bottom = 10.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {
                    onBack()
                }
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Text(
                text =
                    if (existingSchedule == null)
                        "Add Schedule"
                    else
                        "Edit Schedule",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // ====================================================
        // FORM
        // ====================================================

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(20.dp)
        ) {

            item {

                Text(
                    text = "Activity Details",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                Spacer(
                    modifier = Modifier.height(15.dp)
                )

                // =================================================
                // TITLE
                // =================================================

                OutlinedTextField(

                    value = title,

                    onValueChange = {

                        title = it
                        errorMessage = ""
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Activity Title")
                    },

                    placeholder = {
                        Text("e.g. Study for test")
                    },

                    singleLine = true,

                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // =================================================
                // DESCRIPTION
                // =================================================

                OutlinedTextField(

                    value = description,

                    onValueChange = {

                        description = it
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),

                    label = {
                        Text("Description")
                    },

                    placeholder = {
                        Text("Add some details...")
                    },

                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // =================================================
                // DATE
                // =================================================

                OutlinedTextField(

                    value = date,

                    onValueChange = {

                        date = it
                        errorMessage = ""
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Date")
                    },

                    placeholder = {
                        Text("e.g. 15 September 2026")
                    },

                    singleLine = true,

                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // =================================================
                // START TIME
                // =================================================

                OutlinedTextField(

                    value = startTime,

                    onValueChange = {

                        startTime = it
                        errorMessage = ""
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Start Time")
                    },

                    placeholder = {
                        Text("e.g. 08:00")
                    },

                    singleLine = true,

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),

                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // =================================================
                // END TIME
                // =================================================

                OutlinedTextField(

                    value = endTime,

                    onValueChange = {

                        endTime = it
                        errorMessage = ""
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("End Time")
                    },

                    placeholder = {
                        Text("e.g. 10:00")
                    },

                    singleLine = true,

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),

                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // =================================================
                // CATEGORY
                // =================================================

                ExposedDropdownMenuBox(

                    expanded = showCategoryMenu,

                    onExpandedChange = {

                        showCategoryMenu =
                            !showCategoryMenu
                    }
                ) {

                    OutlinedTextField(

                        value = category,

                        onValueChange = {},

                        readOnly = true,

                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(
                                type = MenuAnchorType.PrimaryNotEditable,
                                enabled = true
                            ),

                        label = {
                            Text("Category")
                        },

                        placeholder = {
                            Text("Select a category")
                        },

                        trailingIcon = {

                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = showCategoryMenu
                            )
                        },

                        shape = RoundedCornerShape(14.dp)
                    )

                    ExposedDropdownMenu(

                        expanded = showCategoryMenu,

                        onDismissRequest = {

                            showCategoryMenu = false
                        }
                    ) {

                        categories.forEach { item ->

                            DropdownMenuItem(

                                text = {
                                    Text(item)
                                },

                                onClick = {

                                    category = item

                                    showCategoryMenu = false

                                    errorMessage = ""
                                }
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // =================================================
                // REMINDER
                // =================================================

                ExposedDropdownMenuBox(

                    expanded = showReminderMenu,

                    onExpandedChange = {

                        showReminderMenu =
                            !showReminderMenu
                    }
                ) {

                    OutlinedTextField(

                        value = reminder,

                        onValueChange = {},

                        readOnly = true,

                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(
                                type = MenuAnchorType.PrimaryNotEditable,
                                enabled = true
                            ),

                        label = {
                            Text("Reminder")
                        },

                        placeholder = {
                            Text("Select reminder")
                        },

                        trailingIcon = {

                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = showReminderMenu
                            )
                        },

                        shape = RoundedCornerShape(14.dp)
                    )

                    ExposedDropdownMenu(

                        expanded = showReminderMenu,

                        onDismissRequest = {

                            showReminderMenu = false
                        }
                    ) {

                        reminders.forEach { item ->

                            DropdownMenuItem(

                                text = {
                                    Text(item)
                                },

                                onClick = {

                                    reminder = item

                                    showReminderMenu = false

                                    errorMessage = ""
                                }
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // =================================================
                // ERROR
                // =================================================

                if (errorMessage.isNotEmpty()) {

                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                }

                // =================================================
                // SAVE BUTTON
                // =================================================

                Button(

                    onClick = {

                        when {

                            title.isBlank() -> {

                                errorMessage =
                                    "Please enter an activity title."
                            }

                            date.isBlank() -> {

                                errorMessage =
                                    "Please enter a date."
                            }

                            startTime.isBlank() -> {

                                errorMessage =
                                    "Please enter a start time."
                            }

                            endTime.isBlank() -> {

                                errorMessage =
                                    "Please enter an end time."
                            }

                            category.isBlank() -> {

                                errorMessage =
                                    "Please select a category."
                            }

                            reminder.isBlank() -> {

                                errorMessage =
                                    "Please select a reminder."
                            }

                            else -> {

                                onSave(

                                    ScheduleItem(

                                        title = title,

                                        description = description,

                                        date = date,

                                        startTime = startTime,

                                        endTime = endTime,

                                        category = category,

                                        reminder = reminder
                                    )
                                )
                            }
                        }
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),

                    shape = RoundedCornerShape(14.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = purple
                    )
                ) {

                    Text(
                        text =
                            if (existingSchedule == null)
                                "SAVE SCHEDULE"
                            else
                                "UPDATE SCHEDULE",

                        color = Color.White,

                        fontSize = 15.sp,

                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(25.dp)
                )
            }
        }
    }
}