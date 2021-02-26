package com.mburakcakir.taketicket.ui.profile

import android.app.Application
import com.mburakcakir.taketicket.ui.BaseViewModel

class ProfileViewModel(application: Application) : BaseViewModel(application) {
    fun endSession() = sessionManager.endSession()
}