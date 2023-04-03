package com.example.weatherapp.ui.search

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.weatherapp.databinding.ActivityCityItemBinding
import com.example.weatherapp.ui.viewModel.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCityItemBinding
    private val viewModel: ForecastViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val city = intent.getStringExtra("city")
        Toast.makeText(this, city, Toast.LENGTH_SHORT).show()

        if (city != null) {
            viewModel.getWeatherInfo(city, 7, "no", "no")
        }

        viewModel.forecast.observe(this) { forecastResponse ->
            with(binding.weatherMaster) {
                cityNameTextView.text = forecastResponse.location.name
                val localTime = forecastResponse.location.localtime.split(" ")
                baseInfo.baseCityDateTextView.text = localTime[0]
                baseInfo.baseCityTimeTextView.text = localTime[1]
                baseInfo.baseCityWeatherTextView.text = forecastResponse.current.condition.text
                baseInfo.baseCityTemperatureTextView.text = "${forecastResponse.current.temp_c}°"
                baseInfo.baseCityWeatherImageView.load(forecastResponse.current.condition.icon)
                thermostatValueTextView.text =
                    "${forecastResponse.forecast.forecastday[0].day.mintemp_c}°/${forecastResponse.forecast.forecastday[0].day.maxtemp_c}°"
                windValueTextView.text = "${forecastResponse.current.wind_kph} km/h"
                humidityValueTextView.text = "${forecastResponse.current.humidity}%"
                pressureValueTextView.text = "${forecastResponse.current.pressure_mb} hPa"
                visibilityValueTextView.text = "${forecastResponse.current.vis_km} km"
            }
        }
    }
}
