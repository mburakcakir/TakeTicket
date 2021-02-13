package com.mburakcakir.taketicket.ui.entry

import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mburakcakir.taketicket.R

open class EntryViewModel(application: Application) : AndroidViewModel(application) {
    private val _entryForm = MutableLiveData<EntryFormState>()
    val entryFormState: LiveData<EntryFormState> = _entryForm

    val _entryResult = MutableLiveData<EntryResult>()
    val entryResultState: LiveData<EntryResult> = _entryResult

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _entryForm.value = EntryFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _entryForm.value = EntryFormState(passwordError = R.string.invalid_password)
        } else {
            _entryForm.value = EntryFormState(isDataValid = true)
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
        return password.length > 1
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}