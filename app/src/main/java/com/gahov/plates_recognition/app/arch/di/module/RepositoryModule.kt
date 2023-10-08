package com.gahov.plates_recognition.app.arch.di.module

import com.gahov.data.mapper.CarInfoMapper
import com.gahov.data.repository.car.CarRemoteSource
import com.gahov.data.repository.car.CarRepositoryImpl
import com.gahov.domain.repository.CarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideRepository(
        mapper: CarInfoMapper,
        remoteSource: CarRemoteSource
    ): CarRepository {
        return CarRepositoryImpl(remoteSource, mapper)
    }
}