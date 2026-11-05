
package com.example.grindflow.auth

object AuthValidation {

    fun validateLogin(email: String, password: String): String? {
        return when {
            email.isBlank() ->
                "Please enter your email address."

            password.isBlank() ->
                "Please enter your password."

            else -> null
        }
    }

    fun validateRegistration(
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): String? {
        return when {
            fullName.isBlank() ||
                    email.isBlank() ||
                    password.isBlank() ||
                    confirmPassword.isBlank() ->
                "Please complete all fields."

            !email.contains("@") ->
                "Please enter a valid email address."

            password.length < 6 ->
                "Password must be at least 6 characters."

            password != confirmPassword ->
                "Passwords do not match."

            else -> null
        }
    }
}
