package com.homecredit.exam.data.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.homecredit.exam.data.db.DataConverter

@Entity(tableName = "forecast")
data class ForecastItem(
    @PrimaryKey
    var id: String,
    @Embedded
    var main: Main,
    var name: String,
    @TypeConverters(DataConverter::class)
    @SerializedName("weather")
    var weatherList: List<WeatherItem>,
    var favorite: Boolean
)