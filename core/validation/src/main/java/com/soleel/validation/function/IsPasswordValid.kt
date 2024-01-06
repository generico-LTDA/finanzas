package com.soleel.validation.function


fun isPasswordValid(password: String): Boolean {
    return password.any { it.isDigit() } &&
            password.any { it.isLetter() }
}