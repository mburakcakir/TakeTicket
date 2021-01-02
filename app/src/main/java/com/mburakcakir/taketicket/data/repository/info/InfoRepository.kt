package com.mburakcakir.taketicket.data.repository.info

import com.mburakcakir.taketicket.data.network.model.InfoModel
import com.mburakcakir.taketicket.utils.Resource
import kotlinx.coroutines.flow.Flow

interface InfoRepository {
    suspend fun getAllInfo(): Flow<Resource<List<InfoModel>>>
}