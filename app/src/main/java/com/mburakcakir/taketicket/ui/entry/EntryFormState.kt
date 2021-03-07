package com.mburakcakir.taketicket.ui.entry

/**
 * Data validation state of the login form.
 */
data class EntryFormState(
    val nameError: String? = null,
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val emailError: String? = null,
    val isDataValid: Boolean = false
)