package com.mburakcakir.taketicket.data.repository.ticket

import com.mburakcakir.taketicket.data.db.dao.TicketDao
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.utils.Resource
import kotlinx.coroutines.flow.flow

class TicketRepositoryImpl(private val ticketDao: TicketDao) : TicketRepository {
    override suspend fun insertTicket(ticketModel: TicketModel) =
        ticketDao.insertTicket(ticketModel)

    override fun getAllTickets(username: String?) = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(ticketDao.getAllTickets(username!!)))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override fun checkIfTicketExists(ticketID: Int): Boolean =
        ticketDao.checkIfTicketExists(ticketID) != 0

    override suspend fun deleteTicket(id: Int) = ticketDao.deleteTicket(id)
}