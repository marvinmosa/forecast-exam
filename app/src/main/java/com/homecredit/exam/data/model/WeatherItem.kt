package com.homecredit.exam.data.model

import com.google.gson.annotations.SerializedName

data class WeatherItem(
    @SerializedName("id")
    var id: String,
    @SerializedName("main")
    var weather: String
)