package com.mburakcakir.taketicket.network.service

import com.mburakcakir.taketicket.network.model.event.MovieResponse
import com.mburakcakir.taketicket.network.model.info.InfoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApiClient {

    @GET(".json")
    suspend fun getInfo(): Response<List<InfoModel>>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String): Response<MovieResponse>
}