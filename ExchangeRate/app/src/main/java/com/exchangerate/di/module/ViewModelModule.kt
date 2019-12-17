package com.exchangerate.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.exchangerate.screen.base.ViewModelFactory
import com.exchangerate.screen.main.MainViewModel
import com.exchangerate.screen.schedule.ScheduleViewModel


@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ScheduleViewModel::class)
    abstract fun bindScheduleViewModel(viewModel: ScheduleViewModel): ViewModel
}