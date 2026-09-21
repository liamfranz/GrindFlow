package com.example.grindflow.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterScreen(
    onBackToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {

    var fullName by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    // =========================================================
    // THEME COLORS
    // =========================================================

    val purple = MaterialTheme.colorScheme.primary

    val backgroundColor =
        MaterialTheme.colorScheme.background

    val primaryText =
        MaterialTheme.colorScheme.onBackground

    val secondaryText =
        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.65f)

    val googleButtonColor =
        MaterialTheme.colorScheme.surfaceVariant

    val googleTextColor =
        MaterialTheme.colorScheme.onSurfaceVariant


    // =========================================================
    // MAIN SCREEN
    // =========================================================

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp)
                .verticalScroll(rememberScrollState()),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Center
        ) {

            Spacer(
                modifier = Modifier.height(30.dp)
            )


            // =================================================
            // GRINDFLOW LOGO
            // =================================================

            Box(
                modifier = Modifier
                    .size(75.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF9C27B0),
                                Color(0xFFE040FB)
                            )
                        )
                    ),

                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "GF",
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(
                modifier = Modifier.height(15.dp)
            )


            // =================================================
            // TITLE
            // =================================================

            Text(
                text = "Create Account",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = primaryText
            )


            Spacer(
                modifier = Modifier.height(6.dp)
            )


            // =================================================
            // DESCRIPTION
            // =================================================

            Text(
                text = "Start planning your day with GrindFlow.",
                fontSize = 14.sp,
                color = secondaryText,
                textAlign = TextAlign.Center
            )


            Spacer(
                modifier = Modifier.height(25.dp)
            )


            // =================================================
            // FULL NAME
            // =================================================

            OutlinedTextField(

                value = fullName,

                onValueChange = {
                    fullName = it
                    errorMessage = ""
                },

                modifier = Modifier.fillMaxWidth(),

                label = {
                    Text("Full Name")
                },

                singleLine = true,

                shape = RoundedCornerShape(14.dp)
            )


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            // =================================================
            // EMAIL
            // =================================================

            OutlinedTextField(

                value = email,

                onValueChange = {
                    email = it
                    errorMessage = ""
                },

                modifier = Modifier.fillMaxWidth(),

                label = {
                    Text("Email")
                },

                singleLine = true,

                shape = RoundedCornerShape(14.dp)
            )


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            // =================================================
            // PASSWORD
            // =================================================

            OutlinedTextField(

                value = password,

                onValueChange = {
                    password = it
                    errorMessage = ""
                },

                modifier = Modifier.fillMaxWidth(),

                label = {
                    Text("Password")
                },

                visualTransformation =
                    PasswordVisualTransformation(),

                singleLine = true,

                shape = RoundedCornerShape(14.dp)
            )


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            // =================================================
            // CONFIRM PASSWORD
            // =================================================

            OutlinedTextField(

                value = confirmPassword,

                onValueChange = {
                    confirmPassword = it
                    errorMessage = ""
                },

                modifier = Modifier.fillMaxWidth(),

                label = {
                    Text("Confirm Password")
                },

                visualTransformation =
                    PasswordVisualTransformation(),

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
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }


            // =================================================
            // CREATE ACCOUNT
            // =================================================

            Button(

                onClick = {

                    when {

                        fullName.isBlank() ||
                                email.isBlank() ||
                                password.isBlank() ||
                                confirmPassword.isBlank() -> {

                            errorMessage =
                                "Please complete all fields."
                        }

                        !email.contains("@") -> {

                            errorMessage =
                                "Please enter a valid email address."
                        }

                        password.length < 6 -> {

                            errorMessage =
                                "Password must be at least 6 characters."
                        }

                        password != confirmPassword -> {

                            errorMessage =
                                "Passwords do not match."
                        }

                        else -> {

                            errorMessage = ""
                            isLoading = true

                            val auth = FirebaseAuth.getInstance()

                            auth.createUserWithEmailAndPassword(
                                email.trim(),
                                password
                            ).addOnCompleteListener { task ->

                                isLoading = false

                                if (task.isSuccessful) {

                                    onRegisterSuccess()

                                } else {

                                    errorMessage =
                                        task.exception?.message
                                            ?: "Registration failed."
                                }
                            }
                        }
                    }
                },

                enabled = !isLoading,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape = RoundedCornerShape(14.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = purple
                )
            ) {

                Text(
                    text = if (isLoading) {
                        "CREATING ACCOUNT..."
                    } else {
                        "CREATE ACCOUNT"
                    },
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            // =================================================
            // GOOGLE SSO
            // =================================================

            Button(

                onClick = {
                    // Google SSO will be implemented later
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape = RoundedCornerShape(14.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = googleButtonColor
                )
            ) {

                Text(
                    text = "Sign up with Google",
                    color = googleTextColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(
                modifier = Modifier.height(15.dp)
            )


            // =================================================
            // BACK TO LOGIN
            // =================================================

            TextButton(
                onClick = onBackToLogin
            ) {

                Text(
                    text = "Already have an account? LOGIN",
                    color = purple,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }
    }
}