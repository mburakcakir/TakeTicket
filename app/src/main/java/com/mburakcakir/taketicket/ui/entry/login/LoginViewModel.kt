package com.mburakcakir.taketicket.ui.entry.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.db.TicketDatabase
import com.mburakcakir.taketicket.data.db.entity.UserModel
import com.mburakcakir.taketicket.data.repository.user.UserRepository
import com.mburakcakir.taketicket.data.repository.user.UserRepositoryImpl
import com.mburakcakir.taketicket.ui.entry.EntryResult
import com.mburakcakir.taketicket.ui.entry.EntryViewModel
import com.mburakcakir.taketicket.utils.SessionManager
import com.mburakcakir.taketicket.utils.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(
    application: Application
) : EntryViewModel(application) {
    private val sessionManager: SessionManager = SessionManager(application)
    private val userRepository: UserRepository

    init {
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        userRepository = UserRepositoryImpl(database.userDao())
    }

    fun startSession(userModel: UserModel) {
        sessionManager.startSession(userModel)
    }


    fun login(username: String, password: String) = viewModelScope.launch {
        userRepository.getUserByUsername(username, password).collect {
            it.let {
                when (it.status) {
                    Status.LOADING -> Log.v("USERLOADING", "LOADING")
                    Status.SUCCESS -> {
                        startSession(it.data!!)
                        _entryResult.value = EntryResult("Success")
                    }
                    Status.ERROR -> _entryResult.value = EntryResult(error = "Error")
                }
            }
        }
    }


}