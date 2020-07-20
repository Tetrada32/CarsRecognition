package com.car_plate.data.repository.event

import com.car_plate.data.entity.ApiCar


interface CarPlateRemoteSource {

    suspend fun getCarInfo(carPlate: String) : ApiCar

}