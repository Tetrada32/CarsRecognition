package com.gahov.data.source.remote

import com.gahov.data.entity.car.ApiCar
import com.gahov.data.remote.call
import com.gahov.data.remote.protocol.MainProtocol
import com.gahov.domain.entity.common.Either
import com.gahov.domain.entity.failure.Failure


class RemoteSourceImpl(
    private val apiService: MainProtocol
) : RemoteSource {

    override suspend fun getCarInfo(carPlate: String): Either<Failure, ApiCar> {
        return call { apiService.getCarInfo(carPlate) }
    }
}