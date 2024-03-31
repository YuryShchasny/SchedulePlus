package com.sbapps.scheduleplus.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import java.util.Calendar

class MainViewModel : ViewModel() {
    fun startWorker(context: Context) {

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            SetActiveWeekWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            SetActiveWeekWorker.makeRequest(getMillisUntilNextMonday())
        )
    }

    private fun getMillisUntilNextMonday(): Long {
        val calendar = Calendar.getInstance()
        val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val daysUntilMonday = if (currentDayOfWeek == Calendar.SUNDAY) {
            1
        } else {
            (Calendar.MONDAY - currentDayOfWeek + 7) % 8
        }
        calendar.add(Calendar.DAY_OF_YEAR, daysUntilMonday)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return (calendar.timeInMillis - System.currentTimeMillis())
    }
}