package com.gahov.plates_recognition.arch.di.module

import com.gahov.data.mapper.car.CarDomainToLocalMapper
import com.gahov.data.mapper.car.CarResponseToDomainMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {

    @Provides
    @Singleton
    internal fun provideApiMapper(): CarResponseToDomainMapper = CarResponseToDomainMapper()


    @Provides
    @Singleton
    internal fun provideLocalMapper(): CarDomainToLocalMapper = CarDomainToLocalMapper()
}