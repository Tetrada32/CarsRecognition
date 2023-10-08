package com.gahov.data.remote.configuration.interceptor.utils


import com.gahov.domain.entity.component.logger.Level
import com.gahov.domain.entity.component.logger.Logger
import okhttp3.logging.HttpLoggingInterceptor

class InterceptorLogger(private val logger: Logger) : HttpLoggingInterceptor.Logger {

    private val loggerConfiguration = logger.getConfiguration().copy(className = "Network")

    override fun log(message: String) {
        logger.log(
            level = Level.Info,
            message = message,
            configuration = loggerConfiguration
        )
    }
}
