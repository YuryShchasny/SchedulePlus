package com.sbapps.scheduleplus.presentation.edit.week

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbapps.scheduleplus.data.ScheduleRepositoryImpl
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.usecases.AddScheduleItemUseCase
import com.sbapps.scheduleplus.domain.usecases.DeleteScheduleItemUseCase
import com.sbapps.scheduleplus.domain.usecases.EditScheduleItemUseCase
import com.sbapps.scheduleplus.domain.usecases.GetWeekListUseCase
import kotlinx.coroutines.launch

class WeekEditViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ScheduleRepositoryImpl(application)

    private val deleteScheduleItemUseCase = DeleteScheduleItemUseCase(repository)
    private val addScheduleItemUseCase = AddScheduleItemUseCase(repository)
    private val getWeekListUseCase = GetWeekListUseCase(repository)
    private val editScheduleItemUseCase = EditScheduleItemUseCase(repository)

    var weekList = getWeekListUseCase()

    private var _isLoadFinished = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isLoadFinished: LiveData<Boolean> = _isLoadFinished


    private var _dialogClosed = MutableLiveData<Boolean>().apply {
        value = true
    }
    val dialogClosed: LiveData<Boolean> = _dialogClosed


    fun deleteScheduleItem(weekId: Int, scheduleItem: ScheduleItem) {
        viewModelScope.launch {
            deleteScheduleItemUseCase(weekId, scheduleItem)
        }
    }

    fun addScheduleItem(weekId: Int, scheduleItem: ScheduleItem) {
        viewModelScope.launch {
            addScheduleItemUseCase(weekId, scheduleItem)
        }
    }


    fun editScheduleItem(weekId: Int, scheduleItem: ScheduleItem) {
        viewModelScope.launch {
            editScheduleItemUseCase(weekId, scheduleItem)
        }
    }

    fun setDialogStateOpened() {
        _dialogClosed.value = false
    }

    fun setDialogStateClosed() {
        _dialogClosed.value = true
    }

    fun getDialogState(): Boolean {
        return _dialogClosed.value ?: throw NullPointerException()
    }

    fun setIsLoadFinished(isLoadFinished: Boolean) {
        _isLoadFinished.value = isLoadFinished
    }
}