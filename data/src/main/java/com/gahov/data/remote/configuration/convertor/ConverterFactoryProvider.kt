package com.gahov.data.remote.configuration.convertor

import retrofit2.Converter

interface ConverterFactoryProvider {
    val converterFactory: Converter.Factory
}