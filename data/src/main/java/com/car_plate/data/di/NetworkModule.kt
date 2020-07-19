package com.car_plate.data.di

import com.car_plate.data.network.*
import org.koin.dsl.module


val networkModule = module {

    single<ResponseTransformer> { ResponseTransformerImpl(get()) }

    single<ResponseErrorExtractor> { ApiErrorParser(get()) }

    single { NetworkClientProvider.provideApiService(get(), ApiService::class.java) }

    single { NetworkClientProvider.provideRetrofit(get(), get()) }

    single { NetworkClientProvider.provideOkHttp(get()) }

    single { NetworkClientProvider.provideAuthInterceptor() }

    single { NetworkClientProvider.provideGson() }
}