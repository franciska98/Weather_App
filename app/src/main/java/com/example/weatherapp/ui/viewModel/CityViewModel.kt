package com.example.weatherapp.ui.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.database.HistoryDatabase
import com.example.weatherapp.model.CurrentResponse
import com.example.weatherapp.model.forecast.Location
import com.example.weatherapp.model.search.City
import com.example.weatherapp.networking.Network
import com.example.weatherapp.ui.search.NO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val repository: Network,
) : ViewModel() {
    private val _cityState = MutableLiveData(emptyList<City>())
    val cities: LiveData<List<City>> = _cityState
    private val _myLocationState = MutableLiveData(emptyList<Location>())
    private val _locationWithWeather = MutableLiveData(emptyList<CurrentResponse>())
    val locationWithWeather: LiveData<List<CurrentResponse>> = _locationWithWeather

    fun getAllCities(name: String) {
        viewModelScope.launch {
            val cities = repository.getService().getAllCities(name)
            _cityState.value = cities
        }
    }

    fun insertLocationToDb(context: Context, location: Location) {
        viewModelScope.launch {
            val db = HistoryDatabase.getDatabase(context)
            db?.historyDao()?.insertLocation(location)
        }
    }

    fun deleteLocationFromDb(context: Context, location: Location) {
        viewModelScope.launch {
            val db = HistoryDatabase.getDatabase(context)
            db?.historyDao()?.deleteLocation(location)
        }
    }

    fun getLocationWithWeather(context: Context) {
        viewModelScope.launch {
            val db = HistoryDatabase.getDatabase(context)
            val location = db?.historyDao()?.getAllLocationsFromDb()
            _myLocationState.value = location
            for (city in location!!) {
                val current = (_locationWithWeather.value ?: emptyList()).plus(
                    repository.getService().getCurrentWeather(city.name, NO),
                )
                _locationWithWeather.value = current
            }
        }
    }
}
