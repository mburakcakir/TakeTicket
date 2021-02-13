package com.mburakcakir.taketicket.ui.entry.register

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

class RegisterViewModel(
    application: Application
) : EntryViewModel(application) {
    private val sessionManager: SessionManager
    private val userRepository: UserRepository

    init {
        sessionManager = SessionManager(application)
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        userRepository = UserRepositoryImpl(database.userDao())
    }

    fun insertUser(userModel: UserModel) = viewModelScope.launch {
        userRepository.insertUser(userModel).collect {
            it.let {
                when (it.status) {
                    Status.LOADING -> Log.v("USERLOADING", "LOADING")
                    Status.SUCCESS -> {
                        _entryResult.value =
                            if (it.data!!) EntryResult(success = "Başarılı") else EntryResult(error = "Kullanıcı Kayıtlı")
                    }
                    Status.ERROR -> _entryResult.value = EntryResult(error = "Error")
                }
            }
        }
    }

    fun checkIfUserExists(username: String, password: String) =
        userRepository.checkIfUserExists(username, password)
}
