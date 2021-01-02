package com.mburakcakir.taketicket.data.repository.event

import com.mburakcakir.taketicket.data.db.dao.EventDao
import com.mburakcakir.taketicket.data.db.entity.EventModel
import com.mburakcakir.taketicket.utils.Resource
import kotlinx.coroutines.flow.flow

class EventRepositoryImpl(private val eventDao: EventDao) : EventRepository {

    override suspend fun getAllEvents() = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(eventDao.getAllEvents()))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun insertEvent(eventModel: EventModel) = eventDao.insertEvent(eventModel)
}
