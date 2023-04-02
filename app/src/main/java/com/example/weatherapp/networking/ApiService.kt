package com.example.weatherapp.networking

import com.example.weatherapp.model.AllCitiesResponse
import retrofit2.http.GET
import retrofit2.http.Path

const val API_KEY = "157f79858e69403196e191703232703"

interface ApiService {
    @GET("search.json?key=$API_KEY={cityName}")
    suspend fun getAllCities(@Path("cityName") cityName: String): AllCitiesResponse
}
