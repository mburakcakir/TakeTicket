package com.mburakcakir.taketicket.data.remote.model.event

data class Meta(
    val geolocation: Any,
    val page: Int,
    val per_page: Int,
    val took: Int,
    val total: Int
)