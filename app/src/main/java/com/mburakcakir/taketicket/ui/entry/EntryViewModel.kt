package com.mburakcakir.taketicket.ui.entry

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mburakcakir.taketicket.ui.BaseViewModel
import com.mburakcakir.taketicket.util.Constants
import com.mburakcakir.taketicket.util.ValidationUtils
import com.mburakcakir.taketicket.util.enums.EntryState
import com.mburakcakir.taketicket.util.enums.EntryType

open class EntryViewModel(application: Application) : BaseViewModel(application) {
    private val _entryForm = MutableLiveData<EntryFormState>()
    val entryFormState: LiveData<EntryFormState> = _entryForm
    private val _errorUsername = MutableLiveData("")
    private val _errorPassword = MutableLiveData("")
    private val _errorEmail = MutableLiveData("")

    private lateinit var entryType: EntryType
    private var typeList: MutableList<String?> = mutableListOf()

    init {
        _entryForm.value = EntryFormState()
    }

    fun isDataChanged(
        entryState: EntryState,
        text: String
    ) {
        when (entryState) {
            EntryState.USERNAME -> {
                _errorUsername.value =
                    if (!isUserNameValid(text)) Constants.INVALID_USERNAME
                    else null
            }

            EntryState.PASSWORD -> {
                _errorPassword.value =
                    if (!isPasswordValid(text)) Constants.INVALID_PASSWORD
                    else null
            }

            EntryState.EMAIL -> {
                _errorEmail.value =
                    if (!isEmailValid(text)) Constants.INVALID_EMAIL
                    else null
            }
        }
        setEntryParameters()
        setEntryFormState()
    }

    private fun setEntryFormState() {
        _entryForm.value = EntryFormState(
            usernameError = _errorUsername.value,
            passwordError = _errorPassword.value,
            emailError = _errorEmail.value,
            isDataValid = isDataValid()
        )

    }

    private fun isDataValid() =
        mutableListOf<String>().apply {
            for (item in typeList)
                item?.let { this.add(it) }
        }.size == 0

    private fun setEntryParameters() {
        typeList = when (entryType) {
            EntryType.LOGIN -> mutableListOf(_errorUsername.value, _errorPassword.value)
            EntryType.REGISTER -> mutableListOf(
                _errorUsername.value,
                _errorPassword.value,
                _errorEmail.value
            )
        }
    }

    fun setEntryType(entryType: EntryType) {
        this.entryType = entryType
    }

    private fun isUserNameValid(text: String) = ValidationUtils().isUserNameValid(text)
    private fun isPasswordValid(text: String) = ValidationUtils().isPasswordValid(text)
    private fun isEmailValid(text: String) = ValidationUtils().isEmailValid(text)
}