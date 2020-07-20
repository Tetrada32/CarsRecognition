package com.car_plate.data.di

import com.car_plate.data.repository.PreferenceStorage
import com.car_plate.domain.repository.CarRepository
import com.car_plate.data.repository.car.CarRemoteSource
import com.car_plate.data.repository.car.CarRepositoryImpl
import com.car_plate.data.repository.event.CarPlateRetrofitAdapter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {

    single<CarRepository>{
        CarRepositoryImpl(
            get(),
            get(named(API_MAPPER_CAR_INFO))
        )
    }

    single<CarRemoteSource> { CarPlateRetrofitAdapter(get(), get()) }

    single { PreferenceStorage(get()) }
}