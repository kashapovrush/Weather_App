package com.kashapovrush.network_impl.dto

import com.google.gson.annotations.SerializedName

data class DayWeatherDto(
    @SerializedName("avgtemp_c") val temp: Float,
    @SerializedName("condition") val conditionDto: ConditionDto
)
