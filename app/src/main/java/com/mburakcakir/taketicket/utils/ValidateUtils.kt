package com.mburakcakir.taketicket.utils

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.w3c.dom.Text

class ValidateUtils {
    companion object{
        fun validateUsername(textInputUsername: TextInputLayout): Boolean {
            val usernameInput = textInputUsername.editText?.text.toString().trim()
            if (usernameInput.isEmpty()) {
                textInputUsername.error = "Field can't be empty"
            } else if (usernameInput.length > 15) {
                textInputUsername.error = "Username too long"
            } else {
                textInputUsername.error = null
                return true
            }
            return false
        }

        fun validateInput(textInput: TextInputLayout): Boolean {
            val input = textInput.editText?.text.toString().trim()
            if (input.isEmpty()) {
                textInput.error = "Field can't be empty"
            } else {
                textInput.error = null
                return true
            }
            return false
        }

    }
}
