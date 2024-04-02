package com.sbapps.scheduleplus.di

import com.sbapps.scheduleplus.presentation.edit.ScheduleEditFragment
import com.sbapps.scheduleplus.presentation.edit.week.WeekEditFragment
import com.sbapps.scheduleplus.presentation.main.ScheduleMainFragment
import com.sbapps.scheduleplus.presentation.onboarding.OnBoardingFragment
import com.sbapps.scheduleplus.presentation.schedule.ScheduleFragment
import dagger.Subcomponent

@Subcomponent
interface FragmentComponent {

   fun inject(fragment: ScheduleMainFragment)
    fun inject(fragment: ScheduleFragment)
    fun inject(fragment: ScheduleEditFragment)
    fun inject(fragment: WeekEditFragment)
    fun inject(fragment: OnBoardingFragment)
    @Subcomponent.Factory
    interface Factory {
        fun create() : FragmentComponent
    }
}