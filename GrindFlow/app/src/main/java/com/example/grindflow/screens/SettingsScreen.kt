package com.example.grindflow.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.grindflow.AppLanguage

@Composable
fun SettingsScreen(
    onBackToHome: () -> Unit,
    onLogout: () -> Unit,
    selectedLanguage: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit,
    darkModeEnabled: Boolean,
    onDarkModeChange: (Boolean) -> Unit
) {

    BackHandler {
        onBackToHome()
    }

    var notificationsEnabled by remember {
        mutableStateOf(true)
    }

    var showLanguageDialog by remember {
        mutableStateOf(false)
    }

    // Colours change depending on Dark Mode
    val backgroundColor =
        if (darkModeEnabled) Color(0xFF121212)
        else Color(0xFFF8F5FC)

    val cardColor =
        if (darkModeEnabled) Color(0xFF1E1E1E)
        else Color.White

    val mainTextColor =
        if (darkModeEnabled) Color.White
        else Color(0xFF333333)

    val secondaryTextColor =
        if (darkModeEnabled) Color(0xFFBDBDBD)
        else Color.Gray

    val languageSelectedColor =
        if (darkModeEnabled) Color(0xFF35243F)
        else Color(0xFFF1E5F7)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {

        // Header
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
                    text = "Settings",
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // Profile
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(55.dp)
                                .background(
                                    Color(0xFFE8D5F2),
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = Color(0xFF8E44AD),
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Spacer(
                            modifier = Modifier.width(15.dp)
                        )

                        Column {

                            Text(
                                text = "GrindFlow User",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = mainTextColor
                            )

                            Spacer(
                                modifier = Modifier.height(3.dp)
                            )

                            Text(
                                text = "user@example.com",
                                fontSize = 14.sp,
                                color = secondaryTextColor
                            )
                        }
                    }
                }
            }

            // General
            item {
                Text(
                    text = "General",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = mainTextColor
                )
            }

            // Notifications
            item {
                SettingsSwitchItem(
                    icon = "🔔",
                    title = "Notifications",
                    description = "Receive reminders and task notifications",
                    checked = notificationsEnabled,
                    onCheckedChange = {
                        notificationsEnabled = it
                    },
                    cardColor = cardColor,
                    textColor = mainTextColor,
                    secondaryTextColor = secondaryTextColor
                )
            }

            // Dark Mode
            item {
                SettingsSwitchItem(
                    icon = "🌙",
                    title = "Dark Mode",
                    description = "Use a dark appearance",
                    checked = darkModeEnabled,
                    onCheckedChange = {
                        onDarkModeChange(it)
                    },
                    cardColor = cardColor,
                    textColor = mainTextColor,
                    secondaryTextColor = secondaryTextColor
                )
            }

            // Language
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "🌐",
                            fontSize = 25.sp
                        )

                        Spacer(
                            modifier = Modifier.width(15.dp)
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = "Language",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = mainTextColor
                            )

                            Spacer(
                                modifier = Modifier.height(3.dp)
                            )

                            Text(
                                text = when (selectedLanguage) {
                                    AppLanguage.ENGLISH -> "English"
                                    AppLanguage.AFRIKAANS -> "Afrikaans"
                                    AppLanguage.ISIXHOSA -> "isiXhosa"
                                },
                                fontSize = 12.sp,
                                color = secondaryTextColor
                            )
                        }

                        TextButton(
                            onClick = {
                                showLanguageDialog = true
                            }
                        ) {
                            Text(
                                text = "Change",
                                color = Color(0xFFB66DDA),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Calendar
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "📅",
                            fontSize = 25.sp
                        )

                        Spacer(
                            modifier = Modifier.width(15.dp)
                        )

                        Column {

                            Text(
                                text = "Calendar",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = mainTextColor
                            )

                            Spacer(
                                modifier = Modifier.height(3.dp)
                            )

                            Text(
                                text = "Manage calendar preferences",
                                fontSize = 12.sp,
                                color = secondaryTextColor
                            )
                        }
                    }
                }
            }

            // Account
            item {
                Text(
                    text = "Account",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = mainTextColor,
                    modifier = Modifier.padding(top = 5.dp)
                )
            }

            // Logout
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

                    TextButton(
                        onClick = onLogout,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {

                        Text(
                            text = "Log Out",
                            color = Color(0xFFEF5350),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Footer
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 10.dp,
                            bottom = 20.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "GrindFlow",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = secondaryTextColor
                    )

                    Spacer(
                        modifier = Modifier.height(3.dp)
                    )

                    Text(
                        text = "Plan your day. Complete your goals.",
                        fontSize = 12.sp,
                        color = secondaryTextColor
                    )
                }
            }
        }
    }

    // Language Dialog
    if (showLanguageDialog) {

        AlertDialog(
            onDismissRequest = {
                showLanguageDialog = false
            },

            title = {
                Text(
                    text = "Select Language",
                    fontWeight = FontWeight.Bold
                )
            },

            text = {

                Column {

                    LanguageOption(
                        language = "English",
                        selected = selectedLanguage == AppLanguage.ENGLISH,
                        onClick = {
                            onLanguageChange(AppLanguage.ENGLISH)
                            showLanguageDialog = false
                        },
                        selectedColor = languageSelectedColor,
                        textColor = mainTextColor
                    )

                    LanguageOption(
                        language = "Afrikaans",
                        selected = selectedLanguage == AppLanguage.AFRIKAANS,
                        onClick = {
                            onLanguageChange(AppLanguage.AFRIKAANS)
                            showLanguageDialog = false
                        },
                        selectedColor = languageSelectedColor,
                        textColor = mainTextColor
                    )

                    LanguageOption(
                        language = "isiXhosa",
                        selected = selectedLanguage == AppLanguage.ISIXHOSA,
                        onClick = {
                            onLanguageChange(AppLanguage.ISIXHOSA)
                            showLanguageDialog = false
                        },
                        selectedColor = languageSelectedColor,
                        textColor = mainTextColor
                    )
                }
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        showLanguageDialog = false
                    }
                ) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
fun LanguageOption(
    language: String,
    selected: Boolean,
    onClick: () -> Unit,
    selectedColor: Color,
    textColor: Color
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                selectedColor
            } else {
                Color.Transparent
            }
        )
    ) {

        TextButton(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = if (selected) "✓" else "○",
                    fontSize = 20.sp,
                    color = Color(0xFF8E44AD)
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Text(
                    text = language,
                    fontSize = 16.sp,
                    color = textColor
                )
            }
        }
    }
}

@Composable
fun SettingsSwitchItem(
    icon: String,
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    cardColor: Color,
    textColor: Color,
    secondaryTextColor: Color
) {

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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = icon,
                fontSize = 25.sp
            )

            Spacer(
                modifier = Modifier.width(15.dp)
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
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = secondaryTextColor
                )
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF8E44AD)
                )
            )
        }
    }
}