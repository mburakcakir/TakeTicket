package com.mburakcakir.taketicket.ui.event.foreign

import com.mburakcakir.taketicket.data.remote.model.event.MovieResult

data class NestedRecyclerViewModel(val title: String, val movieResult: List<MovieResult>)
