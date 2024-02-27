package com.sbapps.scheduleplus.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sbapps.scheduleplus.data.ScheduleRepositoryImpl
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.entity.Week
import com.sbapps.scheduleplus.domain.usecases.scheduleitem.GetScheduleItemListUseCase
import com.sbapps.scheduleplus.domain.usecases.week.GetWeekListUseCase

class ScheduleMainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ScheduleRepositoryImpl(application)

    private val getScheduleItemListUseCase = GetScheduleItemListUseCase(repository)
    private val getWeekListUseCase = GetWeekListUseCase(repository)

    val scheduleItemList = getScheduleItemListUseCase()
    val weekList = getWeekListUseCase()

    private var _isLoad = MutableLiveData<Boolean>().apply {
        value = true
    }
    val isLoad: LiveData<Boolean> = _isLoad


    fun setIsLoad(isLoad: Boolean) {
        _isLoad.value = isLoad
    }
    fun getScheduleItemList() : List<ScheduleItem> {
        return scheduleItemList.value ?: listOf()
    }
    fun getWeekList() : List<Week> {
        return weekList.value ?: listOf()
    }
}