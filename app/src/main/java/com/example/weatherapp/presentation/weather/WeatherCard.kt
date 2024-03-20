package com.example.weatherapp.presentation.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.domain.weather.WeatherData
import com.example.weatherapp.domain.weather.WeatherInfo
import com.example.weatherapp.domain.weather.WeatherType.ClearSky
import com.example.weatherapp.presentation.ui.theme.WeatherAppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern
import kotlin.math.roundToInt

@Composable
fun WeatherCard(
    state: WeatherState,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    state.weatherInfo?.let { data ->
        Card(
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(
                        id = R.string.today_title, data.currentWeatherData.time.format(
                            ofPattern("HH:mm")
                        )
                    ),
                    color = White,
                    modifier = Modifier.align(End)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = data.currentWeatherData.weatherType.iconRes),
                    contentDescription = null,
                    Modifier.width(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${data.currentWeatherData.temperatureCelsius} °C",
                    fontSize = 50.sp,
                    color = White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = data.currentWeatherData.weatherType.weatherDesc,
                    fontSize = 20.sp,
                    color = White
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDataDisplay(
                        value = data.currentWeatherData.pressure.roundToInt(),
                        unit = stringResource(id = R.string.hpa),
                        icon = ImageVector.vectorResource(R.drawable.ic_pressure),
                    )
                    WeatherDataDisplay(
                        value = data.currentWeatherData.humidity.roundToInt(),
                        unit = stringResource(id = R.string.percent),
                        icon = ImageVector.vectorResource(R.drawable.ic_drop),
                    )
                    WeatherDataDisplay(
                        value = data.currentWeatherData.windSpeed.roundToInt(),
                        unit = stringResource(id = R.string.kilometers_per_hour),
                        icon = ImageVector.vectorResource(R.drawable.ic_wind),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun WeatherCardPreview() {
    WeatherAppTheme {
        Surface {
            WeatherCard(
                state = WeatherState(
                    weatherInfo = WeatherInfo(
                        weatherDataPerDay = mapOf(
                            Pair(
                                0, listOf(
                                    WeatherData(
                                        time = LocalDateTime.now(),
                                        temperatureCelsius = 20.0,
                                        pressure = 1000.0,
                                        windSpeed = 10.0,
                                        humidity = 0.5,
                                        weatherType = ClearSky
                                    )
                                )
                            )
                        ),
                        currentWeatherData = WeatherData(
                            time = LocalDateTime.now(),
                            temperatureCelsius = 20.0,
                            pressure = 1000.0,
                            windSpeed = 10.0,
                            humidity = 0.5,
                            weatherType = ClearSky
                        )
                    )
                ),
                backgroundColor = White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}
