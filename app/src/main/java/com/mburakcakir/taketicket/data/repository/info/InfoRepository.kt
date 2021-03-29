package com.mburakcakir.taketicket.data.repository.info

import com.mburakcakir.taketicket.network.model.info.InfoModel
import com.mburakcakir.taketicket.util.Resource
import kotlinx.coroutines.flow.Flow

interface InfoRepository {
    suspend fun getAllInfo(): Flow<Resource<List<InfoModel>>>
}