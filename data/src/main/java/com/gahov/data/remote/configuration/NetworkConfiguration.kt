package com.gahov.data.remote.configuration

import com.gahov.data.remote.configuration.convertor.ConverterFactoryProvider
import com.gahov.data.remote.configuration.convertor.KotlinConverterFactory
import com.gahov.data.remote.configuration.interceptor.utils.token.TokenProvider
import com.gahov.data.remote.configuration.url.UrlProvider

sealed class NetworkConfiguration {
    abstract val serverUrlProvider: UrlProvider

    open val timeout: Long = 30L * 1000

    open val withLogs = true
    open val withAuth = false

    open val converterFactoryProvider: ConverterFactoryProvider = KotlinConverterFactory()

    open class AuthConfiguration(
        val tokenProvider: TokenProvider,
        override val serverUrlProvider: UrlProvider,
    ) : NetworkConfiguration() {
        override val withAuth: Boolean = true
    }

    class DefaultConfiguration(
        override val serverUrlProvider: UrlProvider,
    ) : NetworkConfiguration()
}