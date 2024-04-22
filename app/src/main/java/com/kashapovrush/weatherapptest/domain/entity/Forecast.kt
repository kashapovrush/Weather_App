package com.kashapovrush.weatherapptest.domain.entity

data class Forecast(
    val currentWeather: Weather,
    val upcoming: List<Weather>
)
