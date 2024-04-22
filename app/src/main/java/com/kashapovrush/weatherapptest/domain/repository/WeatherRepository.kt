package com.kashapovrush.weatherapptest.domain.repository

import com.kashapovrush.weatherapptest.domain.entity.Forecast
import com.kashapovrush.weatherapptest.domain.entity.Weather

interface WeatherRepository {

    suspend fun getWeather(cityId: Int): Weather

    suspend fun getForecast(cityId: Int): Forecast
}