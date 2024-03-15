package com.sbapps.scheduleplus.di

import android.app.Application
import androidx.lifecycle.ViewModel
import com.sbapps.scheduleplus.data.AppDatabase
import com.sbapps.scheduleplus.data.ScheduleItemDao
import com.sbapps.scheduleplus.data.ScheduleRepositoryImpl
import com.sbapps.scheduleplus.data.WeekDao
import com.sbapps.scheduleplus.domain.repository.ScheduleRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class DataModule {
    @Provides
    fun provideWeekDao(database: AppDatabase) : WeekDao {
        return database.weekDao()
    }

    @Provides
    fun provideScheduleItemDao(database: AppDatabase) : ScheduleItemDao {
        return database.scheduleItemDao()
    }

    @Provides
    fun provideAppDatabase(application: Application) : AppDatabase {
        return AppDatabase.getDatabase(application)
    }

}