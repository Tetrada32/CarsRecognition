package com.gahov.plates_recognition.arch.di.module


import com.gahov.data.mapper.car.CarDomainToLocalMapper
import com.gahov.data.mapper.car.CarResponseToDomainMapper
import com.gahov.data.source.remote.RemoteSource
import com.gahov.data.repository.CarRepositoryImpl
import com.gahov.data.source.local.LocalSource
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
        remoteSource: RemoteSource,
        localSource: LocalSource,
        localMapper: CarDomainToLocalMapper,
        remoteMapper: CarResponseToDomainMapper

    ): CarRepository {
        return CarRepositoryImpl(remoteSource, localSource, remoteMapper, localMapper)
    }
}