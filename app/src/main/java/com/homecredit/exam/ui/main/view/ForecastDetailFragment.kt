package com.homecredit.exam.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.homecredit.exam.R
import com.homecredit.exam.data.model.ForecastItem
import com.homecredit.exam.databinding.FragmentForecastDetailBinding
import com.homecredit.exam.ui.base.BaseFragment
import com.homecredit.exam.ui.main.viewModel.ForecastDetailViewModel
import com.homecredit.exam.utils.Constants.BUNDLE_LOCATION_ID
import com.homecredit.exam.utils.FormatterUtils.CURRENT_TEMPERATURE_FORMAT
import com.homecredit.exam.utils.FormatterUtils.HI_TEMPERATURE_FORMAT
import com.homecredit.exam.utils.FormatterUtils.LOW_TEMPERATURE_FORMAT
import com.homecredit.exam.utils.FormatterUtils.getTemperature
import com.homecredit.exam.utils.Status
import org.koin.android.viewmodel.ext.android.viewModel

class ForecastDetailFragment : BaseFragment(R.layout.fragment_forecast_detail) {

    private val viewModel: ForecastDetailViewModel by viewModel()
    private var viewBinding: FragmentForecastDetailBinding? = null
    private val binding get() = viewBinding!!
    private var locationId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentForecastDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        setupUi()
        setupObservers()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).onShowBackButton(true)
        locationId = arguments?.getString(BUNDLE_LOCATION_ID)
        viewModel.fetchForecast(locationId)
    }

    override fun setupUi() {
        binding.btnFavorite.setOnClickListener {
            viewModel.onToggleFavorite(locationId, binding.btnFavorite.isChecked)
        }
    }

    override fun setupObservers() {
        viewModel.forecasts.observe(requireActivity(), {
            it?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        result.data?.let { response -> displayData(response) }
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        //Do nothing
                    }
                }
            }
        })
    }

    private fun displayData(forecast: ForecastItem) {
        binding.textCity.text = forecast.name
        binding.textTemperature.text = resources.getString(
            R.string.unit_celsius,
            getTemperature(CURRENT_TEMPERATURE_FORMAT, forecast.main.temperature)
        )
        binding.textTempRange.text = resources.getString(
            R.string.detail_hi_low_temp,
            getTemperature(HI_TEMPERATURE_FORMAT, forecast.main.maxTemperature),
            getTemperature(LOW_TEMPERATURE_FORMAT, forecast.main.minTemperature)
        )
        binding.textWeather.text = forecast.weatherList[0].weather
        binding.btnFavorite.isChecked = forecast.favorite
    }
}