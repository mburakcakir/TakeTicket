package com.mburakcakir.taketicket.data.repository.info

import com.mburakcakir.taketicket.data.remote.model.info.InfoModel
import com.mburakcakir.taketicket.data.remote.service.ServiceApiClient
import com.mburakcakir.taketicket.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InfoRepositoryImpl(
    private val apiClient: ServiceApiClient,
) : InfoRepository {

    override suspend fun getAllInfo(): Flow<Resource<List<InfoModel>>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiClient.getInfo()
            if (response.isSuccessful)
                response.body()?.run {
                    emit(Resource.Success(this))
                }
        } catch (e: Exception) {
            emit(Resource.Error(e))
            e.printStackTrace()
        }
    }

}