package com.mburakcakir.taketicket.ui.event.foreign

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.db.TicketDatabase
import com.mburakcakir.taketicket.data.remote.model.event.ResponsePopularMovies
import com.mburakcakir.taketicket.data.remote.model.event.ResponseTrendingMovies
import com.mburakcakir.taketicket.data.remote.model.event.ResponseUpcomingMovies
import com.mburakcakir.taketicket.data.repository.event.EventRepository
import com.mburakcakir.taketicket.data.repository.event.EventRepositoryImpl
import com.mburakcakir.taketicket.ui.BaseViewModel
import com.mburakcakir.taketicket.util.Result
import com.mburakcakir.taketicket.util.enums.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ForeignEventViewModel(application: Application) : BaseViewModel(application) {

    private val eventRepository: EventRepository

    private val _upcomingEvents = MutableLiveData<ResponseUpcomingMovies>()
    val upcomingEvents: LiveData<ResponseUpcomingMovies> = _upcomingEvents

    private val _popularEvents = MutableLiveData<ResponsePopularMovies>()
    val popularEvents: LiveData<ResponsePopularMovies> = _popularEvents

    private val _trendingEvents = MutableLiveData<ResponseTrendingMovies>()
    val trendingEvents: LiveData<ResponseTrendingMovies> = _trendingEvents

    init {
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        eventRepository = EventRepositoryImpl(database.eventDao())
        getMovies()
    }

    private fun getMovies() {
        getUpcomingMovies()
        getPopularMovies()
        getTrendingMovies()
    }

    private fun getUpcomingMovies() = viewModelScope.launch {
        eventRepository.getUpcomingMovies()
            .onStart {
                _result.value =
                    Result(loading = "Yaklaşan Etkinlikler Yükleniyor..")
            }
            .collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        _upcomingEvents.value = it.data!!
                        _result.value = Result("Yaklaşan Etkinlikler Yüklendi.")
                    }
                    Status.ERROR -> _result.value = Result(error = "Bir hata oluştu.")
                }
            }
    }

    private fun getPopularMovies() = viewModelScope.launch {
        eventRepository.getPopularMovies()
            .onStart {
                _result.value =
                    Result(loading = "Popüler Etkinlikler Yükleniyor..")
            }
            .collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        _popularEvents.value = it.data!!
                        _result.value = Result("Popüler Etkinlikler Yüklendi.")
                    }
                    Status.ERROR -> _result.value = Result(error = "Bir hata oluştu.")
                }
            }
    }

    private fun getTrendingMovies() = viewModelScope.launch {
        eventRepository.getTrendingMovies()
            .onStart {
                _result.value =
                    Result(loading = "Yükselen Etkinlikler Yükleniyor..")
            }
            .collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        _trendingEvents.value = it.data!!
                        _result.value = Result("Yükselen Etkinlikler Yüklendi.")
                    }
                    Status.ERROR -> _result.value = Result(error = "Bir hata oluştu.")
                }
            }
    }
}