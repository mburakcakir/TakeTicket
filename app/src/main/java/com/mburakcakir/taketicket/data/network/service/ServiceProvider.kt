package com.mburakcakir.taketicket.data.network.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceProvider {
    internal lateinit var serviceApi: ServiceApiClient

    fun getServiceApi(): ServiceApiClient {
        serviceApi = instance!!.create(ServiceApiClient::class.java)
        return serviceApi
    }
    // ROOM DB SINGLETON.
    // Kotlin'de static keyword olmadığından dolayı companion object ile static özelliği sağlayabilmekteyiz.
    // Kotlin'de singleton için "object" keyword'unu kullanmak yeterlidir, fakat companion object, private instance ve null check ile adım adım yapılmıştır.
    // singleton nesnesinin çağırımı getServiceApi() fonksiyonu ile çağırılmaktadır. Fonksiyonda null kontrolü sağlanmasına rağmen, null-safety desteği sağlayan Kotlin ile
    // tekrardan null durumuna bakılarak uygulamanın çökmesi engellenmiştir.
    companion object {
        private var retrofit: Retrofit? = null
        val BASE_URL = "https://taketicketandroid-default-rtdb.firebaseio.com/"

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

        private val okHttpClientFactory: OkHttpClient
            get() {
                val builder = OkHttpClient().newBuilder()
                return builder.build()
            }
    }
}