package com.mburakcakir.taketicket.data.repository.ticket

import com.mburakcakir.taketicket.data.db.entity.TicketModel

interface TicketRepository{
    suspend fun insertTicket(ticketModel: TicketModel)
    fun getAllTickets(username : String) : ArrayList<TicketModel>
}