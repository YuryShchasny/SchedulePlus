package com.sbapps.scheduleplus.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sbapps.scheduleplus.presentation.MainViewModel
import com.sbapps.scheduleplus.presentation.edit.ScheduleEditViewModel
import com.sbapps.scheduleplus.presentation.edit.week.WeekEditViewModel
import com.sbapps.scheduleplus.presentation.main.ScheduleMainViewModel
import com.sbapps.scheduleplus.presentation.onboarding.OnBoardingViewModel
import com.sbapps.scheduleplus.presentation.schedule.ScheduleViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.internal.Provider
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ScheduleMainViewModel::class)
    fun bindScheduleMainViewModel(viewModel: ScheduleMainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ScheduleViewModel::class)
    fun bindScheduleViewModel(viewModel: ScheduleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ScheduleEditViewModel::class)
    fun bindScheduleEditViewModel(viewModel: ScheduleEditViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WeekEditViewModel::class)
    fun bindWeekEditViewModel(viewModel: WeekEditViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OnBoardingViewModel::class)
    fun bindOnBoardingViewModel(viewModel: OnBoardingViewModel): ViewModel

}