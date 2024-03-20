package com.example.weatherapp.presentation.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun WeatherForecast(
    state: WeatherState,
    modifier: Modifier = Modifier
) {
    state.weatherInfo?.weatherDataPerDay?.get(0)?.let { weatherData ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Text(text = "Today", color = White, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow {
                items(weatherData) {
                    HourlyForecastItem(
                        time = it.time,
                        icon = it.weatherType.iconRes,
                        temperature = it.temperatureCelsius,
                        modifier = Modifier.height(120.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun HourlyForecastItem(
    time: LocalDateTime,
    icon: Int,
    temperature: Double,
    modifier: Modifier = Modifier
) {
    val formattedTime = remember(time) {
        time.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.padding(8.dp)
    ) {
        Text(text = formattedTime, color = LightGray)
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
        )
        Text(text = "$temperature°C", color = LightGray, fontSize = 16.sp, fontWeight = Bold)
    }
}
