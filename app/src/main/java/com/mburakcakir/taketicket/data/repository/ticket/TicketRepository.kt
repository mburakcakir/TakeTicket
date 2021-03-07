package com.mburakcakir.taketicket.data.repository.ticket

import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.util.Resource
import kotlinx.coroutines.flow.Flow

interface TicketRepository{
    suspend fun insertTicket(ticketModel: TicketModel): Flow<Resource<Boolean>>
    fun getAllTickets(username: String): Flow<Resource<List<TicketModel>>>
    fun checkIfTicketExists(eventID: Int, username: String): Boolean
    suspend fun deleteTicket(id: Int)
}