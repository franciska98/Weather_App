package com.example.weatherapp.model

data class ForecastResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location,
)
