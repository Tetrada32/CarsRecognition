package com.gahov.plates_recognition.arch.coroutine.impl

import com.gahov.domain.entity.failure.Failure
import com.gahov.plates_recognition.arch.coroutine.CoroutineLauncher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

/**
 * An implementation of the [CoroutineLauncher] interface that provides a way to launch coroutines
 * with error handling for potential failures.
 *
 * @param scope The [CoroutineScope] on which the coroutines will be launched.
 * @param handleFailure An optional lambda function to handle failures that occur within
 * the launched coroutine.
 */

class DefaultCoroutineLauncher(
    private val scope: CoroutineScope,
    private val handleFailure: ((Failure) -> Unit)? = null
) : CoroutineLauncher {

    override fun launch(
        supervisor: Boolean,
        dispatcher: CoroutineDispatcher,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return scope.launch(context = dispatcher + errorHandler) {
            if (supervisor) {
                supervisorScope {
                    block.invoke(this)
                }
            } else {
                block.invoke(this)
            }
        }
    }

    private val errorHandler by lazy {
        CoroutineExceptionHandler { _, exception ->
            handleFailure?.invoke(Failure.CoroutineException(exception))
        }
    }
}