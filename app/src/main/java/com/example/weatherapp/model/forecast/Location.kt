package com.example.weatherapp.model.forecast

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location(
    val country: String,
    val lat: Double,
    val localtime: String,
    val localtime_epoch: Int,
    val lon: Double,
    @PrimaryKey
    val name: String,
    val region: String,
    val tz_id: String,
)
