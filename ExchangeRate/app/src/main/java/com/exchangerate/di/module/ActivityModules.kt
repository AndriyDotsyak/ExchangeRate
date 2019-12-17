package com.exchangerate.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.exchangerate.screen.main.MainActivity
import com.exchangerate.screen.schedule.ScheduleActivity

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}

@Module
abstract class ScheduleActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeScheduleActivity(): ScheduleActivity
}