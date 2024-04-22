package com.kashapovrush.network_impl.dto

import com.google.gson.annotations.SerializedName

data class DayDto(
    @SerializedName("day") val dayWeatherDto: DayWeatherDto,
    @SerializedName("date_epoch") val date: Long
)
