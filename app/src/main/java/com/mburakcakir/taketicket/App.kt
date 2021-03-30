package com.mburakcakir.taketicket

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.mburakcakir.taketicket.util.SessionManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        SessionManager(this).isDarkMode().apply {
            AppCompatDelegate.setDefaultNightMode(
                if (this)
                    AppCompatDelegate.MODE_NIGHT_YES
                else
                    AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }
}