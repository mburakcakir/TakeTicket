package com.mburakcakir.taketicket.ui.entry

/**
 * Data validation state of the login form.
 */
data class EntryFormState(
    val nameError: String? = "",
    var usernameError: String? = "",
    var passwordError: String? = "",
    var emailError: String? = "",
    val isDataValid: Boolean = false
)