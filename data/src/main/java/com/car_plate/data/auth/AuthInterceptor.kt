package com.car_plate.data.auth

import com.car_plate.data.ApiConstants.HEADER_ACCEPT
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.run {

            val decorateRequest = request()
                .newBuilder()
                .addHeader(HEADER_ACCEPT, "*/*")

            proceed(decorateRequest.build())
        }
    }
}