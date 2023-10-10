package com.gahov.plates_recognition.arch.component.error

import com.gahov.domain.entity.component.logger.Level
import com.gahov.domain.entity.component.logger.Logger
import com.gahov.domain.entity.failure.Failure

open class DefaultFailureHandler(
    private val logger: Logger
) : ErrorHandler {

    override fun parseFailure(failure: Failure) {
        when (failure) {
            is Failure.Common -> commonFailure(failure)
            is Failure.CoroutineException -> logError(failure.throwable)
            is Failure.DataSourceException -> logError(failure.throwable)
            is Failure.FeatureFailure -> featureFailure(failure)
            else -> logger.log(
                level = Level.Error,
                message = failure.toString()
            )
        }
    }

    private fun commonFailure(failure: Failure.Common) {
        val message = failure.throwable?.message
        if (message.isNullOrEmpty()) {
            logWarning(failure.toString())
        } else {
            logWarning(message)
        }
    }

    protected open fun featureFailure(failure: Failure.FeatureFailure) {
        logger.log(
            level = Level.Warning,
            message = "method featureFailure isn't implement for $failure"
        )
    }

    private fun logWarning(errorMessage: String) {
        logger.log(level = Level.Warning, message = errorMessage)
    }

    private fun logError(throwable: Throwable) {
        logger.log(level = Level.Error, message = throwable.message, throwable = throwable)
    }
}