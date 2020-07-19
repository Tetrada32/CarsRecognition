package com.car_plate.domain.repository

import com.car_plate.domain.entity.Car
import com.car_plate.domain.usecase.UseCaseResult

interface CarRepository {

    suspend fun getCarInfo(carPlate: String): UseCaseResult<Car>

}