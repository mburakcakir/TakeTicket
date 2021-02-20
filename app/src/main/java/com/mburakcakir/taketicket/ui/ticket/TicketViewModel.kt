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
import com.mburakcakir.taketicket.utils.Result
import com.mburakcakir.taketicket.utils.SessionManager
import com.mburakcakir.taketicket.utils.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TicketViewModel(
    application: Application
) : BaseViewModel(application) {
    private val sessionManager: SessionManager
    private val eventRepository: EventRepository
    private val ticketRepository: TicketRepository

    private val _allTickets = MutableLiveData<List<TicketModel>>()
    val allTickets: LiveData<List<TicketModel>> = _allTickets

    private val _allEvents = MutableLiveData<List<EventModel>>()
    val allEvents: LiveData<List<EventModel>> = _allEvents

    private val _newTicketResult = MutableLiveData<Result>()
    val newTicketResult: LiveData<Result> = _newTicketResult

    init {
        sessionManager = SessionManager(application)
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

    fun insertTicket(ticketModel: TicketModel) = viewModelScope.launch {
        ticketRepository.insertTicket(ticketModel).collect {
            when (it.status) {
                Status.LOADING -> _newTicketResult.value = Result(loading = "Bilet Kaydediliyor...")
                Status.SUCCESS -> {
                    _newTicketResult.value =
                        if (it.data!!) Result(success = "Bilet Kaydedildi.") else Result(warning = "Bilet Zaten Alınmış.")
                }
                Status.ERROR -> _newTicketResult.value = Result(error = "Bilet Kaydedilemedi.")
            }
        }

    }

    fun getAllTickets() = viewModelScope.launch {
        ticketRepository.getAllTickets(sessionManager.getUsername()!!).collect {
            it.let {
                when (it.status) {
                    Status.LOADING -> _result.value = Result(loading = "Biletler Yükleniyor")
                    Status.SUCCESS -> _allTickets.value = it.data
                    Status.ERROR -> _result.value = Result(error = "Bir hata oluştu")
                }
            }
        }
    }
}