package com.car_plate.domain.usecase.base

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class AsyncUseCase : AbstractUseCase() {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO
    protected val scope = CoroutineScope(coroutineContext)

    val asyncState = MutableLiveData(State.IDLE)

    fun isProcessing() = asyncState.value == State.BUSY

    protected open fun setState(state: State) {
        when (state) {
            State.BUSY -> setCanExecute(false)
            State.FINISHED -> setCanExecute(true)
            State.FAILED -> setCanExecute(true)
            State.IDLE -> setCanExecute(true)
        }
        asyncState.postValue(state)
    }

    enum class State {
        IDLE,
        BUSY,
        FINISHED,
        FAILED
    }
}