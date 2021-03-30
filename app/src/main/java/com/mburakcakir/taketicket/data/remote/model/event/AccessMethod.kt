package com.mburakcakir.taketicket.data.remote.model.event

data class AccessMethod(
    val created_at: String,
    val employee_only: Boolean,
    val method: String
)