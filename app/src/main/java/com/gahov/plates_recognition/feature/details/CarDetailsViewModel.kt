package com.gahov.plates_recognition.feature.details


import com.gahov.domain.entity.cars.CarEntity
import com.gahov.domain.entity.common.Either
import com.gahov.domain.entity.failure.Failure
import com.gahov.domain.usecase.carplate.GetRemoteCarInfoUseCase
import com.gahov.domain.usecase.carplate.params.GetCarParams
import com.gahov.plates_recognition.arch.controller.BaseViewModel
import com.gahov.plates_recognition.arch.router.command.Command
import com.gahov.plates_recognition.feature.details.command.CarDetailsCommand
import com.gahov.plates_recognition.feature.details.presenter.CarDetailsPresenter
import com.gahov.plates_recognition.feature.selector.CarDisplayModel
import com.gahov.plates_recognition.feature.selector.factory.CarEntityBuilder
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CarDetailsViewModel @Inject constructor(
    private val modelBuilder: CarEntityBuilder,
    private val loadCarUseCase: GetRemoteCarInfoUseCase
) : BaseViewModel(), CarDetailsPresenter {

    var model = CarDisplayModel()

    fun loadContent(args: CarDetailsFragmentArgs) {
        submitList(true)

        val carData = args.carData
        val carDigits = args.carDigits

        if (carData == null) {
            loadCarInfoByDigits(carDigits ?: return)
        } else {
            onResultSuccess(carData)
        }
    }

    private fun loadCarInfoByDigits(carDigits: String) {
        launch(Dispatchers.IO) {
            when (val result =
                loadCarUseCase.execute(param = GetCarParams(carDigits))) {
                is Either.Right -> onResultSuccess(result = result.success)
                is Either.Left -> onResultFailure(result.failure)
            }
        }
    }

    private fun onResultSuccess(result: CarEntity) {
        model = modelBuilder.buildCarModel(listOf(result))[0]
        submitList(false)
    }

    private fun submitList(isLoading: Boolean) {
//        model.isLoading.set(isLoading)
        handleCommand(CarDetailsCommand.DisplayContent(model))
    }

    private fun onResultFailure(failureResult: Failure) {
        model = CarDisplayModel()
        submitList(false)
        handleFailure(failureResult)
    }

    override fun onBackPressed() {
        handleCommand(Command.Back)
    }
}