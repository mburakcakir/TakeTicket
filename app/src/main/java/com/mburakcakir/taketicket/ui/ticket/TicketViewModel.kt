package com.mburakcakir.taketicket.ui.ticket

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
import com.mburakcakir.taketicket.util.Result
import com.mburakcakir.taketicket.util.enums.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class TicketViewModel(
    application: Application
) : BaseViewModel(application) {
    private val eventRepository: EventRepository
    private val ticketRepository: TicketRepository

    private val _allTickets = MutableLiveData<List<TicketModel>>()
    val allTickets: LiveData<List<TicketModel>> = _allTickets

    private val _allEvents = MutableLiveData<List<EventModel>>()
    val allEvents: LiveData<List<EventModel>> = _allEvents

    init {
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        eventRepository = EventRepositoryImpl(database.eventDao())
        ticketRepository = TicketRepositoryImpl(database.ticketDao())
        getAllEvents()
        getAllTickets()
    }

    fun deleteTicket(id: Int) = viewModelScope.launch {
        ticketRepository.deleteTicket(id)
        getAllTickets()
    }

    private fun getAllEvents() = viewModelScope.launch {
        eventRepository.getAllEvents()
            .onStart {
                _result.value = Result(loading = "Etkinlikler Yükleniyor")
            }
            .collect {
                when (it.status) {
                    Status.SUCCESS -> it.data?.let { listEventModel ->
                        _allEvents.value = listEventModel
                        _result.value = Result("Etkinlikler Yüklendi")
                    }
                    Status.ERROR -> _result.value = Result(loading = "Bir hata oluştu.")
                }
            }
    }

    private fun getAllTickets() = viewModelScope.launch {
        ticketRepository.getAllTickets(sessionManager.getUsername())
            .onStart {
                _result.value = Result(loading = "Biletler Yükleniyor")
            }
            .collect {
                it.let {
                    when (it.status) {
                        Status.SUCCESS -> it.data?.let { listTicketModel ->
                            _allTickets.value = listTicketModel
                        }
                        Status.ERROR -> _result.value = Result(error = "Bir hata oluştu")
                    }
                }
            }
    }
}