package com.gahov.plates_recognition.arch.di.module


import com.gahov.data.local.storage.cars.CarsDao
import com.gahov.data.remote.protocol.MainProtocol
import com.gahov.data.source.local.LocalSource
import com.gahov.data.source.local.LocalSourceImpl
import com.gahov.data.source.remote.RemoteSource
import com.gahov.data.source.remote.RemoteSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SourceModule {

    @Provides
    @Singleton
    internal fun provideLocalSource(
        carsDao: CarsDao
    ): LocalSource {
        return LocalSourceImpl(
            carsDao
        )
    }

    @Provides
    @Singleton
    internal fun provideRemoteSource(
       protocol: MainProtocol
    ): RemoteSource {
        return RemoteSourceImpl(
           protocol
        )
    }
}