package com.gahov.plates_recognition.app.arch.component.logger

import android.util.Log
import com.gahov.domain.entity.component.logger.Level
import com.gahov.domain.entity.component.logger.Logger
import com.gahov.domain.entity.component.logger.configuration.LoggerConfiguration
import com.gahov.plates_recognition.app.arch.component.logger.configuration.DefaultLoggerConfiguration

class AndroidLogger(
    private var configuration: LoggerConfiguration = DefaultLoggerConfiguration()
) : Logger {

    override fun getConfiguration(): LoggerConfiguration = configuration

    override fun setConfiguration(configuration: LoggerConfiguration) {
        this.configuration = configuration
    }

    override fun log(
        level: Level,
        message: String?,
        throwable: Throwable?,
        configuration: LoggerConfiguration
    ) {
        if (configuration.isEnabled) {
            val logMessage = message ?: EMPTY_MESSAGE
            when (level) {
                Level.Debug -> d(
                    message = logMessage,
                    exception = throwable,
                    configuration = configuration
                )

                Level.Info -> i(
                    message = logMessage,
                    exception = throwable,
                    configuration = configuration
                )

                Level.Warning -> w(
                    message = logMessage,
                    exception = throwable,
                    configuration = configuration
                )

                Level.Error -> e(
                    message = logMessage,
                    exception = throwable,
                    configuration = configuration
                )
            }
        }
    }

    private fun d(message: String, exception: Throwable?, configuration: LoggerConfiguration) {
        Log.d(configuration.className, message, exception)
    }

    private fun i(message: String, exception: Throwable?, configuration: LoggerConfiguration) {
        Log.i(configuration.className, message, exception)
    }

    private fun w(message: String, exception: Throwable?, configuration: LoggerConfiguration) {
        Log.w(configuration.className, message, exception)
    }

    private fun e(message: String, exception: Throwable?, configuration: LoggerConfiguration) {
        Log.e(configuration.className, message, exception)
    }

    companion object {
        private const val EMPTY_MESSAGE = ""
    }
}