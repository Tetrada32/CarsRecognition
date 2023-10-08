package com.gahov.data.remote.configuration.convertor

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter

class KotlinConverterFactory : ConverterFactoryProvider {

    private val contentType: MediaType by lazy { "application/json".toMediaType() }
    private val jsonConfiguration: Json by lazy { Json { ignoreUnknownKeys = true } }

    @OptIn(ExperimentalSerializationApi::class)
    override val converterFactory: Converter.Factory by lazy {
        jsonConfiguration.asConverterFactory(
            contentType
        )
    }
}