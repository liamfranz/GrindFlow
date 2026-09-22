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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.example.grindflow.data.GrindFlowDatabase
import com.example.grindflow.data.TaskEntity
import kotlinx.coroutines.launch


data class TaskItem(
    val id: Int = 0,
    val title: String,
    val description: String,
    val dueDate: String,
    val dueTime: String,
    val category: String,
    val priority: String,
    val completed: Boolean = false
)


@Composable
fun TasksScreen(
    onBackToHome: () -> Unit
) {

    val context = LocalContext.current

    val database = remember {
        GrindFlowDatabase.getDatabase(context)
    }

    val taskDao = database.taskDao()

    val tasksFromDatabase by taskDao
        .getAllTasks()
        .collectAsState(initial = emptyList())

    val scope = rememberCoroutineScope()

    var showAddTask by remember {
        mutableStateOf(false)
    }

    var editingTask by remember {
        mutableStateOf<TaskItem?>(null)
    }

    val tasks = tasksFromDatabase.map { task ->

        TaskItem(
            id = task.id,
            title = task.title,
            description = task.description,
            dueDate = task.date,
            dueTime = task.time,
            category = task.category,
            priority = task.priority,
            completed = task.completed
        )
    }

    BackHandler {

        if (showAddTask) {

            showAddTask = false
            editingTask = null

        } else {

            onBackToHome()
        }
    }

    if (showAddTask) {

        AddTaskScreen(
            existingTask = editingTask,

            onBack = {
                showAddTask = false
                editingTask = null
            },

            onSave = { updatedTask ->

                scope.launch {

                    if (updatedTask.id == 0) {

                        taskDao.insertTask(
                            TaskEntity(
                                title = updatedTask.title,
                                description = updatedTask.description,
                                date = updatedTask.dueDate,
                                time = updatedTask.dueTime,
                                category = updatedTask.category,
                                priority = updatedTask.priority,
                                completed = updatedTask.completed
                            )
                        )

                    } else {

                        taskDao.updateTask(
                            TaskEntity(
                                id = updatedTask.id,
                                title = updatedTask.title,
                                description = updatedTask.description,
                                date = updatedTask.dueDate,
                                time = updatedTask.dueTime,
                                category = updatedTask.category,
                                priority = updatedTask.priority,
                                completed = updatedTask.completed
                            )
                        )
                    }
                }

                showAddTask = false
                editingTask = null
            }
        )

    } else {

        TaskListScreen(

            tasks = tasks,

            onAddTask = {
                editingTask = null
                showAddTask = true
            },

            onEditTask = { task ->
                editingTask = task
                showAddTask = true
            },

            onDeleteTask = { task ->

                scope.launch {

                    taskDao.deleteTask(
                        TaskEntity(
                            id = task.id,
                            title = task.title,
                            description = task.description,
                            date = task.dueDate,
                            time = task.dueTime,
                            category = task.category,
                            priority = task.priority,
                            completed = task.completed
                        )
                    )
                }
            },

            onToggleComplete = { task ->

                scope.launch {

                    taskDao.updateTask(
                        TaskEntity(
                            id = task.id,
                            title = task.title,
                            description = task.description,
                            date = task.dueDate,
                            time = task.dueTime,
                            category = task.category,
                            priority = task.priority,
                            completed = !task.completed
                        )
                    )
                }
            },

            onBackToHome = {
                onBackToHome()
            }
        )
    }
}


@Composable
fun TaskListScreen(
    tasks: List<TaskItem>,
    onAddTask: () -> Unit,
    onEditTask: (TaskItem) -> Unit,
    onDeleteTask: (TaskItem) -> Unit,
    onToggleComplete: (TaskItem) -> Unit,
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

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {

            itemsIndexed(
                items = tasks,
                key = { _, task ->
                    task.id
                }
            ) { _, task ->

                TaskItemCard(

                    task = task,

                    purple = purple,

                    onToggleComplete = {
                        onToggleComplete(task)
                    },

                    onEdit = {
                        onEditTask(task)
                    },

                    onDelete = {
                        onDeleteTask(task)
                    }
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }

            if (tasks.isEmpty()) {

                item {

                    Spacer(
                        modifier = Modifier.height(40.dp)
                    )

                    Text(
                        text = "No tasks yet.\nAdd your first task below.",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(
                            alpha = 0.6f
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )
                }
            }

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
    val secondaryText =
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)

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

            Checkbox(

                checked = task.completed,

                onCheckedChange = {
                    onToggleComplete()
                }
            )

            Spacer(
                modifier = Modifier.width(6.dp)
            )

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

            Box {

                IconButton(
                    onClick = {
                        showMenu = true
                    }
                ) {

                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.75f
                        )
                    )
                }

                DropdownMenu(

                    expanded = showMenu,

                    onDismissRequest = {
                        showMenu = false
                    }
                ) {

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


@Composable
fun AddTaskScreen(
    existingTask: TaskItem? = null,
    onBack: () -> Unit,
    onSave: (TaskItem) -> Unit
) {

    val purple = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    val textColor = MaterialTheme.colorScheme.onBackground

    var title by remember {
        mutableStateOf(existingTask?.title ?: "")
    }

    var description by remember {
        mutableStateOf(existingTask?.description ?: "")
    }

    var dueDate by remember {
        mutableStateOf(existingTask?.dueDate ?: "")
    }

    var dueTime by remember {
        mutableStateOf(existingTask?.dueTime ?: "")
    }

    var category by remember {
        mutableStateOf(existingTask?.category ?: "")
    }

    var priority by remember {
        mutableStateOf(existingTask?.priority ?: "")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {

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

                                        id = existingTask?.id ?: 0,

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