package com.mburakcakir.taketicket.ui.event.turkish

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
import com.mburakcakir.taketicket.data.repository.user.UserRepository
import com.mburakcakir.taketicket.data.repository.user.UserRepositoryImpl
import com.mburakcakir.taketicket.ui.BaseViewModel
import com.mburakcakir.taketicket.util.Result
import com.mburakcakir.taketicket.util.enums.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class TurkishEventViewModel(
    application: Application
) : BaseViewModel(application) {
    private val eventRepository: EventRepository
    private val ticketRepository: TicketRepository
    private val userRepository: UserRepository
    private val _turkishEvents = MutableLiveData<List<EventModel>>()
    val turkishEvents: LiveData<List<EventModel>> = _turkishEvents

    init {
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        eventRepository = EventRepositoryImpl(database.eventDao())
        ticketRepository = TicketRepositoryImpl(database.ticketDao())
        userRepository = UserRepositoryImpl(database.userDao())
        getAllEvents()
    }

    fun getUserImageUri(): String {
        return userRepository.getUserImageUri(sessionManager.getUsername())
    }

    private fun getAllEvents() = viewModelScope.launch {
        eventRepository.getTurkishEvents()
            .onStart { _result.value = Result(loading = "Etkinlikler Yükleniyor") }
            .collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.let { eventList ->
                            _turkishEvents.value = eventList
                            _result.value = Result("Etkinlikler Yüklendi")
                        }
                    }
                    Status.ERROR -> _result.value = Result(error = "Bir hata oluştu.")
                }
            }
    }

    fun getEventById(ID: Int) = eventRepository.getEventById(ID)
    fun getUsername() = sessionManager.getUsername()
}
