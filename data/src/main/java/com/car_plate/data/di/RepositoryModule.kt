package com.car_plate.data.di

import com.car_plate.data.repository.user.CarServiceRetrofitAdapter
import com.car_plate.domain.repository.CarRepository
import com.car_plate.domain.repository.car.CarRemoteSource
import com.car_plate.domain.repository.car.CarRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val API_MAPPER_CAR_INFO = "API_MAPPER_CAR_INFO"

val repositoryModule = module {

    single<CarRepository>{
        CarRepositoryImpl(
            get(),
            get(named(API_MAPPER_CAR_INFO))
        )
    }

    single<CarRemoteSource> { CarServiceRetrofitAdapter(get(), get()) }
}