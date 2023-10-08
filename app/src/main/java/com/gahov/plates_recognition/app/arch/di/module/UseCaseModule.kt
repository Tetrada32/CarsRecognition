package com.gahov.plates_recognition.app.arch.di.module


import com.gahov.domain.repository.CarRepository
import com.gahov.domain.usecase.carplate.GetCarInfoUseCase
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
    internal fun provideUseCase(
        repository: CarRepository
    ) = GetCarInfoUseCase(repository)
}