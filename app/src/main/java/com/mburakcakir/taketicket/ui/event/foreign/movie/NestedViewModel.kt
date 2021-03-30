package com.mburakcakir.taketicket.ui.event.foreign.movie

import com.mburakcakir.taketicket.data.remote.model.movie.MovieResult

data class NestedViewModel(val title: String, val movieResult: List<MovieResult>)
