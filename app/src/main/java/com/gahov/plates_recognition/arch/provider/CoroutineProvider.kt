package com.gahov.plates_recognition.arch.provider

import com.gahov.plates_recognition.arch.coroutine.CoroutineLauncher

interface CoroutineProvider {
    val launcher: CoroutineLauncher
}