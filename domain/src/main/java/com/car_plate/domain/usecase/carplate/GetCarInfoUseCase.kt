package com.car_plate.domain.usecase.carplate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.car_plate.domain.entity.Car
import com.car_plate.domain.repository.CarRepository
import com.car_plate.domain.usecase.UseCaseResult
import com.car_plate.domain.usecase.base.AsyncUseCase
import com.zedra.domain.usecase.base.UseCaseParams
import kotlinx.coroutines.launch

class GetCarParams(
    val carPlate: String
) : UseCaseParams()

class GetCarInfoUseCase(
    private val carRepository: CarRepository
) : AsyncUseCase() {

    private val _carSuccess = MutableLiveData<Car>()
    val carSuccess: LiveData<Car> = _carSuccess

    private val _carError = MutableLiveData<Throwable>()
    val carError: LiveData<Throwable> = _carError

    override fun reset() {
        asyncState.value = State.IDLE
        _carSuccess.value = null
        _carError.value = null
    }

    override fun executeCore(params: UseCaseParams?) {
        val carParams = params as? GetCarParams
            ?: throw IllegalArgumentException()

        setState(State.BUSY)

        scope.launch {
            when (val it = carRepository.getCarInfo(carParams.carPlate)) {
                is UseCaseResult.Success -> {
                    _carSuccess.postValue(it.data)
                    setState(State.FINISHED)
                }

                is UseCaseResult.Error -> {
                    _carError.postValue(it.exception)
                    setState(State.FAILED)
                }
            }
        }
    }
}