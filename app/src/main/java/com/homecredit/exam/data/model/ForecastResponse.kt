package com.homecredit.exam.data.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("list")
    var forecastList: List<ForecastItem>
)