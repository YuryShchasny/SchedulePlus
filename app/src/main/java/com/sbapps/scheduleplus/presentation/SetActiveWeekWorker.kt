package com.sbapps.scheduleplus.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sbapps.scheduleplus.MyApplication
import com.sbapps.scheduleplus.R
import com.sbapps.scheduleplus.domain.usecases.week.GetWeekListUseCase
import com.sbapps.scheduleplus.domain.usecases.week.SetWeekActiveUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SetActiveWeekWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    @Inject
    lateinit var getWeekListUseCase: GetWeekListUseCase
    @Inject
    lateinit var setWeekActiveUseCase: SetWeekActiveUseCase

    private val notificationManager by lazy {
        applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    private val notificationBuilder by lazy {
        createNotificationBuilder(applicationContext)
    }

    private val component by lazy {
        (applicationContext as MyApplication).component
    }
    init {
        component.inject(this)
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun doWork(): Result {
        createNotificationChannel()
        showNotification()
        coroutineScope.launch {
            val weekList = getWeekListUseCase().first()
            val activeWeek = weekList.find { it.isActive }
            activeWeek?.let {
                val indexOfActiveWeek = weekList.indexOf(activeWeek)
                val nextWeek = weekList[(indexOfActiveWeek + 1) % weekList.size]
                setWeekActiveUseCase(nextWeek)
            }
        }
        return Result.success()
    }

    private fun showNotification() {
        val notification = notificationBuilder
            .build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_MIN
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotificationBuilder(context: Context) = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
        .setContentTitle(context.getString(R.string.app_name))
        .setContentText(context.getString(R.string.notification_text))
        .setSmallIcon(R.mipmap.ic_launcher)

    companion object {
        private const val CHANNEL_ID = "channel_id_1"
        private const val CHANNEL_NAME = "channel_worker"
        private const val NOTIFICATION_ID = 1
        private const val REPEAT_INTERVAL_DAYS = 7L
        const val WORK_NAME = "SetNextWeekActive"
        fun makeRequest(
            initialDelayInMillis: Long
        ): PeriodicWorkRequest {
            return PeriodicWorkRequest.Builder(
                SetActiveWeekWorker::class.java,
                REPEAT_INTERVAL_DAYS,
                TimeUnit.DAYS
            )
                .setConstraints(makeConstraints())
                .setInitialDelay(initialDelayInMillis, TimeUnit.MILLISECONDS)
                .build()
        }

        private fun makeConstraints() = Constraints.Builder().build()

    }

}