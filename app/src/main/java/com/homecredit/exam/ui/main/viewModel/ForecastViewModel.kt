package com.homecredit.exam.ui.main.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.homecredit.exam.data.model.ForecastItem
import com.homecredit.exam.data.repository.MainRepository
import com.homecredit.exam.ui.base.BaseViewModel
import com.homecredit.exam.utils.NetworkHelper
import com.homecredit.exam.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ForecastViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper

) : BaseViewModel() {

    private val forecast = MutableLiveData<Result<List<ForecastItem>>>()
    val forecasts: LiveData<Result<List<ForecastItem>>>
        get() = forecast

    init {
        fetchForecasts()
    }

    fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            val localForecasts = mainRepository.getLocalForecasts()
            forecast.postValue(Result.success(localForecasts))
        }
    }

    fun fetchForecasts() {
        viewModelScope.launch {
            forecast.postValue(Result.loading(null))
            if (networkHelper.isNetworkConnected()) {
                withContext(Dispatchers.IO) {
                    val locationList = LOCATION_LIST.joinToString(separator = ",")
                    try {
                        val response = mainRepository.getForecasts(locationList)
                        if (response.isSuccessful) {
                            response.body()?.let { remoteResponse ->
                                val localForecasts = mainRepository.getLocalForecasts()
                                val remoteForecasts = remoteResponse.forecastList
                                remoteForecasts.forEach { remoteForecastItem ->
                                    localForecasts.forEach { localForecastItem ->
                                        if (remoteForecastItem.id == localForecastItem.id) {
                                            remoteForecastItem.favorite = localForecastItem.favorite
                                        }
                                    }
                                }
                                mainRepository.addForecasts(remoteForecasts)

                                //get Local
                                forecast.postValue(Result.success(remoteForecasts))
                            }
                        } else forecast.postValue(Result.error(null, response.message().toString()))
                    } catch (e: Exception) {
                        forecast.postValue(Result.error(null, e.message.toString()))
                    }
                }
            } else forecast.postValue(Result.error(null, "No internet connection"))
        }
    }

    companion object {
        val LOCATION_LIST = listOf("1701668", "1835848", "3067696")
    }
}