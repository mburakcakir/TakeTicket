package com.mburakcakir.taketicket.data.remote.model.event

data class ResponseUpcomingMovies(
    val dates: Dates,
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)