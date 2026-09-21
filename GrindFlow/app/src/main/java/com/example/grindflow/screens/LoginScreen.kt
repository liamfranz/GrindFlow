package com.example.grindflow.screens

import android.content.Context
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.example.grindflow.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onCreateAccount: () -> Unit
) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

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
    // GOOGLE SIGN-IN
    // =========================================================

    fun signInWithGoogle() {

        isLoading = true
        errorMessage = ""

        coroutineScope.launch {

            try {

                val credentialManager =
                    CredentialManager.create(context)

                val googleIdOption =
                    GetGoogleIdOption.Builder()
                        .setFilterByAuthorizedAccounts(false)
                        .setServerClientId(
                            context.getString(
                                R.string.default_web_client_id
                            )
                        )
                        .setAutoSelectEnabled(false)
                        .build()

                val request =
                    GetCredentialRequest.Builder()
                        .addCredentialOption(
                            googleIdOption
                        )
                        .build()

                val result: GetCredentialResponse =
                    credentialManager.getCredential(
                        context,
                        request
                    )

                val credential =
                    result.credential

                val googleIdTokenCredential =
                    GoogleIdTokenCredential
                        .createFrom(credential.data)

                val idToken =
                    googleIdTokenCredential.idToken

                val firebaseCredential =
                    GoogleAuthProvider.getCredential(
                        idToken,
                        null
                    )

                FirebaseAuth
                    .getInstance()
                    .signInWithCredential(
                        firebaseCredential
                    )
                    .addOnCompleteListener { task ->

                        isLoading = false

                        if (task.isSuccessful) {

                            onLogin()

                        } else {

                            errorMessage =
                                task.exception?.message
                                    ?: "Google sign-in failed."
                        }
                    }

            } catch (e: GetCredentialException) {

                isLoading = false

                errorMessage =
                    "Google sign-in was cancelled or could not be completed."

            } catch (e: GoogleIdTokenParsingException) {

                isLoading = false

                errorMessage =
                    "Could not process the Google account."

            } catch (e: Exception) {

                isLoading = false

                errorMessage =
                    e.message
                        ?: "Google sign-in failed."
            }
        }
    }


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
                .padding(horizontal = 28.dp),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Center
        ) {

            // =================================================
            // GRINDFLOW LOGO
            // =================================================

            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(24.dp))
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
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(
                modifier = Modifier.height(20.dp)
            )


            // =================================================
            // APP NAME
            // =================================================

            Text(
                text = "GrindFlow",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = primaryText
            )


            Spacer(
                modifier = Modifier.height(6.dp)
            )


            // =================================================
            // TAGLINE
            // =================================================

            Text(
                text = "Plan your day. Complete your goals.",
                fontSize = 15.sp,
                color = secondaryText,
                textAlign = TextAlign.Center
            )


            Spacer(
                modifier = Modifier.height(35.dp)
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
                modifier = Modifier.height(16.dp)
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
                modifier = Modifier.height(6.dp)
            )


            // =================================================
            // FORGOT PASSWORD
            // =================================================

            TextButton(

                onClick = {

                    if (email.isBlank()) {

                        errorMessage =
                            "Please enter your email address."

                    } else {

                        FirebaseAuth
                            .getInstance()
                            .sendPasswordResetEmail(
                                email.trim()
                            )
                            .addOnCompleteListener { task ->

                                if (task.isSuccessful) {

                                    errorMessage =
                                        "Password reset email sent. Check your inbox."

                                } else {

                                    errorMessage =
                                        "Could not send password reset email."
                                }
                            }
                    }
                },

                modifier = Modifier.align(
                    Alignment.End
                )
            ) {

                Text(
                    text = "Forgot Password?",
                    color = purple,
                    fontSize = 14.sp
                )
            }


            // =================================================
            // ERROR / STATUS MESSAGE
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
            // LOGIN BUTTON
            // =================================================

            Button(

                onClick = {

                    when {

                        email.isBlank() -> {

                            errorMessage =
                                "Please enter your email address."
                        }

                        password.isBlank() -> {

                            errorMessage =
                                "Please enter your password."
                        }

                        else -> {

                            errorMessage = ""
                            isLoading = true

                            FirebaseAuth
                                .getInstance()
                                .signInWithEmailAndPassword(
                                    email.trim(),
                                    password
                                )
                                .addOnCompleteListener { task ->

                                    isLoading = false

                                    if (task.isSuccessful) {

                                        onLogin()

                                    } else {

                                        errorMessage =
                                            "Incorrect email or password."
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
                        "LOGGING IN..."
                    } else {
                        "LOGIN"
                    },

                    color = Color.White,

                    fontSize = 16.sp,

                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(
                modifier = Modifier.height(18.dp)
            )


            // =================================================
            // OR
            // =================================================

            Text(
                text = "OR",
                color = secondaryText,
                fontSize = 13.sp
            )


            Spacer(
                modifier = Modifier.height(18.dp)
            )


            // =================================================
            // GOOGLE SSO
            // =================================================

            Button(

                onClick = {
                    signInWithGoogle()
                },

                enabled = !isLoading,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape = RoundedCornerShape(14.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = googleButtonColor
                )
            ) {

                Text(
                    text = if (isLoading) {
                        "CONNECTING..."
                    } else {
                        "Continue with Google"
                    },

                    color = googleTextColor,

                    fontSize = 15.sp,

                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(
                modifier = Modifier.height(22.dp)
            )


            // =================================================
            // ACCOUNT MESSAGE
            // =================================================

            Text(
                text = "Don't have an account?",
                color = secondaryText,
                fontSize = 14.sp
            )


            // =================================================
            // CREATE ACCOUNT
            // =================================================

            TextButton(
                onClick = onCreateAccount,
                enabled = !isLoading
            ) {

                Text(
                    text = "CREATE ACCOUNT",
                    color = purple,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}