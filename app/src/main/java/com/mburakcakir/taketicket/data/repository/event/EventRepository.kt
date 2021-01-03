package com.mburakcakir.taketicket.data.repository.event

import com.mburakcakir.taketicket.data.db.entity.EventModel
import com.mburakcakir.taketicket.utils.Resource
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    suspend fun getAllEvents(): Flow<Resource<List<EventModel>>>
    suspend fun insertEvent(eventModel: EventModel)
    fun deleteAllEvents()
    fun getEventById(ID: Int): EventModel
}