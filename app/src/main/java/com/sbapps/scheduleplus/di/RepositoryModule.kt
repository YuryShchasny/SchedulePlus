package com.sbapps.scheduleplus.di

import com.sbapps.scheduleplus.data.ScheduleRepositoryImpl
import com.sbapps.scheduleplus.domain.repository.ScheduleRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @ApplicationScope
    @Binds
    fun bindScheduleRepository(impl: ScheduleRepositoryImpl) : ScheduleRepository
}