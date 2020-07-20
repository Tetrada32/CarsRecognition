package com.car_plate.car_plate_recognition.app.di

import com.car_plate.domain.usecase.carplate.GetCarInfoUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory { GetCarInfoUseCase(get()) }
}