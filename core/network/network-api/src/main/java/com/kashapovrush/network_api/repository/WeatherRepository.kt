package com.kashapovrush.network_api.repository

import com.kashapovrush.network_api.entity.Forecast
import com.kashapovrush.network_api.entity.Weather

interface WeatherRepository {

    suspend fun getWeather(cityId: Int): Weather

    suspend fun getForecast(cityId: Int): Forecast
}