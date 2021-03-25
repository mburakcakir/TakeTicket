package com.mburakcakir.taketicket.util

import android.content.Context
import com.mburakcakir.taketicket.data.db.entity.UserModel

class SessionManager(context: Context) {
    private var sharedPreferences = context.getSharedPreferences(PREF_NAME, 0)
    private val editor = sharedPreferences.edit()

    companion object {
        val PREF_NAME: String = "TicketApp"
        val USERNAME: String = "USERNAME"
        val NAME: String = "NAME"
        val PASSWORD: String = "PASSWORD"
        val EMAIL: String = "EMAIL"
        val URI: String = "URI"
        val IS_LOGGED_IN: String = "IS_LOGGED_IN"
        val IS_DARK_MODE: String = "IS_DARK_MODE"
    }

    fun startSession(userModel: UserModel) {
        editor.apply {
            putBoolean(IS_LOGGED_IN, true)
            putString(NAME, userModel.name)
            putString(USERNAME, userModel.userName)
            putString(PASSWORD, userModel.password)
            putString(EMAIL, userModel.email)
            putString(URI, userModel.profileImageUri)
            commit()
        }
    }

    fun saveImageUri(uri: String) {
        editor.apply {
            putString(URI, uri)
            commit()
        }
    }

    fun setDarkMode(state: Boolean) {
        editor.apply {
            putBoolean(IS_DARK_MODE, state)
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
    fun getUsername() = sharedPreferences.getString(USERNAME, "")!!
    fun getUserEmail() = sharedPreferences.getString(EMAIL, "")!!
    fun getName() = sharedPreferences.getString(NAME, "")
    fun getImageUri() = sharedPreferences.getString(URI, null)
    fun isDarkMode() = sharedPreferences.getBoolean(IS_DARK_MODE, false)
}