package com.sbapps.scheduleplus.presentation.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sbapps.scheduleplus.data.ScheduleRepositoryImpl
import com.sbapps.scheduleplus.domain.entity.Week
import com.sbapps.scheduleplus.domain.usecases.AddWeekUseCase
import com.sbapps.scheduleplus.domain.usecases.DeleteWeekUseCase
import com.sbapps.scheduleplus.domain.usecases.GetWeekListUseCase
import com.sbapps.scheduleplus.domain.usecases.SetWeekActiveUseCase
import kotlinx.coroutines.launch

class ScheduleEditViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val FIRST_WEEK = 0
    }

    private val repository = ScheduleRepositoryImpl(application)
    private val getWeekListUseCase = GetWeekListUseCase(repository)
    private val addWeekUseCase = AddWeekUseCase(repository)
    private val deleteWeekUseCase = DeleteWeekUseCase(repository)
    private val setWeekActiveUseCase = SetWeekActiveUseCase(repository)

    var weekList = getWeekListUseCase()

    fun addWeek() {
        val active = getWeekListSize() == 0
        val newWeek = Week(active, mutableListOf())
        viewModelScope.launch {
            addWeekUseCase(newWeek)
        }
    }

    fun deleteWeek(week: Week) {
        viewModelScope.launch {
            deleteWeekUseCase(week)
        }
    }

    fun setWeekActive(week: Week) {
        viewModelScope.launch {
            setWeekActiveUseCase(week)
        }
    }

    private fun getWeekListSize(): Int = weekList.value?.size ?: FIRST_WEEK
}