package com.mburakcakir.taketicket.util

class ValidationUtils {

    fun isEmailValid(email: String): Boolean {
//        return if (email.contains('@')) {
//            Patterns.EMAIL_ADDRESS.matcher(email).matches()
//        } else {
//            false
//        }
        return email.length > 0
    }

    fun isPasswordValid(password: String): Boolean {
//        val textPattern: Pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")
//        return textPattern.matcher(password).matches() && password.length > 5
        return password.length > 0
    }

    fun isUserNameValid(username: String): Boolean {
        return username.length > 0
    }
}