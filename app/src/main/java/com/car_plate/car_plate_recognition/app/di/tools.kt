package com.car_plate.car_plate_recognition.app.di

import com.car_plate.car_plate_recognition.app.util.ImageLoader
import org.koin.dsl.module

val tools = module {

    single { ImageLoader() }
}
