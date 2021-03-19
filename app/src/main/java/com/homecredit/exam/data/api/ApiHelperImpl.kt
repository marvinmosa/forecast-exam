package com.homecredit.exam.data.api

import com.homecredit.exam.data.model.ForecastItem
import com.homecredit.exam.data.model.ForecastResponse
import retrofit2.Response


class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getForecasts(id: String, unit: String, appId: String): Response<ForecastResponse> = apiService.getForecasts(id, unit, appId)
    override suspend fun getForecast(id: String, unit: String, appId: String): Response<ForecastItem> = apiService.getForecast(id, unit, appId)
}
