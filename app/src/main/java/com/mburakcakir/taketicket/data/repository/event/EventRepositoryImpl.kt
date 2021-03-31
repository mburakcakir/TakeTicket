package com.mburakcakir.taketicket.data.repository.event

import android.util.Log
import com.mburakcakir.taketicket.data.db.dao.EventDao
import com.mburakcakir.taketicket.data.db.entity.EventModel
import com.mburakcakir.taketicket.data.remote.model.event.ResponseEventById
import com.mburakcakir.taketicket.data.remote.model.event.ResponseEvents
import com.mburakcakir.taketicket.data.remote.model.movie.ResponsePopularMovies
import com.mburakcakir.taketicket.data.remote.model.movie.ResponseTrendingMovies
import com.mburakcakir.taketicket.data.remote.model.movie.ResponseUpcomingMovies
import com.mburakcakir.taketicket.data.remote.service.ServiceProvider
import com.mburakcakir.taketicket.util.Constants
import com.mburakcakir.taketicket.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EventRepositoryImpl(private val eventDao: EventDao) : EventRepository {

    private val apiClient = ServiceProvider().getEventInstance()

    override suspend fun getTurkishEvents(): Flow<Resource<List<EventModel>>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(eventDao.getAllEvents()))
        } catch (e: Exception) {
            emit(Resource.Error(e))
            e.printStackTrace()
        }
    }

    override suspend fun getForeignEvents(): Flow<Resource<ResponseEvents>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiClient.getForeignEvents()
            if (response.isSuccessful)
                response.body()?.run {
                    emit(Resource.Success(this))
                }
        } catch (e: Exception) {
            emit(Resource.Error(e))
            e.printStackTrace()
        }
    }

    override suspend fun getForeignEventByID(ID: Int): Flow<Resource<ResponseEventById>> = flow {
        emit(Resource.Loading())
        try {
            Constants.ENDPOINT_FOREIGN_EVENT_BY_ID = ID
            val response = apiClient.getForeignEventById()
            if (response.isSuccessful)
                response.body()?.run {
                    Log.v("data", response.body()?.title.toString())
                    emit(Resource.Success(this))
                }
        } catch (e: Exception) {
            emit(Resource.Error(e))
            e.printStackTrace()
        }
    }

    override suspend fun getUpcomingMovies(): Flow<Resource<ResponseUpcomingMovies>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiClient.getUpcomingMovies()
            if (response.isSuccessful)
                response.body()?.run {
                    emit(Resource.Success(this))
                }
        } catch (e: Exception) {
            emit(Resource.Error(e))
            e.printStackTrace()
        }
    }

    override suspend fun getPopularMovies(): Flow<Resource<ResponsePopularMovies>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiClient.getPopularMovies()
            if (response.isSuccessful)
                response.body()?.run {
                    emit(Resource.Success(this))
                }
        } catch (e: Exception) {
            emit(Resource.Error(e))
            e.printStackTrace()
        }
    }

    override suspend fun getTrendingMovies(): Flow<Resource<ResponseTrendingMovies>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiClient.getTrendingMovies()
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
    override fun getTurkishEventById(ID: Int): EventModel = eventDao.getEventById(ID)
//    override suspend fun getForeignEventByID(ID: Int): EventModel = apiClient.getForeignEventById()
}
