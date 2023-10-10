package com.gahov.domain.usecase.carplate

import com.gahov.domain.common.usecase.AsyncUseCase
import com.gahov.domain.common.usecase.UseCase
import com.gahov.domain.entity.cars.CarEntity
import com.gahov.domain.entity.common.Either
import com.gahov.domain.entity.failure.Failure
import com.gahov.domain.repository.CarRepository
import com.gahov.domain.usecase.carplate.params.GetCarParams


class GetRemoteCarInfoUseCase(
    private val carRepository: CarRepository
) : AsyncUseCase<CarEntity>() {

    override suspend fun execute(param: UseCase.Params?): Either<Failure, CarEntity> {
        return carRepository.loadRemoteCarInfoByDigits((param as GetCarParams).carPlate)
    }
}