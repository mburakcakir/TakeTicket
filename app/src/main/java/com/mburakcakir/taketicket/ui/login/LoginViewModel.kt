package com.mburakcakir.taketicket.ui.login

import android.app.Application
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.R
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

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    init {
        sessionManager = SessionManager(application)
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
                        _loginResult.value = LoginResult("Success")
                    }
                    Status.ERROR -> _loginResult.value = LoginResult(error = "Error")
                }
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}