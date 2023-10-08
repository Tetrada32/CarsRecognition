package com.gahov.domain.usecase.carplate

import com.gahov.domain.common.usecase.AsyncUseCase
import com.gahov.domain.common.usecase.UseCase
import com.gahov.domain.entity.cars.Car
import com.gahov.domain.entity.common.Either
import com.gahov.domain.entity.failure.Failure
import com.gahov.domain.repository.CarRepository

class GetCarParams(
    val carPlate: String
) : UseCase.Params()

class GetCarInfoUseCase(
    private val carRepository: CarRepository
) : AsyncUseCase<Car>() {

    override suspend fun execute(param: UseCase.Params?): Either<Failure, Car> {
        return carRepository.getCarInfo((param as GetCarParams).carPlate)
    }
}