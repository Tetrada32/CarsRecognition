package com.car_plate.data.repository.car

import com.car_plate.data.entity.*

interface CarRemoteSource {

    suspend fun getCarInfo(carPlate: String): ApiCar
}