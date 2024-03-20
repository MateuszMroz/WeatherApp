package com.example.weatherapp.domain.weather

import com.example.weatherapp.domain.weather.WeatherData

data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData
)
