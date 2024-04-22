package com.kashapovrush.weatherapptest.data.repository

import com.kashapovrush.weatherapptest.data.mapper.toEntity
import com.kashapovrush.weatherapptest.data.mapper.toWeather
import com.kashapovrush.weatherapptest.data.network.api.ApiService
import com.kashapovrush.weatherapptest.domain.entity.Forecast
import com.kashapovrush.weatherapptest.domain.entity.Weather
import com.kashapovrush.weatherapptest.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : WeatherRepository {

    override suspend fun getWeather(cityId: Int): Weather {
        return apiService.getWeather("$PREFIX_CITY_ID$cityId").toWeather()
    }

    override suspend fun getForecast(cityId: Int): Forecast {
        return apiService.getForecast("$PREFIX_CITY_ID$cityId").toEntity()
    }

    companion object {

        const val PREFIX_CITY_ID = "id:"
    }
}