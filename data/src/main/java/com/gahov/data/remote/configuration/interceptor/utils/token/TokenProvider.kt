package com.gahov.data.remote.configuration.interceptor.utils.token

import com.gahov.data.local.entities.TokenData


interface TokenProvider {

    fun getToken(): String?

    fun setToken(tokenData: TokenData)
}