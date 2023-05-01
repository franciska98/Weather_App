package com.example.weatherapp.ui.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityCityItemBinding
import com.example.weatherapp.ui.adapter.FutureWeatherRecyclerAdapter
import com.example.weatherapp.ui.adapter.TodayWeatherRecyclerAdapter
import com.example.weatherapp.ui.viewModel.CityViewModel
import com.example.weatherapp.ui.viewModel.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint

const val NO = "no"

@AndroidEntryPoint
class CityItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCityItemBinding
    private val viewModel: ForecastViewModel by viewModels()
    private val cityViewModel: CityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cityName = intent.getStringExtra(CITY_EXTRA)

        if (cityName != null) {
            viewModel.getWeatherInfo(cityName, 7, NO, NO)
            viewModel.getLocationWithWeather(this)
            viewModel.isLocationSaved(this@CityItemActivity, cityName)
        }

        with(binding.cityItemHeader) {
            headerTitle.text = cityName
            headerBack.setOnClickListener() {
                finish()
            }
            headerStar.setOnClickListener() {
                if (viewModel.locationInDb.value == true) {
                    headerStar.load(R.drawable.icons_android_ic_star_zero)
                    viewModel.deleteLocationFromDb(
                        this@CityItemActivity,
                        viewModel.forecast.value!!.location,
                    )
                } else {
                    headerStar.load(R.drawable.icons_android_ic_star_1)
                    viewModel.insertLocationToDb(
                        this@CityItemActivity,
                        viewModel.forecast.value!!.location,
                    )
                }
            }
        }

        viewModel.locationInDb.observe(this) {
            if (it) {
                binding.cityItemHeader.headerStar.load(R.drawable.icons_android_ic_star_1)
            } else {
                binding.cityItemHeader.headerStar.load(R.drawable.icons_android_ic_star_zero)
            }
        }

        viewModel.forecast.observe(this) { forecastResponse ->
            cityViewModel.insertLocationToDb(this, forecastResponse.location)
            with(binding.weatherMaster) {
                cityNameTextView.text = forecastResponse.location.name
                val localTime = forecastResponse.location.localtime.split(" ")
                baseInfo.baseCityDateTextView.text = localTime[0]
                baseInfo.baseCityTimeTextView.text = localTime[1]
                baseInfo.baseCityWeatherTextView.text = forecastResponse.current.condition.text
                baseInfo.baseCityTemperatureTextView.text =
                    getString(R.string.temperature_text_view, forecastResponse.current.temp_c)
                baseInfo.baseCityWeatherImageView.load(forecastResponse.current.condition.icon)
                thermostatValueTextView.text = getString(
                    R.string.thermostat_value_text_view,
                    forecastResponse.forecast.forecastday[0].day.mintemp_c,
                    forecastResponse.forecast.forecastday[0].day.maxtemp_c,
                )
                windValueTextView.text =
                    getString(R.string.wind_value_text_view, forecastResponse.current.wind_kph)
                humidityValueTextView.text = "${forecastResponse.current.humidity}%"
                pressureValueTextView.text = getString(
                    R.string.pressure_value_text_view,
                    forecastResponse.current.pressure_mb,
                )
                visibilityValueTextView.text =
                    getString(R.string.visibility_value_text_view, forecastResponse.current.vis_km)
            }

            val todayWeather = forecastResponse.forecast.forecastday[0].hour
            val todayAdapter = TodayWeatherRecyclerAdapter(this, todayWeather)
            with(binding.todayRecycler) {
                titleTextView.text = getString(R.string.today_card_title)
                weatherRecyclerView.layoutManager =
                    LinearLayoutManager(this@CityItemActivity, RecyclerView.HORIZONTAL, false)
                weatherRecyclerView.adapter = todayAdapter
            }

            val futureWeather = forecastResponse.forecast.forecastday
            val futureAdapter = FutureWeatherRecyclerAdapter(this, futureWeather)
            with(binding.futureRecycler) {
                titleTextView.text = getString(R.string.future_card_title)
                weatherRecyclerView.layoutManager =
                    LinearLayoutManager(this@CityItemActivity, RecyclerView.HORIZONTAL, false)
                weatherRecyclerView.adapter = futureAdapter
            }
        }
    }
}
