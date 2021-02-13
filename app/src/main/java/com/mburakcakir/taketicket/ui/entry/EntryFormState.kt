package com.mburakcakir.taketicket.ui.entry

/**
 * Data validation state of the login form.
 */
data class EntryFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)