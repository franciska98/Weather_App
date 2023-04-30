package com.example.weatherapp.model

import com.example.weatherapp.model.forecast.Current
import com.example.weatherapp.model.forecast.Location

data class CurrentResponse(

    val current: Current,
    val location: Location,
)
