package com.gahov.plates_recognition.app.arch.di.module


import com.gahov.data.remote.protocol.MainProtocol
import com.gahov.data.repository.car.CarRemoteSource
import com.gahov.data.repository.event.CarPlateRetrofitAdapter
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
    internal fun provideRemoteSource(
       protocol: MainProtocol
    ): CarRemoteSource {
        return CarPlateRetrofitAdapter(
           protocol
        )
    }
}