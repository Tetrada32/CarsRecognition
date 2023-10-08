package com.gahov.domain.repository

import com.gahov.domain.entity.cars.Car
import com.gahov.domain.entity.common.Either
import com.gahov.domain.entity.failure.Failure

interface CarRepository {

    suspend fun getCarInfo(carPlate: String): Either<Failure, Car>

}