package com.car_plate.data.di

import com.car_plate.data.entity.car.ApiCar
import com.car_plate.data.mapper.ApiMapper
import com.car_plate.domain.entity.Car
import org.koin.core.qualifier.named
import org.koin.dsl.module


const val API_MAPPER_CAR_PLATE_INFO = "API_MAPPER_CAR_PLATE_INFO"


val apiMapperModule = module {

//    single<ApiMapper<ApiCar, Car, Map<String, Any>>>(named(API_MAPPER_CAR_PLATE_INFO)) { ApiTokenMapper() }
}