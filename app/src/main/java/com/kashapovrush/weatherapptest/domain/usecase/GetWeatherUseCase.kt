package com.kashapovrush.weatherapptest.domain.usecase

import com.kashapovrush.weatherapptest.domain.entity.Weather
import com.kashapovrush.weatherapptest.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(cityId: Int): Weather = repository.getWeather(cityId)
}