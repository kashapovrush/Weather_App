package com.kashapovrush.weatherapptest.domain.usecase

import com.kashapovrush.weatherapptest.domain.entity.Forecast
import com.kashapovrush.weatherapptest.domain.repository.WeatherRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(cityId: Int): Forecast = repository.getForecast(cityId)
}