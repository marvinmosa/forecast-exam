package com.homecredit.exam.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.homecredit.exam.data.model.ForecastItem

@Dao
interface ForecastDao {
    @Query("SELECT * FROM forecast ORDER BY id")
    fun getAllForecasts(): List<ForecastItem>

    @Query("SELECT * FROM forecast WHERE id = :id")
    fun getForecast(id: Int): ForecastItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addForecast(forecast: ForecastItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addForecastList(forecastList: List<ForecastItem>)

    @Query("UPDATE forecast SET favorite = :isToggled WHERE id = :id")
    fun updateForecastFavorite(id: String, isToggled: Boolean)

    @Query("DELETE FROM forecast")
    fun deleteAll()
}