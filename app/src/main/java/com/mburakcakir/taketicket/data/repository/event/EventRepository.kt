package com.mburakcakir.taketicket.data.repository.event

import com.mburakcakir.taketicket.data.db.entity.EventModel
import com.mburakcakir.taketicket.data.remote.model.event.ResponseEventById
import com.mburakcakir.taketicket.data.remote.model.event.ResponseEvents
import com.mburakcakir.taketicket.data.remote.model.movie.ResponsePopularMovies
import com.mburakcakir.taketicket.data.remote.model.movie.ResponseTrendingMovies
import com.mburakcakir.taketicket.data.remote.model.movie.ResponseUpcomingMovies
import com.mburakcakir.taketicket.util.Resource
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    suspend fun getTurkishEvents(): Flow<Resource<List<EventModel>>>
    suspend fun getForeignEvents(): Flow<Resource<ResponseEvents>>
    suspend fun insertEvent(eventModel: EventModel)
    fun deleteAllEvents()
    fun getTurkishEventById(ID: Int): EventModel
    suspend fun getForeignEventByID(ID: Int): Flow<Resource<ResponseEventById>>
    suspend fun getUpcomingMovies(): Flow<Resource<ResponseUpcomingMovies>>
    suspend fun getPopularMovies(): Flow<Resource<ResponsePopularMovies>>
    suspend fun getTrendingMovies(): Flow<Resource<ResponseTrendingMovies>>
}