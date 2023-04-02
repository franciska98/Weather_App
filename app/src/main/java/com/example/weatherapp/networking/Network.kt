package com.example.weatherapp.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Network @Inject constructor() {
    private val interceptor = HttpLoggingInterceptor()
    private val httpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
    private val baseUrl = "http://api.weatherapi.com/v1/"

    private val service = Retrofit
        .Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    fun getService(): ApiService {
        return service
    }
}
