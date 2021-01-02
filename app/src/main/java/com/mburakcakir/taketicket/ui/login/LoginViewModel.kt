package com.mburakcakir.taketicket.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.db.TicketDatabase
import com.mburakcakir.taketicket.data.db.entity.UserModel
import com.mburakcakir.taketicket.data.repository.user.UserRepository
import com.mburakcakir.taketicket.data.repository.user.UserRepositoryImpl
import com.mburakcakir.taketicket.utils.SessionManager

class LoginViewModel (
    application: Application
) : AndroidViewModel(application) {
    val sessionManager: SessionManager
    val userRepository: UserRepository

    init {
        sessionManager = SessionManager(application)
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        userRepository = UserRepositoryImpl(database.userDao())
    }

    fun startSession(userName: String, password: String) {
        val userModel = getUserByUsername(userName, password)
        sessionManager.startSession(userModel)
    }

    fun checkIfUserExists(username: String, password: String) =
        userRepository.checkIfUserExists(username, password)

    fun getUserByUsername(username: String, password: String): UserModel =
        userRepository.getUserByUsername(username, password)


}