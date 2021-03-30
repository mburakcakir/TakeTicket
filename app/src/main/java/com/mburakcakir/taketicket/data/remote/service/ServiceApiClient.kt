package com.mburakcakir.taketicket.data.remote.service

import com.mburakcakir.taketicket.data.remote.model.event.ResponsePopularMovies
import com.mburakcakir.taketicket.data.remote.model.event.ResponseTrendingMovies
import com.mburakcakir.taketicket.data.remote.model.event.ResponseUpcomingMovies
import com.mburakcakir.taketicket.data.remote.model.info.InfoModel
import com.mburakcakir.taketicket.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApiClient {

    @GET(".json")
    suspend fun getInfo(): Response<List<InfoModel>>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String = Constants.API_KEY): Response<ResponseUpcomingMovies>

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String = Constants.API_KEY): Response<ResponsePopularMovies>

    @GET("trending/all/day")
    suspend fun getTrendingMovies(@Query("api_key") apiKey: String = Constants.API_KEY): Response<ResponseTrendingMovies>

}