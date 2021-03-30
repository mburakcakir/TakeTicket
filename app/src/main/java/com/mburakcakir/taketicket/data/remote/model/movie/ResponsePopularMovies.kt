package com.mburakcakir.taketicket.data.remote.model.movie

data class ResponsePopularMovies(
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)