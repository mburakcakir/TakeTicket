package com.mburakcakir.taketicket.data.repository.ticket

import com.mburakcakir.taketicket.data.db.entity.TicketModel

interface TicketRepository{
    suspend fun insertTicket(ticketModel: TicketModel)
    fun getAllTickets(username: String): List<TicketModel>
    fun checkIfTicketExists(ticketName: String): Boolean
    fun deleteTicket(id: Int)
}