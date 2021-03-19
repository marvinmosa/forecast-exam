package com.homecredit.exam.di.module

import com.homecredit.exam.ui.main.viewModel.ForecastDetailViewModel
import com.homecredit.exam.ui.main.viewModel.ForecastViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        ForecastViewModel(get(), get())
    }
    viewModel{
        ForecastDetailViewModel(get(),get())
    }
}