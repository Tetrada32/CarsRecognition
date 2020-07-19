package com.zedra.domain.usecase.base

import com.car_plate.domain.usecase.base.AsyncUseCase

abstract class SingleExecutableAsyncUseCase : AsyncUseCase() {

    override fun setState(state: State) {
        when (state) {
            State.FINISHED -> setCanExecute(false)
            else -> super.setState(state)
        }
    }
}