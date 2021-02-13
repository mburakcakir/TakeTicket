package com.mburakcakir.taketicket.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.db.TicketDatabase
import com.mburakcakir.taketicket.data.db.entity.EventModel
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.data.repository.event.EventRepository
import com.mburakcakir.taketicket.data.repository.event.EventRepositoryImpl
import com.mburakcakir.taketicket.data.repository.ticket.TicketRepository
import com.mburakcakir.taketicket.data.repository.ticket.TicketRepositoryImpl
import com.mburakcakir.taketicket.ui.BaseViewModel
import com.mburakcakir.taketicket.utils.Result
import com.mburakcakir.taketicket.utils.SessionManager
import com.mburakcakir.taketicket.utils.Status
import kotlinx.coroutines.Dispatchers
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

    init {
        sessionManager = SessionManager(application)
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        eventRepository = EventRepositoryImpl(database.eventDao())
        ticketRepository = TicketRepositoryImpl(database.ticketDao())
        getAllEvents()
    }

    fun insertTicket(ticketModel: TicketModel) = viewModelScope.launch(Dispatchers.IO) {
        ticketRepository.insertTicket(ticketModel)
    }

    fun getAllEvents() = viewModelScope.launch {
        eventRepository.getAllEvents().collect {
            when (it.status) {
                Status.LOADING -> _result.value = Result(loading = "Etkinlikler Yükleniyor")
                Status.SUCCESS -> {
                    _allEvents.value = it.data
                    _result.value = Result("Etkinlikler Yüklendi")
                }
                Status.ERROR -> _result.value = Result(loading = "Bir hata oluştu.")
            }
        }
    }

    fun checkIfTicketExists(ticketID: Int) = ticketRepository.checkIfTicketExists(ticketID)
    fun getEventById(ID: Int) = eventRepository.getEventById(ID)
    fun endSession() = sessionManager.endSession()
    fun getUsername() = sessionManager.getUsername()
}



