package com.gahov.plates_recognition.app.arch.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface CoroutineLauncher {

    fun launch(supervisor: Boolean = true, block: suspend CoroutineScope.() -> Unit): Job

}