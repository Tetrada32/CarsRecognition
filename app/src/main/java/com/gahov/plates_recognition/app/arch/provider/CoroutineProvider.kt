package com.gahov.plates_recognition.app.arch.provider

import com.gahov.plates_recognition.app.arch.coroutine.CoroutineLauncher

interface CoroutineProvider {
    val launcher: CoroutineLauncher
}