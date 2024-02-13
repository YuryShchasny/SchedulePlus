package com.sbapps.scheduleplus.presentation.schedule

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sbapps.scheduleplus.data.ScheduleRepositoryImpl
import com.sbapps.scheduleplus.domain.usecases.GetWeekListUseCase

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ScheduleRepositoryImpl(application)

    private val getWeekListUseCase = GetWeekListUseCase(repository)

    var weekList = getWeekListUseCase()
    private var _isLoadFinished = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isLoadFinished: LiveData<Boolean> = _isLoadFinished

    fun setIsLoadFinished(isLoadFinished: Boolean) {
        _isLoadFinished.value = isLoadFinished
    }
}