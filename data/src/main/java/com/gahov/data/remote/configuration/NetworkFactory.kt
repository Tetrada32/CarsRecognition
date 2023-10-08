package com.gahov.data.remote.configuration

import com.gahov.data.remote.configuration.interceptor.provider.InterceptorProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class NetworkFactory(
    private val configuration: NetworkConfiguration,
    private val interceptor: InterceptorProvider,
) {

    private fun <S> createService(protocol: Class<S>): S {
        return retrofit.create(protocol)
    }

    private val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(configuration.serverUrlProvider.getBaseUrl())
            .addConverterFactory(configuration.converterFactoryProvider.converterFactory)
            .client(okHttpClient)
            .build()

    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder().apply {
            connectTimeout(configuration.timeout, TimeUnit.MILLISECONDS)
            writeTimeout(configuration.timeout, TimeUnit.MILLISECONDS)
            readTimeout(configuration.timeout, TimeUnit.MILLISECONDS)
            interceptor.interceptors.forEach {
                addInterceptor(it)
            }
            interceptor.networkInterceptors.forEach {
                addNetworkInterceptor(it)
            }
        }.build()

    companion object {
        fun <S> createService(
            protocol: Class<S>,
            configuration: NetworkConfiguration,
            interceptors: InterceptorProvider,
        ): S {
            return NetworkFactory(
                configuration = configuration,
                interceptor = interceptors
            ).createService(protocol = protocol)
        }
    }
}