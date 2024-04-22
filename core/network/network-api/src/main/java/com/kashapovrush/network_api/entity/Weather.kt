package com.kashapovrush.network_api.entity

import java.util.Calendar

data class Weather(
    val temperature: Float,
    val conditionText: String,
    val conditionUrl: String,
    val date: Calendar
)
