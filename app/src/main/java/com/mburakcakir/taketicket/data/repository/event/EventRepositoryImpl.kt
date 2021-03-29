package com.mburakcakir.taketicket.data.repository.event

import com.mburakcakir.taketicket.data.db.dao.EventDao
import com.mburakcakir.taketicket.data.db.entity.EventModel
import com.mburakcakir.taketicket.network.model.event.MovieResponse
import com.mburakcakir.taketicket.network.service.ServiceProvider
import com.mburakcakir.taketicket.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EventRepositoryImpl(private val eventDao: EventDao) : EventRepository {

    private val apiClient = ServiceProvider().getServiceApi()

    override suspend fun getAllEvents(): Flow<Resource<List<EventModel>>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(eventDao.getAllEvents()))
        } catch (e: Exception) {
            emit(Resource.Error(e))
            e.printStackTrace()
        }
    }

    override suspend fun getUpcomingMovies(): Flow<Resource<MovieResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiClient.getUpcomingMovies("0084f501851d0d513240f2a30678bbe4")
            if (response.isSuccessful)
                response.body()?.run {
                    emit(Resource.Success(this))
                }
        } catch (e: Exception) {
            emit(Resource.Error(e))
            e.printStackTrace()
        }
    }

    // update veya delete Completable
    override suspend fun insertEvent(eventModel: EventModel) = eventDao.insertEvent(eventModel)
    override fun deleteAllEvents() = eventDao.deleteAllEvents()
    override fun getEventById(ID: Int): EventModel = eventDao.getEventById(ID)
}
