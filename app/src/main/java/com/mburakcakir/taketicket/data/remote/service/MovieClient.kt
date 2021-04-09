package com.mburakcakir.taketicket.data.remote.service

import com.mburakcakir.taketicket.data.remote.model.movie.ResponsePopularMovies
import com.mburakcakir.taketicket.data.remote.model.movie.ResponseTrendingMovies
import com.mburakcakir.taketicket.data.remote.model.movie.ResponseUpcomingMovies
import com.mburakcakir.taketicket.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface MovieClient {


    @GET
    suspend fun getUpcomingMovies(@Url url: String = Constants.BASE_URL_MOVIE + Constants.QUERY_MOVIE_UPCOMING): Response<ResponseUpcomingMovies>

    @GET
    suspend fun getPopularMovies(@Url url: String = Constants.BASE_URL_MOVIE + Constants.QUERY_MOVIE_POPULAR): Response<ResponsePopularMovies>

    @GET
    suspend fun getTrendingMovies(@Url url: String = Constants.BASE_URL_MOVIE + Constants.QUERY_MOVIE_TRENDING): Response<ResponseTrendingMovies>

//    @GET("movie/upcoming")
//    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String = Constants.API_KEY): Response<ResponseUpcomingMovies>
//
//    @GET("movie/popular")
//    suspend fun getPopularMovies(@Query("api_key") apiKey: String = Constants.API_KEY): Response<ResponsePopularMovies>
//
//    @GET("trending/all/day")
//    suspend fun getTrendingMovies(@Query("api_key") apiKey: String = Constants.API_KEY): Response<ResponseTrendingMovies>
//
}