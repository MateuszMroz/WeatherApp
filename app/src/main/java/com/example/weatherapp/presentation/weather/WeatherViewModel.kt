package com.example.weatherapp.presentation.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.location.LocationTracker
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.util.Resource.Error
import com.example.weatherapp.domain.util.Resource.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            locationTracker.getCurrentLocation()?.let {
                state = when (val result = repository.getWeather(it.first, it.second)) {
                    is Success -> {
                        state.copy(weatherInfo = result.data, isLoading = false)
                    }

                    is Error -> {
                        state.copy(error = result.message, isLoading = false)
                    }
                }
            } ?: run { state = state.copy(error = "Couldn't get location", isLoading = false) }
        }
    }

}
