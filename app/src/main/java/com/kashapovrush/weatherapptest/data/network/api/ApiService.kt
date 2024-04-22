package com.kashapovrush.weatherapptest.data.network.api

import com.kashapovrush.weatherapptest.data.network.dto.CityDto
import com.kashapovrush.weatherapptest.data.network.dto.ForecastDto
import com.kashapovrush.weatherapptest.data.network.dto.WeatherCurrentDto
import com.kashapovrush.weatherapptest.data.network.dto.WeatherForecastDto
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