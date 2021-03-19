package com.homecredit.exam.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.homecredit.exam.data.model.WeatherItem
import java.lang.reflect.Type


class DataConverter {
    @TypeConverter
    fun toString(list: List<WeatherItem>): String {
        val type: Type = object : TypeToken<List<WeatherItem>>() {}.type
        return Gson().toJson(list, type)
    }

    @TypeConverter
    fun toList(listString: String): List<WeatherItem>? {
        val type: Type = object : TypeToken<List<WeatherItem>>() {}.type
        return Gson().fromJson<List<WeatherItem>>(listString, type)
    }
}