package com.car_plate.domain.repository.car

import com.car_plate.data.entity.car.ApiCar

interface CarRemoteSource {

    suspend fun getCarInfo(carPlate: String): ApiCar
}