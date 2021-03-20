package com.homecredit.exam.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.homecredit.exam.R
import com.homecredit.exam.data.model.ForecastItem
import com.homecredit.exam.databinding.FragmentForecastBinding
import com.homecredit.exam.ui.base.BaseFragment
import com.homecredit.exam.ui.main.adapter.MainAdapter
import com.homecredit.exam.ui.main.viewModel.ForecastViewModel
import com.homecredit.exam.utils.Constants.BUNDLE_LOCATION_ID
import com.homecredit.exam.utils.Status
import org.koin.android.viewmodel.ext.android.viewModel

class ForecastFragment : BaseFragment(R.layout.fragment_forecast), MainAdapter.OnItemClickListener {

    private val viewModel: ForecastViewModel by viewModel()
    private lateinit var adapter: MainAdapter

    private var viewBinding: FragmentForecastBinding? = null
    private val binding get() = viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentForecastBinding.inflate(inflater, container, false)
        val view = binding.root
        setupUi()
        setupObservers()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).onShowBackButton(false)
    }

    override fun setupUi() {
        binding.swipeRefresh.setOnRefreshListener { viewModel.fetchForecasts() }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MainAdapter(arrayListOf(), this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter
    }

    override fun setupObservers() {
        viewModel.getLiveLocalForecasts().observe(requireActivity(), {
            retrieveList(it)
        })

        viewModel.forecasts.observe(requireActivity(), {
            it?.let { result ->

                when (result.status) {
                    Status.SUCCESS -> {
                        binding.swipeRefresh.isRefreshing = false
                        result.data?.let { response -> retrieveList(response) }
                    }
                    Status.ERROR -> {
                        binding.swipeRefresh.isRefreshing = false
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.swipeRefresh.isRefreshing = true
                    }
                }
            }
        })
    }

    private fun retrieveList(forecasts: List<ForecastItem>) {
        adapter.apply {
            addUsers(forecasts)
            notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }



    override fun onItemClick(position: Int) {
        val forecast = adapter.getItem(position)
        val bundle = Bundle()
        bundle.putString(BUNDLE_LOCATION_ID, forecast.id)
        findNavController().navigate(R.id.action_ForecastFragment_to_ForecastDetailFragment, bundle)
    }
}