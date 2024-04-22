package com.kashapovrush.weatherapptest.domain.entity

import java.util.Calendar

data class Weather(
    val temperature: Float,
    val conditionText: String,
    val conditionUrl: String,
    val date: Calendar
)
