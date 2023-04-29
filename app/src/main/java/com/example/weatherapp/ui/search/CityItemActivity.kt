package com.example.weatherapp.ui.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityCityItemBinding
import com.example.weatherapp.ui.viewModel.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint

const val NO = "no"

@AndroidEntryPoint
class CityItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCityItemBinding
    private val viewModel: ForecastViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val city = intent.getStringExtra(CITY_EXTRA)

        if (city != null) {
            viewModel.getWeatherInfo(city, 7, NO, NO)
        }

        viewModel.forecast.observe(this) { forecastResponse ->
            with(binding.weatherMaster) {
                cityNameTextView.text = forecastResponse.location.name
                val localTime = forecastResponse.location.localtime.split(" ")
                baseInfo.baseCityDateTextView.text = localTime[0]
                baseInfo.baseCityTimeTextView.text = localTime[1]
                baseInfo.baseCityWeatherTextView.text = forecastResponse.current.condition.text
                baseInfo.baseCityTemperatureTextView.text = getString(R.string.base_city_temperature_text_view, forecastResponse.current.temp_c)
                baseInfo.baseCityWeatherImageView.load(forecastResponse.current.condition.icon)
                thermostatValueTextView.text = getString(
                    R.string.thermostat_value_text_view,
                    forecastResponse.forecast.forecastday[0].day.mintemp_c,
                    forecastResponse.forecast.forecastday[0].day.maxtemp_c,
                )
                windValueTextView.text = getString(R.string.wind_value_text_view, forecastResponse.current.wind_kph)
                humidityValueTextView.text = "${forecastResponse.current.humidity}%"
                pressureValueTextView.text = getString(R.string.pressure_value_text_view, forecastResponse.current.pressure_mb)
                visibilityValueTextView.text = getString(R.string.visibility_value_text_view, forecastResponse.current.vis_km)
            }
        }
    }
}
