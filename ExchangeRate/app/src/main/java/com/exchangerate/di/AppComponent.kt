package com.exchangerate.di

import com.exchangerate.App
import com.exchangerate.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ViewModelModule::class,
        DataModule::class,
        MainActivityModule::class,
        ScheduleActivityModule::class]
)

interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}