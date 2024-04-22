package com.kashapovrush.weatherapptest.data.network.dto

import com.google.gson.annotations.SerializedName

data class DayDto(
    @SerializedName("day") val dayWeatherDto: DayWeatherDto,
    @SerializedName("date_epoch") val date: Long
)
