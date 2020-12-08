package com.mburakcakir.taketicket.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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
        val database = TicketDatabase.getDatabase(application,viewModelScope)
        userRepository = UserRepositoryImpl(database.userDao())
        sessionManager = SessionManager(application)
    }

    fun startSession(userModel : UserModel) = sessionManager.startSession(userModel)
    fun checkIfUserExists(username : String, password: String) = userRepository.checkIfUserExists(username,password)
    fun getUserByUsername(username : String, password : String) : LiveData<UserModel> = userRepository.getUserByUsername(username, password)

}