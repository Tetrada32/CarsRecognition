package com.gahov.plates_recognition.app.arch.component.error

import com.gahov.domain.entity.failure.Failure

interface ErrorHandler {

    fun parseFailure(failure: Failure)
}