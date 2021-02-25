package com.mburakcakir.taketicket.ui.entry

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.ui.BaseViewModel

open class EntryViewModel(application: Application) : BaseViewModel(application) {
    private val _entryForm = MutableLiveData<EntryFormState>()
    val entryFormState: LiveData<EntryFormState> = _entryForm

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _entryForm.value = EntryFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _entryForm.value = EntryFormState(passwordError = R.string.invalid_password)
        } else {
            _entryForm.value = EntryFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty()
    }
}