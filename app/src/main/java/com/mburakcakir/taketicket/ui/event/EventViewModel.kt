package com.mburakcakir.taketicket.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.db.TicketDatabase
import com.mburakcakir.taketicket.data.db.entity.EventModel
import com.mburakcakir.taketicket.data.repository.event.EventRepository
import com.mburakcakir.taketicket.data.repository.event.EventRepositoryImpl
import com.mburakcakir.taketicket.data.repository.ticket.TicketRepository
import com.mburakcakir.taketicket.data.repository.ticket.TicketRepositoryImpl
import com.mburakcakir.taketicket.ui.BaseViewModel
import com.mburakcakir.taketicket.utils.Result
import com.mburakcakir.taketicket.utils.SessionManager
import com.mburakcakir.taketicket.utils.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EventViewModel(
    application: Application
) : BaseViewModel(application) {
    val sessionManager: SessionManager
    private val eventRepository: EventRepository
    private val ticketRepository: TicketRepository
    private val _allEvents = MutableLiveData<List<EventModel>>()
    val allEvents: LiveData<List<EventModel>> = _allEvents

    private val _ticketResult = MutableLiveData<List<EventModel>>()
    val ticketResult: LiveData<List<EventModel>> = _ticketResult

    init {
        sessionManager = SessionManager(application)
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        eventRepository = EventRepositoryImpl(database.eventDao())
        ticketRepository = TicketRepositoryImpl(database.ticketDao())
        getAllEvents()
    }

    private fun getAllEvents() = viewModelScope.launch {
        eventRepository.getAllEvents().collect { it ->
            when (it.status) {
                Status.LOADING -> _result.value = Result(loading = "Etkinlikler Yükleniyor")
                Status.SUCCESS -> {
                    it.data!!.let { eventList ->
                        _allEvents.value = eventList
                        _result.value = Result("Etkinlikler Yüklendi")
                    }
                }
                Status.ERROR -> _result.value = Result(error = "Bir hata oluştu.")
            }
        }
    }

    fun getEventById(ID: Int) = eventRepository.getEventById(ID)
    fun endSession() = sessionManager.endSession()
    fun getUsername() = sessionManager.getUsername()
}



