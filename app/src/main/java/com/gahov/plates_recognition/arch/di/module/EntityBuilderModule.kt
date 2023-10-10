package com.gahov.plates_recognition.arch.di.module

import com.gahov.plates_recognition.feature.selector.factory.CarEntityBuilder
import com.gahov.plates_recognition.feature.selector.factory.CarListToCarModelBuilder
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class EntityBuilderModule {

    @Provides
    @Reusable
    internal fun provideWeatherEntityBuilder(): CarEntityBuilder = CarListToCarModelBuilder()
}