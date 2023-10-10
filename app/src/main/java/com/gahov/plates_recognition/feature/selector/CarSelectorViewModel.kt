package com.gahov.plates_recognition.feature.selector

import com.gahov.domain.entity.cars.CarEntity
import com.gahov.domain.entity.common.Either
import com.gahov.domain.usecase.carplate.DeleteLocalCarInfoUseCase
import com.gahov.domain.usecase.carplate.GetLocalCarInfoUseCase
import com.gahov.domain.usecase.carplate.params.GetCarParams
import com.gahov.plates_recognition.arch.controller.BaseViewModel
import com.gahov.plates_recognition.feature.selector.factory.CarEntityBuilder
import com.gahov.plates_recognition.feature.selector.command.CarSelectorCommand
import com.gahov.plates_recognition.feature.selector.presenter.CarSelectorPresenter
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CarSelectorViewModel @Inject constructor(
    private val carEntityBuilder: CarEntityBuilder,
    private val getLocalCarInfoUseCase: GetLocalCarInfoUseCase,
    private val deleteLocalCarUseCase: DeleteLocalCarInfoUseCase,
) : BaseViewModel(), CarSelectorPresenter {

    fun loadAllCashedCarData() {
        launch(Dispatchers.IO) {
            when (val result = getLocalCarInfoUseCase.execute()) {
                is Either.Right -> processResult(result.success)
                is Either.Left -> error(result.failure)
            }
        }
    }

    private fun processResult(result: List<CarEntity>) {
        handleCommand(
            CarSelectorCommand.DisplayContent(carEntityBuilder.buildCarModel(result))
        )
    }

    override fun onAddCarClick() {
        navigateDirection(
            CarSelectorFragmentDirections.actionCarSelectorToCarSearch()
        )
    }

    override fun onCarItemClick(carDigits: String) {
        navigateDirection(
            CarSelectorFragmentDirections.actionCarSelectorToCarDetails(
                carDigits = carDigits,
                carData = null
            )
        )
    }

    override fun onCarHistoryButtonClick(carDigits: String) {}

    fun deleteItem(carDigits: String) {
        launch(Dispatchers.IO) {
            deleteLocalCarUseCase.execute(GetCarParams(carDigits))
        }
    }
}