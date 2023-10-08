package com.gahov.data.local.source.auth

import com.gahov.data.local.entities.TokenData
import com.gahov.domain.source.Source

interface TokenSource : Source {

    fun getToken(): TokenData

    fun updateToken(tokenData: TokenData)

    suspend fun clearToken()

}