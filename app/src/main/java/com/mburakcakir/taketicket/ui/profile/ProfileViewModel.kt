package com.mburakcakir.taketicket.ui.profile

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mburakcakir.taketicket.ui.BaseViewModel

class ProfileViewModel(application: Application) : BaseViewModel(application) {
    fun endSession() = sessionManager.endSession()

    private var _isDarkMode = MutableLiveData<Boolean>()
    val isDarkMode: LiveData<Boolean> = _isDarkMode

    init {
        _isDarkMode.value = sessionManager.isDarkMode()
    }

    fun changeDarkMode() {
        val isDarkMode = sessionManager.isDarkMode()

        if (isDarkMode)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        _isDarkMode.value = !isDarkMode
        sessionManager.setDarkMode(!isDarkMode)
    }
}