package com.gahov.domain.repository

import com.gahov.domain.entity.cars.CarEntity
import com.gahov.domain.entity.common.Either
import com.gahov.domain.entity.failure.Failure


/**
 * A repository interface for cars-related data async operations.
 */

interface CarRepository {

    /**
     * To load remote information for a specified car by digits (plates).
     *
     * @param carDigits The plates of the car for which to retrieve car information.
     * @return An [Either] instance containing either a [Failure] if the operation fails,
     * or a [CarEntity] representing the remote information for the car.
     */
    suspend fun loadRemoteCarInfoByDigits(carDigits: String): Either<Failure, CarEntity>

    /**
     * To load local information for all the saved cars-list.
     *
     * @return An [Either] instance containing either a [Failure] if the operation fails,
     * or a list of [CarEntity] representing the saved information for all the searched before
     * car list information.
     */
    suspend fun loadLocalCarsInfoList(): Either<Failure, List<CarEntity>>

    /**
     * To saves car data to a local storage.
     *
     * @param car The [CarEntity] representing the car information to be mapped and saved.
     */
    suspend fun saveCarData(car: CarEntity)

    /**
     * Deletes all information of specific car.
     *
     * @param carDigits The digits of the car for which to delete information.
     * @return An [Either] instance containing either a [Failure] if the operation fails,
     * or a [List] of [CarEntity] representing the updated list of car information after deletion.
     */
    suspend fun deleteLocalCar(carDigits: String): Either<Failure, List<CarEntity>>

}