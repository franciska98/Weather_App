package com.example.weatherapp.ui.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.database.LocationDatabase
import com.example.weatherapp.model.CurrentResponse
import com.example.weatherapp.model.forecast.ForecastResponse
import com.example.weatherapp.model.forecast.Location
import com.example.weatherapp.networking.Network
import com.example.weatherapp.ui.search.NO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val repository: Network,
) : ViewModel() {
    private val _forecastState = MutableLiveData<ForecastResponse>()
    val forecast: LiveData<ForecastResponse> = _forecastState
    private val _myLocationState = MutableLiveData(emptyList<Location>())
    private val _locationWithWeather = MutableLiveData(emptyList<CurrentResponse>())
    val locationWithWeather: LiveData<List<CurrentResponse>> = _locationWithWeather
    private val _locationInDb = MutableLiveData<Boolean>()
    val locationInDb: LiveData<Boolean> = _locationInDb

    fun getWeatherInfo(name: String, days: Int, aqi: String, alerts: String) {
        viewModelScope.launch {
            val forecast = repository.getService().getWeatherInfo(name, days, aqi, alerts)
            _forecastState.value = forecast
        }
    }

    fun insertLocationToDb(context: Context, location: Location) {
        viewModelScope.launch {
            val db = LocationDatabase.getDatabase(context)
            db?.locationDao()?.insertLocation(location)
        }
    }

    fun deleteLocationFromDb(context: Context, location: Location) {
        viewModelScope.launch {
            val db = LocationDatabase.getDatabase(context)
            db?.locationDao()?.deleteLocation(location)
        }
    }

    fun getLocationWithWeather(context: Context) {
        viewModelScope.launch {
            val db = LocationDatabase.getDatabase(context)
            val location = db?.locationDao()?.getAllLocationsFromDb()
            _myLocationState.value = location
            for (city in location!!) {
                val current = (_locationWithWeather.value ?: emptyList()).plus(
                    repository.getService().getCurrentWeather(city.name, NO),
                )
                _locationWithWeather.value = current
            }
        }
    }

    fun isLocationSaved(context: Context, name: String) {
        viewModelScope.launch {
            val db = LocationDatabase.getDatabase(context)
            val location = db?.locationDao()?.getLocationByName(name)
            _locationInDb.value = location!!.isNotEmpty()
        }
    }
}
