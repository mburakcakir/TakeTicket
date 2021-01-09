package com.mburakcakir.taketicket.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
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
import com.mburakcakir.taketicket.utils.SessionManager
import com.mburakcakir.taketicket.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EventViewModel(
    application: Application
) : AndroidViewModel(application) {
    val sessionManager: SessionManager
    private val eventRepository: EventRepository
    private val ticketRepository: TicketRepository
    private val _allEvents = MutableLiveData<List<EventModel>>()
    val allEvents: LiveData<List<EventModel>>
        get() = _allEvents

    init {
        sessionManager = SessionManager(application)
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        eventRepository = EventRepositoryImpl(database.eventDao())
        ticketRepository = TicketRepositoryImpl(database.ticketDao())
    }

    fun insertTicket(ticketModel: TicketModel) = viewModelScope.launch(Dispatchers.IO) {
        ticketRepository.insertTicket(ticketModel)
    }

    fun getAllEvents() = viewModelScope.launch {
        eventRepository.getAllEvents().collect {
            when (it.status) {
                Status.LOADING -> Log.v("EVENTLOADING", "LOADING")
                Status.SUCCESS -> _allEvents.value = it.data
                Status.ERROR -> Log.v("EVENTERROR", it.message.toString())
            }
        }
    }

    fun checkIfTicketExists(ticketID: Int) = ticketRepository.checkIfTicketExists(ticketID)
    fun getEventById(ID: Int) = eventRepository.getEventById(ID)
    fun endSession() = sessionManager.endSession()
    fun getUsername() = sessionManager.getUsername()
}



