package com.gahov.data.remote.configuration.interceptor.utils.token

import com.gahov.data.local.entities.TokenData
import com.gahov.data.local.source.auth.TokenSource


class BearerProvider(
    private val tokenSource: TokenSource,
) : TokenProvider {

    @Synchronized
    override fun getToken() = tokenSource.getToken().accessToken

    @Synchronized
    override fun setToken(tokenData: TokenData) {
        tokenSource.updateToken(tokenData)
    }
}