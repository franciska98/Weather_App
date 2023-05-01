package com.example.weatherapp.networking

import com.example.weatherapp.model.CurrentResponse
import com.example.weatherapp.model.forecast.ForecastResponse
import com.example.weatherapp.model.search.AllCitiesResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val KEY = "157f79858e69403196e191703232703"

interface ApiService {
    @GET("search.json?key=$KEY")
    suspend fun getAllCities(
        @Query("q") cityName: String,
    ): AllCitiesResponse

    @GET("forecast.json?key=$KEY")
    suspend fun getWeatherInfo(
        @Query("q") cityName: String,
        @Query("days") days: Int,
        @Query("aqi") aqi: String,
        @Query("alerts") alerts: String,
    ): ForecastResponse

    @GET("current.json?key=$KEY")
    suspend fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("aqi") aqi: String,
    ): CurrentResponse
}
