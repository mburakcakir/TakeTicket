package com.mburakcakir.taketicket.data.remote.service

import com.mburakcakir.taketicket.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceProvider {
    private lateinit var serviceApi: ServiceApiClient

    fun getEventInstance(): ServiceApiClient {
        return instance!!.create(ServiceApiClient::class.java)
    }
    companion object {
        private var retrofit: Retrofit? = null

        private val instance: Retrofit?
            get() {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL_FOREIGN_EVENT)
                        .client(okHttpClientFactory)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
                return retrofit
            }

        private val okHttpClientFactory: OkHttpClient = OkHttpClient().newBuilder().build()

    }
}