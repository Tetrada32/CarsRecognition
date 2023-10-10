package com.gahov.domain.usecase.carplate

import com.gahov.domain.common.usecase.AsyncUseCase
import com.gahov.domain.common.usecase.UseCase
import com.gahov.domain.entity.cars.CarEntity
import com.gahov.domain.entity.common.Either
import com.gahov.domain.entity.failure.Failure
import com.gahov.domain.repository.CarRepository
import com.gahov.domain.usecase.carplate.params.GetCarParams

/**
 * A use case class that handles the deletion of a local cars information.
 *
 * @param repository The repository responsible for data access related to car information.
 * @constructor Creates a DeleteLocalCarInfoUseCase with the provided [repository].
 */
class DeleteLocalCarInfoUseCase(
    private val repository: CarRepository
) : AsyncUseCase<List<CarEntity>>() {


    /**
     * Executes the use case to delete information for a specified car.
     *
     * @param param The parameters for executing the use case, which should be of type [GetCarParams].
     * @return An [Either] instance containing either a [Failure] if the operation fails, or a [List]
     * of [CarEntity] representing the deleted car information.
     *
     * See [Either] details.
     */
    override suspend fun execute(param: UseCase.Params?): Either<Failure, List<CarEntity>> {
        val params = param as? GetCarParams
        return repository.deleteLocalCar(params?.carPlate ?: "")
    }
}