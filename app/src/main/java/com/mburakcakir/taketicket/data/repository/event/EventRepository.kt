package com.mburakcakir.taketicket.data.repository.event

import com.mburakcakir.taketicket.data.db.entity.EventModel
import com.mburakcakir.taketicket.data.remote.model.event.ResponsePopularMovies
import com.mburakcakir.taketicket.data.remote.model.event.ResponseTrendingMovies
import com.mburakcakir.taketicket.data.remote.model.event.ResponseUpcomingMovies
import com.mburakcakir.taketicket.util.Resource
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    suspend fun getAllEvents(): Flow<Resource<List<EventModel>>>
    suspend fun insertEvent(eventModel: EventModel)
    fun deleteAllEvents()
    fun getEventById(ID: Int): EventModel
    suspend fun getUpcomingMovies(): Flow<Resource<ResponseUpcomingMovies>>
    suspend fun getPopularMovies(): Flow<Resource<ResponsePopularMovies>>
    suspend fun getTrendingMovies(): Flow<Resource<ResponseTrendingMovies>>
}