package com.car_plate.car_plate_recognition.app

import android.app.Application
import com.car_plate.car_plate_recognition.app.di.app_module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(app_module)
        }
    }
}