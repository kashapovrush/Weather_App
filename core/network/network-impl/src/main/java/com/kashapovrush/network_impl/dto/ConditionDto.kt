package com.kashapovrush.network_impl.dto

import com.google.gson.annotations.SerializedName

data class ConditionDto(
    @SerializedName("text") val textCondition: String,
    @SerializedName("icon") val iconCondition: String
)
