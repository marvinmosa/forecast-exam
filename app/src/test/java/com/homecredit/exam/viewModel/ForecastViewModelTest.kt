package com.homecredit.exam.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.homecredit.exam.data.model.ForecastItem
import com.homecredit.exam.data.model.ForecastResponse
import com.homecredit.exam.data.repository.MainRepository
import com.homecredit.exam.ui.main.viewModel.ForecastViewModel
import com.homecredit.exam.utils.NetworkHelper
import com.homecredit.exam.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ForecastViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var mainRepository: MainRepository


    @Before
    fun setUp() {

    }

    @Mock
    lateinit var networkHelper: NetworkHelper

    @Mock
    private lateinit var apiForecastObserver: Observer<Result<List<ForecastItem>>>

    @Mock
    private lateinit var response: Response<ForecastResponse>

    @Test
    fun getForecastSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(ForecastResponse(1, emptyList<ForecastItem>())).`when`(response).body()
            doReturn(true).`when`(response).isSuccessful

            doReturn(response)
                .`when`(mainRepository)
                .getForecasts("1701668,1835848,3067696", "metric", "8beca4dc58974504cca73181ee8ca127")
            doReturn(true)
                .`when`(networkHelper)
                .isNetworkConnected()

            val forecastViewModel = ForecastViewModel(mainRepository, networkHelper)

            forecastViewModel.forecasts.observeForever(apiForecastObserver)
            verify(mainRepository).getForecasts("1701668,1835848,3067696", "metric", "8beca4dc58974504cca73181ee8ca127")
            verify(apiForecastObserver).onChanged(Result.loading(null))
            verify(apiForecastObserver).onChanged(Result.success(emptyList()))
            forecastViewModel.forecasts.removeObserver(apiForecastObserver)
        }
    }

    @Test
    fun getForecastNoInternet() {
        testCoroutineRule.runBlockingTest {
            doReturn(false)
                .`when`(networkHelper)
                .isNetworkConnected()

            val forecastViewModel = ForecastViewModel(mainRepository, networkHelper)
            forecastViewModel.forecasts.observeForever(apiForecastObserver)
            verify(apiForecastObserver).onChanged(Result.loading(null))
            verify(apiForecastObserver).onChanged(Result.error(null, "No internet connection"))
            forecastViewModel.forecasts.removeObserver(apiForecastObserver)
        }
    }
}