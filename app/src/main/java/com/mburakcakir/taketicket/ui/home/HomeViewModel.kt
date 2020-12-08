package com.mburakcakir.taketicket.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.mburakcakir.taketicket.data.db.TicketDatabase
import com.mburakcakir.taketicket.data.db.entity.ProductModel
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.data.repository.product.ProductRepository
import com.mburakcakir.taketicket.data.repository.product.ProductRepositoryImpl
import com.mburakcakir.taketicket.data.repository.ticket.TicketRepository
import com.mburakcakir.taketicket.data.repository.ticket.TicketRepositoryImpl
import com.mburakcakir.taketicket.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    application: Application
) : AndroidViewModel(application){
    val sessionManager: SessionManager
    val productRepository : ProductRepository
    val ticketRepository : TicketRepository

    val allProducts : LiveData<List<ProductModel>>

    init {
        sessionManager = SessionManager(application)
        val database = TicketDatabase.getDatabase(application,viewModelScope)
        productRepository = ProductRepositoryImpl(database.productDao())
        ticketRepository = TicketRepositoryImpl(database.ticketDao())
        allProducts = productRepository.getAllProducts()
    }

    fun insertTicket(ticketModel: TicketModel) = viewModelScope.launch(Dispatchers.IO){
        ticketRepository.insertTicket(ticketModel)
    }
}

