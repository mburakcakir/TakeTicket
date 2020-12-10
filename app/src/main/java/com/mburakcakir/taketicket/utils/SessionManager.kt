package com.mburakcakir.taketicket.utils

import android.content.Context
import com.mburakcakir.taketicket.data.db.entity.UserModel

class SessionManager(private val context: Context) {
    var sharedPreferences = context.getSharedPreferences(PREF_NAME, 0)
    val editor = sharedPreferences.edit()

    companion object {
        val PREF_NAME: String = "TicketApp"
        val USERNAME: String = "USERNAME"
        val NAME: String = "NAME"
        val PASSWORD: String = "PASSWORD"
        val EMAIL: String = "EMAIL"
        val IS_LOGGED_IN: String = "IS_LOGGED_IN"
    }

    fun startSession(userModel: UserModel) {
        editor.apply {
            putBoolean(IS_LOGGED_IN, true)
            putString(NAME, userModel.name)
            putString(USERNAME, userModel.userName)
            putString(PASSWORD, userModel.password)
            putString(EMAIL, userModel.email)
            commit()
        }
    }

    fun endSession() {
        editor.apply {
            clear()
            commit()
        }
    }

    fun ifUserLoggedIn() = sharedPreferences.getBoolean(IS_LOGGED_IN, false)
    fun getUsername() = sharedPreferences.getString(USERNAME, "")
    fun getUserEmail() = sharedPreferences.getString(EMAIL, "")
    fun getName() = sharedPreferences.getString(NAME, "")
}