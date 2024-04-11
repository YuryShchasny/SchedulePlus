package com.sbapps.scheduleplus.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import java.util.Calendar

class MainViewModel : ViewModel() {

    private var workManager: WorkManager? = null
    fun startWorker(context: Context) {
        workManager = WorkManager.getInstance(context)
        workManager?.enqueueUniquePeriodicWork(
            SetActiveWeekWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            SetActiveWeekWorker.makeRequest(getMillisUntilNextMonday())
        )
    }

    fun cancelWorker(context: Context) {
        workManager?.cancelAllWork() ?: WorkManager.getInstance(context).cancelAllWork()
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