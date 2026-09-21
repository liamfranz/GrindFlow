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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// ============================================================
// TASK DATA
// ============================================================

data class TaskItem(
    val title: String,
    val description: String,
    val dueDate: String,
    val dueTime: String,
    val category: String,
    val priority: String,
    val completed: Boolean = false
)


// ============================================================
// MAIN TASKS SCREEN
// ============================================================

@Composable
fun TasksScreen(
    onBackToHome: () -> Unit
) {

    var showAddTask by remember {
        mutableStateOf(false)
    }

    var editingIndex by remember {
        mutableIntStateOf(-1)
    }


    // ========================================================
    // SAMPLE TASKS
    // ========================================================

    val tasks = remember {

        mutableStateListOf(

            TaskItem(
                title = "Finish GrindFlow UI",
                description = "Complete the remaining screens for the Android application.",
                dueDate = "17 September 2026",
                dueTime = "14:00",
                category = "College",
                priority = "High",
                completed = false
            ),

            TaskItem(
                title = "Study Software Testing",
                description = "Review component testing and test automation.",
                dueDate = "17 September 2026",
                dueTime = "18:00",
                category = "Study",
                priority = "Medium",
                completed = false
            ),

            TaskItem(
                title = "Complete Workout",
                description = "Complete today's home workout.",
                dueDate = "17 September 2026",
                dueTime = "19:30",
                category = "Personal",
                priority = "Low",
                completed = true
            )
        )
    }


    // ========================================================
    // ANDROID BACK BUTTON
    // ========================================================

    BackHandler {

        if (showAddTask) {

            showAddTask = false
            editingIndex = -1

        } else {

            onBackToHome()
        }
    }


    // ========================================================
    // ADD / EDIT TASK SCREEN
    // ========================================================

    if (showAddTask) {

        val taskToEdit =
            if (editingIndex >= 0 && editingIndex < tasks.size) {
                tasks[editingIndex]
            } else {
                null
            }

        AddTaskScreen(

            existingTask = taskToEdit,

            onBack = {

                showAddTask = false
                editingIndex = -1
            },

            onSave = { updatedTask ->

                if (editingIndex >= 0 && editingIndex < tasks.size) {

                    tasks[editingIndex] = updatedTask

                } else {

                    tasks.add(updatedTask)
                }

                showAddTask = false
                editingIndex = -1
            }
        )

    } else {

        // ====================================================
        // TASK LIST
        // ====================================================

        TaskListScreen(

            tasks = tasks,

            onAddTask = {

                editingIndex = -1
                showAddTask = true
            },

            onEditTask = { index ->

                editingIndex = index
                showAddTask = true
            },

            onDeleteTask = { index ->

                if (index >= 0 && index < tasks.size) {
                    tasks.removeAt(index)
                }
            },

            onToggleComplete = { index ->

                if (index >= 0 && index < tasks.size) {

                    val task = tasks[index]

                    tasks[index] = task.copy(
                        completed = !task.completed
                    )
                }
            },

            onBackToHome = {
                onBackToHome()
            }
        )
    }
}


// ============================================================
// TASK LIST SCREEN
// ============================================================

@Composable
fun TaskListScreen(
    tasks: List<TaskItem>,
    onAddTask: () -> Unit,
    onEditTask: (Int) -> Unit,
    onDeleteTask: (Int) -> Unit,
    onToggleComplete: (Int) -> Unit,
    onBackToHome: () -> Unit
) {

    val purple = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    val textColor = MaterialTheme.colorScheme.onBackground

    val completedCount = tasks.count {
        it.completed
    }

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
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to Home",
                    tint = Color.White
                )
            }

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Column {

                Text(
                    text = "Tasks",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Manage your daily tasks",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp
                )
            }
        }


        // ====================================================
        // TASK SUMMARY
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
                text = "Today's Tasks",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )

            Text(
                text = "$completedCount/${tasks.size} completed",
                fontSize = 13.sp,
                color = purple,
                fontWeight = FontWeight.Bold
            )
        }


        // ====================================================
        // TASK LIST
        // ====================================================

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {

            itemsIndexed(tasks) { index, task ->

                TaskItemCard(

                    task = task,

                    purple = purple,

                    onToggleComplete = {
                        onToggleComplete(index)
                    },

                    onEdit = {
                        onEditTask(index)
                    },

                    onDelete = {
                        onDeleteTask(index)
                    }
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }


            // =================================================
            // ADD TASK BUTTON
            // =================================================

            item {

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Button(

                    onClick = onAddTask,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),

                    shape = RoundedCornerShape(14.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = purple
                    )
                ) {

                    Text(
                        text = "+  ADD TASK",
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
// TASK CARD
// ============================================================

@Composable
fun TaskItemCard(
    task: TaskItem,
    purple: Color,
    onToggleComplete: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

    var showMenu by remember {
        mutableStateOf(false)
    }

    val cardColor = MaterialTheme.colorScheme.surface
    val primaryText = MaterialTheme.colorScheme.onSurface
    val secondaryText = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                cardColor,
                RoundedCornerShape(16.dp)
            )
            .padding(14.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // =================================================
            // CHECKBOX
            // =================================================

            Checkbox(

                checked = task.completed,

                onCheckedChange = {
                    onToggleComplete()
                }
            )


            Spacer(
                modifier = Modifier.width(6.dp)
            )


            // =================================================
            // TASK DETAILS
            // =================================================

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = task.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,

                    color =
                        if (task.completed) {
                            secondaryText
                        } else {
                            primaryText
                        }
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = task.category,
                    fontSize = 12.sp,
                    color = purple,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                if (task.description.isNotEmpty()) {

                    Text(
                        text = task.description,
                        fontSize = 13.sp,
                        color = secondaryText
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )
                }

                Text(
                    text = "Due: ${task.dueDate} at ${task.dueTime}",
                    fontSize = 12.sp,
                    color = secondaryText
                )
            }


            // =================================================
            // MORE MENU
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
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
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


        // =====================================================
        // PRIORITY
        // =====================================================

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 48.dp,
                    top = 4.dp
                )
        ) {

            Text(
                text = "Priority: ${task.priority}",
                fontSize = 12.sp,
                color = secondaryText,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


// ============================================================
// ADD / EDIT TASK SCREEN
// ============================================================

@Composable
fun AddTaskScreen(
    existingTask: TaskItem? = null,
    onBack: () -> Unit,
    onSave: (TaskItem) -> Unit
) {

    val purple = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    val textColor = MaterialTheme.colorScheme.onBackground


    // ========================================================
    // FORM VALUES
    // ========================================================

    var title by remember {

        mutableStateOf(
            existingTask?.title ?: ""
        )
    }

    var description by remember {

        mutableStateOf(
            existingTask?.description ?: ""
        )
    }

    var dueDate by remember {

        mutableStateOf(
            existingTask?.dueDate ?: ""
        )
    }

    var dueTime by remember {

        mutableStateOf(
            existingTask?.dueTime ?: ""
        )
    }

    var category by remember {

        mutableStateOf(
            existingTask?.category ?: ""
        )
    }

    var priority by remember {

        mutableStateOf(
            existingTask?.priority ?: ""
        )
    }


    // ========================================================
    // ERROR MESSAGE
    // ========================================================

    var errorMessage by remember {
        mutableStateOf("")
    }


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
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Text(
                text =
                    if (existingTask == null) {
                        "Add Task"
                    } else {
                        "Edit Task"
                    },

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
                    text = "Task Details",
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
                        Text("Task Title")
                    },

                    placeholder = {
                        Text("e.g. Complete assignment")
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
                // DUE DATE
                // =================================================

                OutlinedTextField(

                    value = dueDate,

                    onValueChange = {

                        dueDate = it
                        errorMessage = ""
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Due Date")
                    },

                    placeholder = {
                        Text("e.g. 17 September 2026")
                    },

                    singleLine = true,

                    shape = RoundedCornerShape(14.dp)
                )


                Spacer(
                    modifier = Modifier.height(12.dp)
                )


                // =================================================
                // DUE TIME
                // =================================================

                OutlinedTextField(

                    value = dueTime,

                    onValueChange = {

                        dueTime = it
                        errorMessage = ""
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Due Time")
                    },

                    placeholder = {
                        Text("e.g. 14:00")
                    },

                    singleLine = true,

                    shape = RoundedCornerShape(14.dp)
                )


                Spacer(
                    modifier = Modifier.height(12.dp)
                )


                // =================================================
                // CATEGORY
                // =================================================

                OutlinedTextField(

                    value = category,

                    onValueChange = {

                        category = it
                        errorMessage = ""
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Category")
                    },

                    placeholder = {
                        Text("e.g. College, Study, Work")
                    },

                    singleLine = true,

                    shape = RoundedCornerShape(14.dp)
                )


                Spacer(
                    modifier = Modifier.height(12.dp)
                )


                // =================================================
                // PRIORITY
                // =================================================

                OutlinedTextField(

                    value = priority,

                    onValueChange = {

                        priority = it
                        errorMessage = ""
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Priority")
                    },

                    placeholder = {
                        Text("e.g. High, Medium, Low")
                    },

                    singleLine = true,

                    shape = RoundedCornerShape(14.dp)
                )


                Spacer(
                    modifier = Modifier.height(12.dp)
                )


                // =================================================
                // ERROR MESSAGE
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
                                    "Please enter a task title."
                            }

                            dueDate.isBlank() -> {

                                errorMessage =
                                    "Please enter a due date."
                            }

                            dueTime.isBlank() -> {

                                errorMessage =
                                    "Please enter a due time."
                            }

                            category.isBlank() -> {

                                errorMessage =
                                    "Please enter a category."
                            }

                            priority.isBlank() -> {

                                errorMessage =
                                    "Please enter a priority."
                            }

                            else -> {

                                onSave(

                                    TaskItem(

                                        title = title,

                                        description = description,

                                        dueDate = dueDate,

                                        dueTime = dueTime,

                                        category = category,

                                        priority = priority,

                                        completed =
                                            existingTask?.completed
                                                ?: false
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
                            if (existingTask == null) {
                                "SAVE TASK"
                            } else {
                                "UPDATE TASK"
                            },

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