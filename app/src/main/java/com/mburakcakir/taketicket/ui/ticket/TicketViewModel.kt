package com.mburakcakir.taketicket.ui.ticket

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.db.TicketDatabase
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.data.repository.event.EventRepository
import com.mburakcakir.taketicket.data.repository.event.EventRepositoryImpl
import com.mburakcakir.taketicket.data.repository.ticket.TicketRepository
import com.mburakcakir.taketicket.data.repository.ticket.TicketRepositoryImpl
import com.mburakcakir.taketicket.utils.SessionManager
import com.mburakcakir.taketicket.utils.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TicketViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val sessionManager: SessionManager
    private val eventRepository: EventRepository
    private val ticketRepository: TicketRepository
    private val _allTickets = MutableLiveData<List<TicketModel>>()
    val allTickets: LiveData<List<TicketModel>>
        get() = _allTickets

    init {
        sessionManager = SessionManager(application)
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        eventRepository = EventRepositoryImpl(database.eventDao())
        ticketRepository = TicketRepositoryImpl(database.ticketDao())
    }

    fun deleteTicket(id: Int) = viewModelScope.launch {
        ticketRepository.deleteTicket(id)
    }

    fun getEventByID(ID: Int) = eventRepository.getEventById(ID)

    fun getAllTickets() = viewModelScope.launch {
        ticketRepository.getAllTickets(sessionManager.getUsername()).collect {
            it.let {
                when (it.status) {
                    Status.LOADING -> Log.v("TICKETLOADING", "LOADING")
                    Status.SUCCESS -> _allTickets.value = it.data
                    Status.ERROR -> Log.v("TICKETERROR", "ERROR")
                }
            }
        }
    }
}