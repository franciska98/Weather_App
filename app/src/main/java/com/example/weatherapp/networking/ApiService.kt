package com.example.weatherapp.networking

import com.example.weatherapp.model.AllCitiesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search.json?key=157f79858e69403196e191703232703")
    suspend fun getAllCities(
        @Query("q") cityName: String,
    ): AllCitiesResponse
}