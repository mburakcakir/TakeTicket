package com.mburakcakir.taketicket.data.repository.info

import com.mburakcakir.taketicket.data.network.service.ServiceApiClient

class InfoRepositoryImpl(
    private val apiClient: ServiceApiClient,
) : InfoRepository {
    override suspend fun getSchedule() = apiClient.getInfo()
}