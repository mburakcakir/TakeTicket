package com.mburakcakir.taketicket.data.repository.ticket

import com.mburakcakir.taketicket.data.db.dao.TicketDao
import com.mburakcakir.taketicket.data.db.entity.TicketModel

class TicketRepositoryImpl(private val ticketDao : TicketDao) : TicketRepository{
    override suspend fun insertTicket(ticketModel: TicketModel) =
        ticketDao.insertTicket(ticketModel)

    override fun getAllTickets(username: String): ArrayList<TicketModel> =
        ticketDao.getAllTickets(username) as ArrayList<TicketModel>

    override fun checkIfTicketExists(ticketName: String): Boolean =
        ticketDao.checkIfTicketExists(ticketName) != 0
}