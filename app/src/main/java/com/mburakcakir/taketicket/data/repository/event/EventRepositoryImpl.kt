package com.mburakcakir.taketicket.data.repository.event

import androidx.lifecycle.LiveData
import com.mburakcakir.taketicket.data.db.dao.EventDao
import com.mburakcakir.taketicket.data.db.entity.EventModel

class EventRepositoryImpl(private val eventDao: EventDao) : EventRepository {
    override fun getAllEvents(): LiveData<List<EventModel>> = eventDao.getAllEvents()
    override suspend fun insertEvent(eventModel: EventModel) =
        eventDao.insertEvent(eventModel)
}