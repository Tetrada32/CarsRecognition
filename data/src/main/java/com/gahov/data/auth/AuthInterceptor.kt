package com.gahov.data.auth

import com.gahov.data.ApiConstants.HEADER_ACCEPT
import okhttp3.Interceptor
import okhttp3.Response

//TODO remove
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.run {

            val decorateRequest = request()
                .newBuilder()
                .addHeader(HEADER_ACCEPT, "application/json")

            proceed(decorateRequest.build())
        }
    }
}