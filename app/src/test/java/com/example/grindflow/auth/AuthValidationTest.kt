
package com.example.grindflow.auth

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AuthValidationTest {

    @Test
    fun loginWithEmptyEmailReturnsError() {
        val result = AuthValidation.validateLogin("", "password123")
        assertEquals("Please enter your email address.", result)
    }

    @Test
    fun loginWithEmptyPasswordReturnsError() {
        val result = AuthValidation.validateLogin("test@email.com", "")
        assertEquals("Please enter your password.", result)
    }

    @Test
    fun loginWithValidDetailsReturnsNull() {
        val result = AuthValidation.validateLogin(
            "test@email.com",
            "password123"
        )
        assertNull(result)
    }

    @Test
    fun registrationWithEmptyFieldsReturnsError() {
        val result = AuthValidation.validateRegistration("", "", "", "")
        assertEquals("Please complete all fields.", result)
    }

    @Test
    fun registrationWithInvalidEmailReturnsError() {
        val result = AuthValidation.validateRegistration(
            "Test User",
            "invalidemail",
            "password123",
            "password123"
        )
        assertEquals("Please enter a valid email address.", result)
    }

    @Test
    fun registrationWithShortPasswordReturnsError() {
        val result = AuthValidation.validateRegistration(
            "Test User",
            "test@email.com",
            "12345",
            "12345"
        )
        assertEquals("Password must be at least 6 characters.", result)
    }

    @Test
    fun registrationWithDifferentPasswordsReturnsError() {
        val result = AuthValidation.validateRegistration(
            "Test User",
            "test@email.com",
            "password123",
            "password456"
        )
        assertEquals("Passwords do not match.", result)
    }

    @Test
    fun registrationWithValidDetailsReturnsNull() {
        val result = AuthValidation.validateRegistration(
            "Test User",
            "test@email.com",
            "password123",
            "password123"
        )
        assertNull(result)
    }
}
