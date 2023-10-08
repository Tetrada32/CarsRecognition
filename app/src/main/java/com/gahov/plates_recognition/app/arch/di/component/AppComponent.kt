package com.gahov.plates_recognition.app.arch.di.component

import android.app.Application
import com.gahov.plates_recognition.app.App
import com.gahov.plates_recognition.app.arch.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.MembersInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent : MembersInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}