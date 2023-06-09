package com.example.weatherapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.search.City
import com.example.weatherapp.networking.Network
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val repository: Network,
) : ViewModel() {
    private val _cityState = MutableLiveData(emptyList<City>())
    val cities: LiveData<List<City>> = _cityState

    fun getAllCities(name: String) {
        viewModelScope.launch {
            val cities = repository.getService().getAllCities(name)
            _cityState.value = cities
        }
    }
}
