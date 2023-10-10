package com.gahov.data.remote.configuration.interceptor

import com.gahov.domain.entity.component.device.UserAgentProvider
import okhttp3.Interceptor
import okhttp3.Response

class UserAgentInterceptor(private val userAgentProvider: UserAgentProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithUserAgent = originalRequest.newBuilder()
            .header(UA_HEADER_KEY, userAgentProvider.userAgent)
            .header(ACCEPT_HEADER_KEY, ACCEPT_HEADER_VALUE)
            .build()
        return chain.proceed(requestWithUserAgent)
    }

    companion object {
        private const val UA_HEADER_KEY = "User-Agent"
        private const val ACCEPT_HEADER_KEY = "Accept"
        private const val ACCEPT_HEADER_VALUE = "application/json"
    }
}
