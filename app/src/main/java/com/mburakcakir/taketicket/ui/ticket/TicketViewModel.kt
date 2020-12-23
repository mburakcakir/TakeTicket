package com.mburakcakir.taketicket.ui.ticket

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.db.TicketDatabase
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.data.repository.event.EventRepository
import com.mburakcakir.taketicket.data.repository.event.EventRepositoryImpl
import com.mburakcakir.taketicket.data.repository.ticket.TicketRepository
import com.mburakcakir.taketicket.data.repository.ticket.TicketRepositoryImpl
import com.mburakcakir.taketicket.utils.SessionManager

// FACADE PATTERN
class TicketViewModel(
    application: Application
) : AndroidViewModel(application) {
    val sessionManager: SessionManager
    val eventRepository: EventRepository
    val ticketRepository: TicketRepository
    val allTickets: MutableLiveData<List<TicketModel>> by lazy {
        MutableLiveData<List<TicketModel>>()
    }

    // burada 35.satırda, SessionManager ve TicketRepositoryImpl classları kullanılmaktadır.
    // TicketRepositoryImpl nesnesi için TicketDao nesnesi, TicketDao nesnesi için Database nesnesi gerekmektedir.
    // EventRepositoryImpl nesnesi için EventDao nesnesi, EventDao nesnesi için Database nesnesi gerekmektedir.
    // Bu işlemler ViewModel classında oluşturularak Activity'nin, nesnelerin oluşturulmasından haberi olmadan veya hangi nesnelere erişmesi gerektiği belirtilmeden
    // verilere ulaşması sağlanmakta, aynı zamanda View-Model arasındaki bağlantı kurulmaktadır.
    init {
        sessionManager = SessionManager(application)
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        eventRepository = EventRepositoryImpl(database.eventDao())
        ticketRepository = TicketRepositoryImpl(database.ticketDao())
        allTickets.value = sessionManager.getUsername()?.let { ticketRepository.getAllTickets(it) }
    }

}