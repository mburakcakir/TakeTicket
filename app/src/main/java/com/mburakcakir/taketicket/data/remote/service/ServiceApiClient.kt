package com.mburakcakir.taketicket.data.remote.service

import com.mburakcakir.taketicket.data.remote.model.event.ResponseEventById
import com.mburakcakir.taketicket.data.remote.model.event.ResponseEvents
import com.mburakcakir.taketicket.data.remote.model.info.InfoModel
import com.mburakcakir.taketicket.data.remote.model.movie.ResponsePopularMovies
import com.mburakcakir.taketicket.data.remote.model.movie.ResponseTrendingMovies
import com.mburakcakir.taketicket.data.remote.model.movie.ResponseUpcomingMovies
import com.mburakcakir.taketicket.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ServiceApiClient {

//    @GET(".json")
//    suspend fun getInfo(): Response<List<InfoModel>>

//    @GET("movie/upcoming")
//    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String = Constants.API_KEY): Response<ResponseUpcomingMovies>
//
//    @GET("movie/popular")
//    suspend fun getPopularMovies(@Query("api_key") apiKey: String = Constants.API_KEY): Response<ResponsePopularMovies>
//
//    @GET("trending/all/day")
//    suspend fun getTrendingMovies(@Query("api_key") apiKey: String = Constants.API_KEY): Response<ResponseTrendingMovies>
//
//    @GET("events")
//    suspend fun getForeignEvents(@Query("client_id") clientID: String = Constants.CLIENT_ID) : Response<ResponseEvents>


    @GET
    suspend fun getInfo(@Url url: String = Constants.BASE_URL_INFO + Constants.QUERY_INFO): Response<List<InfoModel>>

    @GET
    suspend fun getUpcomingMovies(@Url url: String = Constants.BASE_URL_MOVIE + Constants.QUERY_MOVIE_UPCOMING): Response<ResponseUpcomingMovies>

    @GET
    suspend fun getPopularMovies(@Url url: String = Constants.BASE_URL_MOVIE + Constants.QUERY_MOVIE_POPULAR): Response<ResponsePopularMovies>

    @GET
    suspend fun getTrendingMovies(@Url url: String = Constants.BASE_URL_MOVIE + Constants.QUERY_MOVIE_TRENDING): Response<ResponseTrendingMovies>

    @GET
    suspend fun getForeignEvents(@Url url: String = Constants.BASE_URL_FOREIGN_EVENT + Constants.ENDPOINT_ALL_FOREIGN_EVENTS + Constants.QUERY_FOREIGN_EVENT): Response<ResponseEvents>

    @GET
    suspend fun getForeignEventById(@Url url: String = Constants.BASE_URL_FOREIGN_EVENT + Constants.ENDPOINT_ALL_FOREIGN_EVENTS + Constants.ENDPOINT_FOREIGN_EVENT_BY_ID + Constants.QUERY_FOREIGN_EVENT): Response<ResponseEventById>
}