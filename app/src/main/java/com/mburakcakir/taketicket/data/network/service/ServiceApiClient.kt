package com.mburakcakir.taketicket.data.network.service

import com.mburakcakir.taketicket.data.network.model.InfoModel
import retrofit2.Response
import retrofit2.http.GET

interface ServiceApiClient {

    @GET(".json")
    suspend fun getInfo(): Response<List<InfoModel>>
}