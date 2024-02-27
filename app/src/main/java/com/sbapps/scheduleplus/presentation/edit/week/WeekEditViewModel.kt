package com.sbapps.scheduleplus.presentation.edit.week

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbapps.scheduleplus.data.ScheduleRepositoryImpl
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.usecases.scheduleitem.AddScheduleItemUseCase
import com.sbapps.scheduleplus.domain.usecases.scheduleitem.DeleteScheduleItemUseCase
import com.sbapps.scheduleplus.domain.usecases.scheduleitem.EditScheduleItemUseCase
import com.sbapps.scheduleplus.domain.usecases.scheduleitem.GetScheduleItemListUseCase
import kotlinx.coroutines.launch

class WeekEditViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ScheduleRepositoryImpl(application)

    private val deleteScheduleItemUseCase = DeleteScheduleItemUseCase(repository)
    private val addScheduleItemUseCase = AddScheduleItemUseCase(repository)
    private val editScheduleItemUseCase = EditScheduleItemUseCase(repository)
    private val getScheduleItemListUseCase = GetScheduleItemListUseCase(repository)

    val scheduleItemList = getScheduleItemListUseCase()

    private var _isLoad = MutableLiveData<Boolean>().apply {
        value = true
    }
    val isLoad: LiveData<Boolean> = _isLoad


    private var _dialogClosed = MutableLiveData<Boolean>().apply {
        value = true
    }
    val dialogClosed: LiveData<Boolean> = _dialogClosed


    fun deleteScheduleItem(scheduleItem: ScheduleItem) {
        viewModelScope.launch {
            deleteScheduleItemUseCase(scheduleItem)
        }
    }

    fun addScheduleItem(scheduleItem: ScheduleItem) {
        viewModelScope.launch {
            addScheduleItemUseCase(scheduleItem)
        }
    }


    fun editScheduleItem(scheduleItem: ScheduleItem) {
        viewModelScope.launch {
            editScheduleItemUseCase(scheduleItem)
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

    fun setIsLoad(isLoad: Boolean) {
        _isLoad.value = isLoad
    }
}