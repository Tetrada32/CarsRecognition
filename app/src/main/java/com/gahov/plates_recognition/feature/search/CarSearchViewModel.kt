package com.gahov.plates_recognition.feature.search


import com.gahov.domain.entity.cars.CarEntity
import com.gahov.domain.entity.common.Either
import com.gahov.domain.entity.failure.Failure
import com.gahov.domain.usecase.carplate.GetRemoteCarInfoUseCase
import com.gahov.domain.usecase.carplate.params.GetCarParams
import com.gahov.plates_recognition.arch.controller.BaseViewModel
import com.gahov.plates_recognition.feature.search.command.CarSearchCommand
import javax.inject.Inject

class CarSearchViewModel @Inject constructor(
    private val loadCarInfoUseCase: GetRemoteCarInfoUseCase
) : BaseViewModel() {

    fun onNewCarDigits(carDigits: String) {
        if ((carDigits.chars()).count().toInt() == POSSIBLE_CAR_DIGITS_LENGTH) {
            searchCityByInputName(GetCarParams(carDigits))
        }
    }

    private fun searchCityByInputName(param: GetCarParams) {
        launch {
            when (val result = loadCarInfoUseCase.execute(param = param)) {
                is Either.Right -> onResultSuccess(result = result.success)
                is Either.Left -> onResultFailure(result.failure)
            }
        }
    }

    private fun onResultSuccess(result: CarEntity) {
        navigateDirection(
            CarSearchBottomDialogFragmentDirections.actionCarSearchToCarDetails(
                carData = result,
                carDigits = result.digits
            )
        )
    }

    private fun onResultFailure(failureResult: Failure) {
        handleCommand(CarSearchCommand.OnNetworkError(failureResult))
        handleFailure(failureResult)
    }

    companion object {
        private const val POSSIBLE_CAR_DIGITS_LENGTH = 8
    }
}