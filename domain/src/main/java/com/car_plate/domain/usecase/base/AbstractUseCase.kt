package com.car_plate.domain.usecase.base

import androidx.lifecycle.MutableLiveData
import com.zedra.domain.usecase.base.UseCaseParams

abstract class AbstractUseCase(canExecute: Boolean = true) {

    private val canExecute = MutableLiveData(canExecute)

    protected fun setCanExecute(canExecute: Boolean) {
        this.canExecute.postValue(canExecute)
    }

    fun execute(params : UseCaseParams? = null) {
        if (canExecute.value == true) {
            executeCore(params)
        }
    }

    open fun dispose() {
        /* no op */
    }

    abstract fun reset()

    protected abstract fun executeCore(params : UseCaseParams? = null)
}