package com.mburakcakir.taketicket.ui.event.foreign

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.db.TicketDatabase
import com.mburakcakir.taketicket.data.repository.event.EventRepository
import com.mburakcakir.taketicket.data.repository.event.EventRepositoryImpl
import com.mburakcakir.taketicket.network.model.event.MovieResponse
import com.mburakcakir.taketicket.ui.BaseViewModel
import com.mburakcakir.taketicket.util.Result
import com.mburakcakir.taketicket.util.enums.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ForeignEventViewModel(application: Application) : BaseViewModel(application) {

    private val eventRepository: EventRepository
    private val _foreignEvents = MutableLiveData<MovieResponse>()
    val foreignEvents: LiveData<MovieResponse> = _foreignEvents

    init {
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        eventRepository = EventRepositoryImpl(database.eventDao())
        getUpcomingMovies()
    }

    private fun getUpcomingMovies() = viewModelScope.launch {
        eventRepository.getUpcomingMovies()
            .onStart {
                _result.value =
                    Result(loading = "Geliştirici Bilgileri Yükleniyor..")
            }
            .collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        _foreignEvents.value = it.data!!
                        _result.value = Result("Bilgiler Yüklendi.")
                    }
                    Status.ERROR -> _result.value = Result(error = "Bir hata oluştu.")
                }
            }
    }
}