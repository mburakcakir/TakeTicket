package com.mburakcakir.taketicket.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.db.TicketDatabase
import com.mburakcakir.taketicket.data.db.entity.UserModel
import com.mburakcakir.taketicket.data.repository.user.UserRepository
import com.mburakcakir.taketicket.data.repository.user.UserRepositoryImpl
import com.mburakcakir.taketicket.utils.SessionManager
import com.mburakcakir.taketicket.utils.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val sessionManager: SessionManager
    private val userRepository: UserRepository
    lateinit var userModel: UserModel

    init {
        sessionManager = SessionManager(application)
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        userRepository = UserRepositoryImpl(database.userDao())
    }

    fun startSession(userName: String, password: String) {
        getUserByUsername(userName, password)
        sessionManager.startSession(userModel)
    }

    fun checkIfUserExists(username: String, password: String) =
        userRepository.checkIfUserExists(username, password)

    fun getUserByUsername(username: String, password: String) = viewModelScope.launch {
        userRepository.getUserByUsername(username, password).collect {
            it.let {
                when (it.status) {
                    Status.LOADING -> Log.v("USERLOADING", "LOADING")
                    Status.SUCCESS -> userModel = it.data!!
                    Status.ERROR -> Log.v("USERERROR", "ERROR")
                }
            }
        }
    }
}