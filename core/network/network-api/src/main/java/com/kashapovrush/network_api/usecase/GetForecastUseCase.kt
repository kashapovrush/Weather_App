package com.kashapovrush.network_api.usecase

import com.kashapovrush.network_api.entity.Forecast
import com.kashapovrush.network_api.repository.WeatherRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(cityId: Int): Forecast = repository.getForecast(cityId)
}