package com.gahov.domain.usecase.carplate

import com.gahov.domain.common.usecase.AsyncUseCase
import com.gahov.domain.common.usecase.UseCase
import com.gahov.domain.entity.cars.CarEntity
import com.gahov.domain.entity.common.Either
import com.gahov.domain.entity.failure.Failure
import com.gahov.domain.repository.CarRepository


class GetLocalCarInfoUseCase(
    private val carRepository: CarRepository
) : AsyncUseCase<List<CarEntity>>() {

    override suspend fun execute(param: UseCase.Params?): Either<Failure, List<CarEntity>> {
        return carRepository.loadLocalCarsInfoList()
    }
}