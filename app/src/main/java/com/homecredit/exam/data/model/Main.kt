package com.homecredit.exam.data.model

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("temp")
    var temperature: String,
    @SerializedName("temp_min")
    var minTemperature: String,
    @SerializedName("temp_max")
    var maxTemperature: String
)