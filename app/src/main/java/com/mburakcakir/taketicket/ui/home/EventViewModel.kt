package com.mburakcakir.taketicket.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.db.TicketDatabase
import com.mburakcakir.taketicket.data.db.entity.EventModel
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.data.repository.event.EventRepository
import com.mburakcakir.taketicket.data.repository.event.EventRepositoryImpl
import com.mburakcakir.taketicket.data.repository.ticket.TicketRepository
import com.mburakcakir.taketicket.data.repository.ticket.TicketRepositoryImpl
import com.mburakcakir.taketicket.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// FACADE PATTERN
class EventViewModel(
    application: Application
) : AndroidViewModel(application) {
    val sessionManager: SessionManager
    val eventRepository: EventRepository
    val ticketRepository: TicketRepository
    val allEvents: LiveData<List<EventModel>>

    // burada 33. satırda EventRepositoryImpl class'ı, 34. satırda, TicketRepositoryImpl class'ı ve 42. satırda SessionManager class'ı kullanılmaktadır.
    // TicketRepositoryImpl nesnesi için TicketDao nesnesi, TicketDao nesnesi için Database nesnesi gerekmektedir.
    // EventRepositoryImpl nesnesi için EventDao nesnesi, EventDao nesnesi için Database nesnesi gerekmektedir.
    // Bu işlemler ViewModel classında oluşturularak Activity'nin, nesnelerin oluşturulmasından haberi olmadan veya hangi nesnelere erişmesi gerektiği belirtilmeden
    // verilere ulaşması sağlanmakta, aynı zamanda View-Model arasındaki bağlantı kurulmaktadır.
    init {
        sessionManager = SessionManager(application)
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        eventRepository = EventRepositoryImpl(database.eventDao())
        ticketRepository = TicketRepositoryImpl(database.ticketDao())
        allEvents = eventRepository.getAllEvents()
    }

    fun insertTicket(ticketModel: TicketModel) = viewModelScope.launch(Dispatchers.IO) {
        ticketRepository.insertTicket(ticketModel)
    }

    fun checkIfTicketExists(ticketName: String) = ticketRepository.checkIfTicketExists(ticketName)
    fun getUsername() = sessionManager.getUsername()
}

