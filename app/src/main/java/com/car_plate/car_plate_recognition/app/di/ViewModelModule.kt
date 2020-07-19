package com.car_plate.car_plate_recognition.app.di

import com.car_plate.car_plate_recognition.app.ui.home.HomeViewModel
import com.car_plate.car_plate_recognition.app.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MainViewModel() }

    viewModel { HomeViewModel() }
}