package com.example.weatherapp.model

data class City(
    val country: String,
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
