package com.mburakcakir.taketicket.data.remote.service

import com.mburakcakir.taketicket.data.remote.model.event.ResponseEvents
import com.mburakcakir.taketicket.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface EventClient {

//    @GET("events")
//    suspend fun getForeignEvents(@Query("client_id") clientID: String = Constants.CLIENT_ID) : Response<ResponseEvents>

    @GET
    suspend fun getForeignEvents(@Url url: String = Constants.BASE_URL_FOREIGN_EVENT + Constants.ENDPOINT_ALL_FOREIGN_EVENTS + Constants.QUERY_FOREIGN_EVENT): Response<ResponseEvents>
}