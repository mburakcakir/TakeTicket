package com.mburakcakir.taketicket.data.remote.service

import com.mburakcakir.taketicket.data.remote.model.info.InfoModel
import com.mburakcakir.taketicket.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface InfoClient {
//    @GET(".json")
//    suspend fun getInfo(): Response<List<InfoModel>>

    @GET
    suspend fun getInfo(@Url url: String = Constants.BASE_URL_INFO + Constants.QUERY_INFO): Response<List<InfoModel>>

}