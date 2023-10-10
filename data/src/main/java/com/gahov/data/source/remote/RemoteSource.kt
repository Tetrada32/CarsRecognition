package com.gahov.data.source.remote

import com.gahov.data.entity.car.ApiCar
import com.gahov.domain.entity.common.Either
import com.gahov.domain.entity.failure.Failure

interface RemoteSource {

    suspend fun getCarInfo(carPlate: String): Either<Failure, ApiCar>
}