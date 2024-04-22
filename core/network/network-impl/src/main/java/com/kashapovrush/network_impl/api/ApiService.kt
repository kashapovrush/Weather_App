package com.kashapovrush.network_impl.api

import com.kashapovrush.network_impl.dto.CityDto
import com.kashapovrush.network_impl.dto.WeatherCurrentDto
import com.kashapovrush.network_impl.dto.WeatherForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("current.json")
    suspend fun getWeather(
        @Query("q") query: String
    ): WeatherCurrentDto

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") query: String,
        @Query("days") daysCount: Int = 4
    ): WeatherForecastDto

    @GET("search.json")
    suspend fun searchCity(
        @Query("q") query: String
    ):List<CityDto>
}