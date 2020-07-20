package com.car_plate.car_plate_recognition.app.di

import com.car_plate.data.di.apiMapperModule
import com.car_plate.data.di.networkModule
import com.car_plate.data.di.repositoryModule


val app_module = listOf(
    tools,
    viewModelModule,
    useCaseModule,
    networkModule,
    apiMapperModule,
    repositoryModule
)
