package com.mburakcakir.taketicket.network.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceProvider {
    private lateinit var serviceApi: ServiceApiClient

    fun getServiceApi(): ServiceApiClient {
        serviceApi = instance!!.create(ServiceApiClient::class.java)
        return serviceApi
    }

    companion object {
        private var retrofit: Retrofit? = null
        private const val BASE_URL = "https://taketicketandroid-default-rtdb.firebaseio.com/"

        private val instance: Retrofit?
            get() {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClientFactory)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
                return retrofit
            }

        private val okHttpClientFactory: OkHttpClient = OkHttpClient().newBuilder().build()

    }
}