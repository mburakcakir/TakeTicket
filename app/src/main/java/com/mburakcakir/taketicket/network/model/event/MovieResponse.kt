package com.mburakcakir.taketicket.network.model.event

data class MovieResponse(
    val dates: Dates,
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)