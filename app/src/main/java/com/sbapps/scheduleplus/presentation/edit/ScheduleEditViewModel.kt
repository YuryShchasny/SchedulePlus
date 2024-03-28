package com.sbapps.scheduleplus.presentation.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbapps.scheduleplus.domain.entity.Week
import com.sbapps.scheduleplus.domain.usecases.week.AddWeekUseCase
import com.sbapps.scheduleplus.domain.usecases.week.DeleteWeekUseCase
import com.sbapps.scheduleplus.domain.usecases.week.GetWeekListUseCase
import com.sbapps.scheduleplus.domain.usecases.week.SetWeekActiveUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScheduleEditViewModel @Inject constructor(
    getWeekListUseCase : GetWeekListUseCase,
    private val addWeekUseCase : AddWeekUseCase,
    private val deleteWeekUseCase : DeleteWeekUseCase,
    private val setWeekActiveUseCase : SetWeekActiveUseCase
) : ViewModel() {

    companion object {
        private const val FIRST_WEEK = 0
    }

    val weekList = getWeekListUseCase()

    fun addWeek() {
        val active = getWeekListSize() == 0
        val newWeek = Week(active)
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