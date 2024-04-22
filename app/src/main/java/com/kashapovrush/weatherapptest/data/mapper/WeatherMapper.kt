package com.kashapovrush.weatherapptest.data.mapper

import com.kashapovrush.weatherapptest.data.network.dto.WeatherCurrentDto
import com.kashapovrush.weatherapptest.data.network.dto.WeatherDto
import com.kashapovrush.weatherapptest.data.network.dto.WeatherForecastDto
import com.kashapovrush.weatherapptest.domain.entity.Forecast
import com.kashapovrush.weatherapptest.domain.entity.Weather
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