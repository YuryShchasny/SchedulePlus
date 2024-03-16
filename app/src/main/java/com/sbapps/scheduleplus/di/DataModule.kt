package com.sbapps.scheduleplus.di

import android.app.Application
import androidx.room.Room
import com.sbapps.scheduleplus.data.AppDatabase
import com.sbapps.scheduleplus.data.ScheduleItemDao
import com.sbapps.scheduleplus.data.WeekDao
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    @ApplicationScope
    fun provideWeekDao(database: AppDatabase): WeekDao {
        return database.weekDao()
    }

    @Provides
    @ApplicationScope
    fun provideScheduleItemDao(database: AppDatabase): ScheduleItemDao {
        return database.scheduleItemDao()
    }

    @Provides
    @ApplicationScope
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "schedule_plus.db"
        ).build()
    }
}