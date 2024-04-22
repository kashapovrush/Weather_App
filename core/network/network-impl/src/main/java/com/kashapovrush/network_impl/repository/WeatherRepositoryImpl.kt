package com.kashapovrush.network_impl.repository

import com.kashapovrush.network_api.entity.Forecast
import com.kashapovrush.network_api.entity.Weather
import com.kashapovrush.network_api.repository.WeatherRepository
import com.kashapovrush.network_impl.api.ApiService
import com.kashapovrush.network_impl.mapper.toEntity
import com.kashapovrush.network_impl.mapper.toWeather
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