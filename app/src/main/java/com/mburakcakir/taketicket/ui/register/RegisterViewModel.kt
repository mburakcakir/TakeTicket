package com.mburakcakir.taketicket.ui.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.db.TicketDatabase
import com.mburakcakir.taketicket.data.db.entity.UserModel
import com.mburakcakir.taketicket.data.repository.user.UserRepository
import com.mburakcakir.taketicket.data.repository.user.UserRepositoryImpl
import com.mburakcakir.taketicket.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// FACADE PATTERN
class RegisterViewModel(
    application: Application
) : AndroidViewModel(application) {
    val sessionManager: SessionManager
    val userRepository: UserRepository

    init {
        sessionManager = SessionManager(application)
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        userRepository = UserRepositoryImpl(database.userDao())
    }

    fun insertUser(userModel: UserModel) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.insertUser(userModel)
    }

    fun getUserByUsername(username : String, password : String) : LiveData<UserModel> = userRepository.getUserByUsername(username, password)
    fun checkIfUserExists(username : String, password: String) = userRepository.checkIfUserExists(username, password)

    //fun startSession(userModel: UserModel) = sessionManager.startSession(userModel)
    fun startSession(userModel: UserModel) = userRepository.startSession(
        userModel,
        getApplication()
    )


}
