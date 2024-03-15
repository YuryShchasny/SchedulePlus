package com.sbapps.scheduleplus.di

import android.app.Application
import com.sbapps.scheduleplus.presentation.main.ScheduleMainFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, RepositoryModule::class])
interface ApplicationComponent {

    fun fragmentComponentFactory() : FragmentComponent.Factory

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance application: Application
        ) : ApplicationComponent
    }
}