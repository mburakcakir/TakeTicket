package com.mburakcakir.taketicket.ui.profile

import android.app.Application
import com.mburakcakir.taketicket.ui.BaseViewModel

class ProfileViewModel(application: Application) : BaseViewModel(application) {
    fun endSession() = sessionManager.endSession()
    fun setDarkMode(state: Boolean) = sessionManager.setDarkMode(state)
    fun isDarkMode() = sessionManager.isDarkMode()
}