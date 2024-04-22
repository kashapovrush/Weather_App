package com.kashapovrush.network_api.usecase

import com.kashapovrush.network_api.entity.Weather
import com.kashapovrush.network_api.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(cityId: Int): Weather = repository.getWeather(cityId)
}