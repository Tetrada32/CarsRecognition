package com.gahov.data.repository.car

import com.gahov.data.entity.car.ApiCar
import com.gahov.domain.entity.common.Either
import com.gahov.domain.entity.failure.Failure

interface CarRemoteSource {

    suspend fun getCarInfo(carPlate: String): Either<Failure, ApiCar>
}