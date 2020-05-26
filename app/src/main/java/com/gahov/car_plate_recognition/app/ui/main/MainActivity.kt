package com.gahov.car_plate_recognition.app.ui.main

import com.gahov.car_plate_recognition.BR
import com.gahov.car_plate_recognition.R
import com.gahov.car_plate_recognition.app.arch.BaseActivity
import com.gahov.car_plate_recognition.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by inject()

    override fun getLayoutId() = R.layout.activity_main

    override fun getBindingVariable() = BR.viewModel

    override fun initViewModel(): Any? {
        return viewModel
    }

    override fun setSubscribers() {

    }
}