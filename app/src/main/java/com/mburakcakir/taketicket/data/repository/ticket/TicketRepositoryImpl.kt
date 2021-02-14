package com.mburakcakir.taketicket.data.repository.ticket

import com.mburakcakir.taketicket.data.db.dao.TicketDao
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.utils.Resource
import kotlinx.coroutines.flow.flow

class TicketRepositoryImpl(private val ticketDao: TicketDao) : TicketRepository {
    override suspend fun insertTicket(ticketModel: TicketModel) = flow {
        emit(Resource.Loading())
        try {
            if (checkIfTicketExists(ticketModel.eventID))
                emit(Resource.Success(false))
            else {
                emit(Resource.Success(true))
                ticketDao.insertTicket(ticketModel)
            }
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override fun getAllTickets(username: String?) = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(ticketDao.getAllTickets(username!!)))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override fun checkIfTicketExists(eventID: Int): Boolean =
        ticketDao.checkIfTicketExists(eventID) != 0

    override suspend fun deleteTicket(id: Int) = ticketDao.deleteTicket(id)
}