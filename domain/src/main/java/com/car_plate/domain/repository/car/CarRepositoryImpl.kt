package com.car_plate.domain.repository.car

import com.car_plate.data.entity.car.ApiCar
import com.car_plate.data.mapper.ApiMapper
import com.car_plate.domain.entity.Car
import com.car_plate.domain.repository.CarRepository
import com.car_plate.domain.usecase.UseCaseResult

class CarRepositoryImpl(
    private val carRemoteSource: CarRemoteSource,
    private val carInfoMapper: ApiMapper<ApiCar, Car, Map<String, Any>>
) : CarRepository {

    override suspend fun getCarInfo(carPlate: String): UseCaseResult<Car> {
        return try {
            val apiUser = carRemoteSource.getCarInfo(carPlate)
            UseCaseResult.Success(carInfoMapper.toDomain(apiUser))
        } catch (error: Throwable) {
            UseCaseResult.Error(error)
        }
    }
}