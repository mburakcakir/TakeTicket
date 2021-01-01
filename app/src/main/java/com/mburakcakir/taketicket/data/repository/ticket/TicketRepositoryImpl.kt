package com.mburakcakir.taketicket.data.repository.ticket

import com.mburakcakir.taketicket.data.db.dao.TicketDao
import com.mburakcakir.taketicket.data.db.entity.TicketModel

class TicketRepositoryImpl(private val ticketDao : TicketDao) : TicketRepository{
    override suspend fun insertTicket(ticketModel: TicketModel) =
        ticketDao.insertTicket(ticketModel)

    override fun getAllTickets(username: String): List<TicketModel> =
        ticketDao.getAllTickets(username)

    override fun checkIfTicketExists(ticketName: String): Boolean =
        ticketDao.checkIfTicketExists(ticketName) != 0

    override fun deleteTicket(id: Int) = ticketDao.deleteTicket(id)
}