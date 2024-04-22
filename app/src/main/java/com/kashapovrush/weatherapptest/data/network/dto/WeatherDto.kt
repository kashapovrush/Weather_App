package com.kashapovrush.weatherapptest.data.network.dto

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("last_updated_epoch") val date: Long,
    @SerializedName("temp_c") val temp: Float,
    @SerializedName("condition") val conditionDto: ConditionDto
)
