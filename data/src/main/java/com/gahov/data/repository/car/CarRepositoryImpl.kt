package com.gahov.data.repository.car

import com.gahov.data.entity.car.ApiCar
import com.gahov.data.mapper.common.ApiMapper
import com.gahov.domain.entity.cars.Car
import com.gahov.domain.entity.common.Either
import com.gahov.domain.entity.failure.Failure
import com.gahov.domain.repository.CarRepository

class CarRepositoryImpl(
    private val carRemoteSource: CarRemoteSource,
    private val carInfoMapper: ApiMapper<ApiCar, Car, Map<String, Any>>
) : CarRepository {

    override suspend fun getCarInfo(carPlate: String): Either<Failure, Car> {
        return when (val result = carRemoteSource.getCarInfo(carPlate)) {
            is Either.Left -> result
            is Either.Right -> Either.Right(carInfoMapper.toDomain(result.success))
        }
    }
}