package com.mburakcakir.taketicket.data.remote.service

import com.mburakcakir.taketicket.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceProvider {
    private lateinit var event: EventClient

    fun getEventInstance(): EventClient {
        return instance!!.create(EventClient::class.java)
    }
//    retrofitBuilder.baseUrl("https://api.example.com").build().create(Api.class);

    fun getMovieInstance(): MovieClient {
        return instance!!.create(MovieClient::class.java)
    }

    fun getInfoInstance(): InfoClient {
        return instance!!.create(InfoClient::class.java)
    }


    companion object {
        private var retrofit: Retrofit? = null

        private val instance: Retrofit?
            get() {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL_MOVIE)
                        .client(okHttpClientFactory)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
                return retrofit
            }

        private val okHttpClientFactory: OkHttpClient = OkHttpClient().newBuilder().build()
    }
}