package com.mburakcakir.taketicket.data.repository.ticket

import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TicketRepository{
    suspend fun insertTicket(ticketModel: TicketModel)
    fun getAllTickets(username: String?): Flow<Resource<List<TicketModel>>>
    fun checkIfTicketExists(ticketID: Int): Boolean
    suspend fun deleteTicket(id: Int)
}