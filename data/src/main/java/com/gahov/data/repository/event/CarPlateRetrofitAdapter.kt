package com.gahov.data.repository.event

import com.gahov.data.entity.car.ApiCar
import com.gahov.data.remote.call
import com.gahov.data.remote.protocol.MainProtocol
import com.gahov.data.repository.car.CarRemoteSource
import com.gahov.domain.entity.common.Either
import com.gahov.domain.entity.failure.Failure


class CarPlateRetrofitAdapter(
    private val apiService: MainProtocol
) :  CarRemoteSource {

    override suspend fun getCarInfo(carPlate: String): Either<Failure, ApiCar> {
        return call { apiService.getCarInfo(carPlate)}
    }
}