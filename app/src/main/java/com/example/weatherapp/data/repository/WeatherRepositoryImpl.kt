package com.example.weatherapp.data.repository

import com.example.weatherapp.data.mapper.toWeatherInfo
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.util.Resource
import com.example.weatherapp.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {

    override suspend fun getWeather(lat: Double, lon: Double): Resource<WeatherInfo> {
        return try {
            val weatherResponse = weatherApi.getWeather(lat, lon)
            val weatherInfo = weatherResponse.toWeatherInfo()
            Resource.Success(weatherInfo)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}
