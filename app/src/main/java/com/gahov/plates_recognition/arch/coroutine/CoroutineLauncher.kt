package com.gahov.plates_recognition.arch.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * An interface for launching coroutines with specified options.
 */
interface CoroutineLauncher {
    fun launch(
        supervisor: Boolean = true,
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        block: suspend CoroutineScope.() -> Unit
    ): Job
}