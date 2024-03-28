package com.sbapps.scheduleplus.presentation.schedule

import androidx.lifecycle.ViewModel
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.entity.Week
import com.sbapps.scheduleplus.domain.usecases.scheduleitem.GetScheduleItemListUseCase
import com.sbapps.scheduleplus.domain.usecases.week.GetWeekListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
    getScheduleItemListUseCase: GetScheduleItemListUseCase,
    getWeekListUseCase: GetWeekListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ScheduleFragmentState>(ScheduleFragmentState.Loading)
    val state = _state.asStateFlow()

    val scheduleItemList = getScheduleItemListUseCase()
    val weekList = getWeekListUseCase()

    private fun setContentState(weekList: List<Week>, scheduleItemList: List<ScheduleItem>) {
        _state.value = ScheduleFragmentState.Content(weekList, scheduleItemList)
    }

    fun setWeekList(list: List<Week>) {
        val state = state.value as? ScheduleFragmentState.Content
        state?.let {
            setContentState(list, state.currencyScheduleItemList)
        } ?: setContentState(list, arrayListOf())
    }

    fun setScheduleItemList(list: List<ScheduleItem>) {
        val state = state.value as? ScheduleFragmentState.Content
        state?.let {
            setContentState(state.currencyWeekList, list)
        } ?: setContentState(arrayListOf(), list)
    }
}