package com.mburakcakir.taketicket.data.repository.info

import com.mburakcakir.taketicket.data.network.model.InfoModel
import com.mburakcakir.taketicket.data.network.service.ServiceApiClient
import com.mburakcakir.taketicket.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InfoRepositoryImpl(
    private val apiClient: ServiceApiClient,
) : InfoRepository {

    override suspend fun getAllInfo(): Flow<Resource<List<InfoModel>>> = flow {
        emit(Resource.loading())
        try {
            val response = apiClient.getInfo()
            if (response.isSuccessful)
                response.body()?.run {
                    emit(Resource.success(this))
                }

        } catch (e: Exception) {
            emit(Resource.error())
        }
    }

}