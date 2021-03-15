package com.mburakcakir.taketicket.ui.entry

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mburakcakir.taketicket.ui.BaseViewModel
import com.mburakcakir.taketicket.util.Constants
import com.mburakcakir.taketicket.util.LoginState
import com.mburakcakir.taketicket.util.ValidationUtils

open class EntryViewModel(application: Application) : BaseViewModel(application) {
    private val _entryForm = MutableLiveData<EntryFormState>()
    val entryFormState: LiveData<EntryFormState> = _entryForm
    var errorUsername = MutableLiveData<String?>()
    var errorPassword = MutableLiveData<String?>()
    var errorEmail = MutableLiveData<String?>()

    init {
        _entryForm.value = EntryFormState()
    }

    fun isDataChanged(LOGINSTATE: LoginState, text: String) {
        when (LOGINSTATE) {
            LoginState.USERNAME -> {
                errorUsername.value =
                    if (!isUserNameValid(text)) Constants.INVALID_USERNAME
                    else null
            }
            LoginState.PASSWORD -> {
                errorPassword.value =
                    if (!isPasswordValid(text)) Constants.INVALID_PASSWORD
                    else null
            }
            LoginState.EMAIL -> {
                errorEmail.value =
                    if (!isEmailValid(text)) Constants.INVALID_EMAIL
                    else null
            }
        }
        setEntryState()
    }

    private fun setEntryState() {
        _entryForm.value = EntryFormState(
            usernameError = errorUsername.value,
            passwordError = errorPassword.value,
            emailError = errorEmail.value,
            isDataValid = isDataValid()
        )
    }

    private fun isDataValid() =
        errorUsername.value == null && errorPassword.value == null && errorEmail.value == null

    private fun isUserNameValid(text: String) = ValidationUtils().isUserNameValid(text)
    private fun isPasswordValid(text: String) = ValidationUtils().isPasswordValid(text)
    private fun isEmailValid(text: String) = ValidationUtils().isEmailValid(text)


}