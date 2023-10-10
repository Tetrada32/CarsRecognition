package com.gahov.data.repository

import com.gahov.data.entity.car.ApiCar
import com.gahov.data.mapper.car.CarDomainToLocalMapper
import com.gahov.data.mapper.car.CarResponseToDomainMapper
import com.gahov.data.source.local.LocalSource
import com.gahov.data.source.remote.RemoteSource
import com.gahov.domain.entity.cars.CarEntity
import com.gahov.domain.entity.common.Either
import com.gahov.domain.entity.failure.Failure
import com.gahov.domain.repository.CarRepository

class CarRepositoryImpl(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource,
    private val responseMapper: CarResponseToDomainMapper,
    private val localMapper: CarDomainToLocalMapper,
) : CarRepository {

    override suspend fun loadRemoteCarInfoByDigits(carDigits: String): Either<Failure, CarEntity> {
        return when (val result = remoteSource.getCarInfo(carDigits)) {
            is Either.Left -> result
            is Either.Right -> Either.Right(processAndSaveSuccessResult(result.success))
        }
    }

    private suspend fun processAndSaveSuccessResult(result: ApiCar): CarEntity {
        val mappedResult = responseMapper.toDomain(result)
        saveCarData(mappedResult)
        return mappedResult
    }

    override suspend fun loadLocalCarsInfoList(): Either<Failure, List<CarEntity>> {
        return try {
            val cashedCarsList = localSource.getAllCarsData()
            Either.Right(cashedCarsList.map { localMapper.toDomain(it) })
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Left(Failure.Common(e.fillInStackTrace()))
        }
    }

    override suspend fun saveCarData(car: CarEntity) {
        val mappedItem = localMapper.toDatabase(car)
        localSource.saveCar(mappedItem)
    }

    override suspend fun deleteLocalCar(carDigits: String): Either<Failure, List<CarEntity>> {
        localSource.deleteCarDataByDigits(carDigits)
        return loadLocalCarsInfoList()
    }
}