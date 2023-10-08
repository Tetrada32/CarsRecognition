package com.gahov.plates_recognition.app.arch.di.module

import android.content.SharedPreferences
import com.gahov.data.local.source.auth.impl.ImplTokenSource
import com.gahov.domain.entity.component.device.DeviceInfo
import com.gahov.domain.entity.component.device.UserAgent
import com.gahov.domain.entity.component.device.UserAgentProvider
import com.gahov.plates_recognition.app.arch.component.device.AndroidDeviceInfo
import com.gahov.data.remote.configuration.interceptor.provider.DefaultInterceptorProvider
import com.gahov.data.remote.configuration.interceptor.utils.token.BearerProvider
import com.gahov.data.remote.configuration.interceptor.utils.token.TokenProvider
import com.gahov.data.remote.configuration.url.BaseUrlProvider
import com.gahov.data.remote.configuration.url.UrlProvider
import com.gahov.data.remote.protocol.MainProtocol
import com.gahov.domain.entity.component.logger.Logger
import com.gahov.data.local.storage.authorization.ImplAuthorizationLocalStorage
import com.gahov.data.remote.configuration.NetworkConfiguration
import com.gahov.data.remote.configuration.NetworkFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.gahov.plates_recognition.BuildConfig


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideNetworkFactory(
        configuration: NetworkConfiguration.AuthConfiguration,
        interceptor: DefaultInterceptorProvider
    ): NetworkFactory {
        return NetworkFactory(
            configuration = configuration,
            interceptor = interceptor
        )
    }

    @Provides
    @Singleton
    internal fun provideDefaultInterceptor(
        configuration: NetworkConfiguration.AuthConfiguration,
        userAgent: UserAgentProvider,
        logger: Logger,
    ) = DefaultInterceptorProvider(
        configuration = configuration,
        userAgentProvider = userAgent,
        logger = logger
    )

    @Provides
    @Singleton
    internal fun provideDefaultAuthConfiguration(
        tokenProvider: TokenProvider,
        urlProvider: UrlProvider,
    ) = NetworkConfiguration.AuthConfiguration(
        tokenProvider = tokenProvider,
        serverUrlProvider = urlProvider,
    )

    @Provides
    @Singleton
    internal fun provideTokenProvider(
        sharedPreferences: SharedPreferences
    ): TokenProvider = BearerProvider(
        tokenSource = ImplTokenSource(
            storage =
            ImplAuthorizationLocalStorage(preferences = sharedPreferences)
        )
    )

    @Provides
    @Singleton
    internal fun provideServerUrlProvider(): UrlProvider = BaseUrlProvider()

    @Provides
    @Singleton
    internal fun provideUserAgent(
        deviceInfo: DeviceInfo
    ): UserAgentProvider = UserAgent(deviceInfo = deviceInfo)

    @Provides
    @Singleton
    internal fun provideDeviceInfo(): DeviceInfo = object : AndroidDeviceInfo() {
        override val versionCode: Int = BuildConfig.VERSION_CODE
        override val versionName: String = BuildConfig.VERSION_NAME
    }

    @Provides
    @Reusable
    internal fun provideProtocol(
        configuration: NetworkConfiguration.AuthConfiguration,
        interceptor: DefaultInterceptorProvider
    ): MainProtocol {
        return NetworkFactory.createService(
            protocol = MainProtocol::class.java,
            configuration = configuration,
            interceptors = interceptor
        )
    }
}