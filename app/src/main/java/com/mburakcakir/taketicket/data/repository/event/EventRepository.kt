package com.mburakcakir.taketicket.data.repository.event

import androidx.lifecycle.LiveData
import com.mburakcakir.taketicket.data.db.entity.EventModel

interface EventRepository {
    fun getAllEvents(): LiveData<List<EventModel>>
    suspend fun insertEvent(eventModel: EventModel)
}