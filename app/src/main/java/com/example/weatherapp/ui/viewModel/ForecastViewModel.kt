package com.example.weatherapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.forecast.ForecastResponse
import com.example.weatherapp.networking.Network
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val repository: Network,
) : ViewModel() {
    private val _forecastState = MutableLiveData<ForecastResponse>()
    val forecast: LiveData<ForecastResponse> = _forecastState

    fun getWeatherInfo(name: String, days: Int, aqi: String, alerts: String) {
        viewModelScope.launch {
            val forecast = repository.getService().getWeatherInfo(name, days, aqi, alerts)
            _forecastState.value = forecast
        }
    }
}
