package com.example.weatherapp.model.search

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
    val country: String,
    @PrimaryKey
    val id: Int,
    val lat: Double,
    val lon: Double,
    val name: String,
    val region: String,
    val url: String,
) {
    override fun toString(): String {
        return "$name, $country"
    }
}
