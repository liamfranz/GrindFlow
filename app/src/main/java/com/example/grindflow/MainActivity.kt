package com.example.grindflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.grindflow.screens.HomeScreen
import com.example.grindflow.screens.LoginScreen
import com.example.grindflow.screens.ProgressScreen
import com.example.grindflow.screens.RegisterScreen
import com.example.grindflow.screens.ScheduleScreen
import com.example.grindflow.screens.SettingsScreen
import com.example.grindflow.screens.TasksScreen
import com.example.grindflow.ui.theme.GrindFlowTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GrindFlowApp()
        }
    }
}

@Composable
fun GrindFlowApp() {

    var currentScreen by remember {
        mutableStateOf("login")
    }

    var selectedLanguage by remember {
        mutableStateOf(AppLanguage.ENGLISH)
    }

    var darkModeEnabled by remember {
        mutableStateOf(false)
    }

    LanguageManager.currentLanguage = selectedLanguage

    GrindFlowTheme(
        darkTheme = darkModeEnabled
    ) {

        when (currentScreen) {

            "login" -> {
                LoginScreen(
                    onLogin = {
                        currentScreen = "home"
                    },
                    onCreateAccount = {
                        currentScreen = "register"
                    }
                )
            }

            "register" -> {
                RegisterScreen(
                    onBackToLogin = {
                        currentScreen = "login"
                    },
                    onRegisterSuccess = {
                        currentScreen = "login"
                    }
                )
            }

            "home" -> {
                HomeScreen(
                    onScheduleClick = {
                        currentScreen = "schedule"
                    },
                    onTasksClick = {
                        currentScreen = "tasks"
                    },
                    onProgressClick = {
                        currentScreen = "progress"
                    },
                    onSettingsClick = {
                        currentScreen = "settings"
                    }
                )
            }

            "schedule" -> {
                ScheduleScreen(
                    onBackToHome = {
                        currentScreen = "home"
                    }
                )
            }

            "tasks" -> {
                TasksScreen(
                    onBackToHome = {
                        currentScreen = "home"
                    }
                )
            }

            "progress" -> {
                ProgressScreen(
                    onBackToHome = {
                        currentScreen = "home"
                    }
                )
            }

            "settings" -> {
                SettingsScreen(
                    onBackToHome = {
                        currentScreen = "home"
                    },
                    onLogout = {
                        currentScreen = "login"
                    },
                    selectedLanguage = selectedLanguage,
                    onLanguageChange = {
                        selectedLanguage = it
                    },
                    darkModeEnabled = darkModeEnabled,
                    onDarkModeChange = {
                        darkModeEnabled = it
                    }
                )
            }
        }
    }
}