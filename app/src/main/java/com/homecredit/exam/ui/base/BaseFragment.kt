package com.homecredit.exam.ui.base

import androidx.fragment.app.Fragment


abstract class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    abstract fun setupUi()
    abstract fun setupObservers()
}
