package com.example.weatherapp.ui.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.database.CitiesDatabase
import com.example.weatherapp.model.CurrentResponse
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
    private val _myCityState = MutableLiveData(emptyList<City>())
    val myCities: LiveData<List<City>> = _myCityState
    private val _myCityWeatherState = MutableLiveData(emptyList<CurrentResponse>())
    val myCityWeather: LiveData<List<CurrentResponse>> = _myCityWeatherState

    fun getAllCities(name: String) {
        viewModelScope.launch {
            val cities = repository.getService().getAllCities(name)
            _cityState.value = cities
        }
    }

    fun insertCityToDb(context: Context, city: City) {
        viewModelScope.launch {
            val db = CitiesDatabase.getDatabase(context)
            db?.citiesDao()?.insertCity(city)
        }
    }

    fun deleteCityToDb(context: Context, city: City) {
        viewModelScope.launch {
            val db = CitiesDatabase.getDatabase(context)
            db?.citiesDao()?.deleteCity(city)
        }
    }

    fun getMyCitiesWithWeather(context: Context) {
        viewModelScope.launch {
            val db = CitiesDatabase.getDatabase(context)
            val cities = db?.citiesDao()?.getAllCitiesFromDb()
            _myCityState.value = cities
            for (city in cities!!) {
                val current = (_myCityWeatherState.value ?: emptyList()).plus(
                    repository.getService().getCurrentWeather(city.name, NO),
                )
                _myCityWeatherState.value = current
            }
        }
    }
}
