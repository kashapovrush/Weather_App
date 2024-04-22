package com.kashapovrush.network_api.entity

data class Forecast(
    val currentWeather: Weather,
    val upcoming: List<Weather>
)
