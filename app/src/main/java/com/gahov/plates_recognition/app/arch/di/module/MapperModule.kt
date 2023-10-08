package com.gahov.plates_recognition.app.arch.di.module

import com.gahov.data.mapper.CarInfoMapper
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
    internal fun provideMapper(): CarInfoMapper = CarInfoMapper()
}