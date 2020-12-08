package com.mburakcakir.taketicket.ui.ticket

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.db.TicketDatabase
import com.mburakcakir.taketicket.data.repository.product.ProductRepository
import com.mburakcakir.taketicket.data.repository.product.ProductRepositoryImpl
import com.mburakcakir.taketicket.data.repository.ticket.TicketRepository
import com.mburakcakir.taketicket.data.repository.ticket.TicketRepositoryImpl
import com.mburakcakir.taketicket.utils.SessionManager

class TicketViewModel(
    application: Application
) : AndroidViewModel(application) {
    val sessionManager: SessionManager
    val productRepository : ProductRepository
    val ticketRepository : TicketRepository

    init {
        sessionManager = SessionManager(application)
        val database = TicketDatabase.getDatabase(application,viewModelScope)
        productRepository = ProductRepositoryImpl(database.productDao())
        ticketRepository = TicketRepositoryImpl(database.ticketDao())
    }

    fun getAllTickets(username : String) = ticketRepository.getAllTickets(username)
    fun getUsername() = sessionManager.getUsername()

}