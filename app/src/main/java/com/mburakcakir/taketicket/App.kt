package com.mburakcakir.taketicket

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.mburakcakir.taketicket.util.SessionManager

class App : Application() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate() {
        super.onCreate()
        sessionManager = SessionManager(this)

        val isDarkMode = sessionManager.isDarkMode()
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}