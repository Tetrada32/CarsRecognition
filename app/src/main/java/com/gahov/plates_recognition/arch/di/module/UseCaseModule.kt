package com.gahov.plates_recognition.arch.di.module


import com.gahov.domain.repository.CarRepository
import com.gahov.domain.usecase.carplate.DeleteLocalCarInfoUseCase
import com.gahov.domain.usecase.carplate.GetLocalCarInfoUseCase
import com.gahov.domain.usecase.carplate.GetRemoteCarInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    internal fun provideRemoteCarUseCase(
        repository: CarRepository
    ) = GetRemoteCarInfoUseCase(repository)

    @Provides
    @Singleton
    internal fun provideDeleteCarUseCase(
        repository: CarRepository
    ) = DeleteLocalCarInfoUseCase(repository)

    @Provides
    @Singleton
    internal fun provideLocalCarUseCase(
        repository: CarRepository
    ) = GetLocalCarInfoUseCase(repository)
}