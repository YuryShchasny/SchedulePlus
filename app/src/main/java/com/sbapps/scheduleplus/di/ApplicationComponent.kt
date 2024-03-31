package com.sbapps.scheduleplus.di

import android.app.Application
import com.sbapps.scheduleplus.presentation.SetActiveWeekWorker
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, RepositoryModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun fragmentComponentFactory() : FragmentComponent.Factory

    fun inject(worker: SetActiveWeekWorker)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance application: Application
        ) : ApplicationComponent
    }
}