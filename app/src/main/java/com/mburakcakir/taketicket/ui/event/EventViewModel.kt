package com.mburakcakir.taketicket.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
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

// FACADE PATTERN
class EventViewModel(
    application: Application
) : AndroidViewModel(application) {
    val sessionManager: SessionManager
    val eventRepository: EventRepository
    val ticketRepository: TicketRepository
    val allEvents: MutableLiveData<List<EventModel>> by lazy {
        MutableLiveData<List<EventModel>>()
    }

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
                Status.LOADING -> {
                    Log.v("EVENTLOADING", "LOADING")
                }

                Status.SUCCESS -> {
                    Log.v("EVENTSUCCESS", it.data.toString())
                    allEvents.value = it.data
                }

                Status.ERROR -> {
//                    Log.v("EVENTERROR", it.throwable.toString())
                }

            }
        }
    }

    fun checkIfTicketExists(ticketName: String) = ticketRepository.checkIfTicketExists(ticketName)

    fun getUsername() = sessionManager.getUsername()
}



