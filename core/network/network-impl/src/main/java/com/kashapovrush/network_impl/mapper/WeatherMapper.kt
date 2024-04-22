package com.kashapovrush.network_impl.mapper

import com.kashapovrush.network_api.entity.Forecast
import com.kashapovrush.network_api.entity.Weather
import com.kashapovrush.network_impl.dto.WeatherCurrentDto
import com.kashapovrush.network_impl.dto.WeatherDto
import com.kashapovrush.network_impl.dto.WeatherForecastDto
import java.util.Calendar
import java.util.Date

fun WeatherDto.toEntity(): Weather = Weather(
    temperature = temp,
    conditionText = conditionDto.textCondition,
    conditionUrl = conditionDto.iconCondition,
    date = date.toCalendar()
)

fun WeatherCurrentDto.toWeather(): Weather = current.toEntity()



fun WeatherForecastDto.toEntity(): Forecast =
    Forecast(
        currentWeather = current.toEntity(),
        upcoming = forecastDto.forecastDay.drop(1).map {
            Weather(
                temperature = it.dayWeatherDto.temp,
                conditionText = it.dayWeatherDto.conditionDto.textCondition,
                conditionUrl = it.dayWeatherDto.conditionDto.iconCondition.toCorrectImage(),
                date = it.date.toCalendar()
            )
        }
    )

fun Long.toCalendar() = Calendar.getInstance().apply {
    time = Date(this@toCalendar * 1000)
}

fun String.toCorrectImage() = "https:$this".replace("64x64", "128x128")