package com.gahov.plates_recognition.arch.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gahov.plates_recognition.arch.di.ViewModelFactory
import com.gahov.plates_recognition.arch.di.ViewModelKey
import com.gahov.plates_recognition.feature.details.CarDetailsViewModel
import com.gahov.plates_recognition.feature.home.HomeViewModel
import com.gahov.plates_recognition.feature.search.CarSearchViewModel
import com.gahov.plates_recognition.feature.selector.CarSelectorViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CarSelectorViewModel::class)
    abstract fun bindCarSelectorViewModel(viewModel: CarSelectorViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CarSearchViewModel::class)
    abstract fun bindCarSearchViewModel(viewModel: CarSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CarDetailsViewModel::class)
    abstract fun bindCarDetailsViewModel(viewModel: CarDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
}