package com.sbapps.scheduleplus.presentation.main

import androidx.lifecycle.ViewModel
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.entity.Week
import com.sbapps.scheduleplus.domain.usecases.scheduleitem.GetScheduleItemListUseCase
import com.sbapps.scheduleplus.domain.usecases.week.GetWeekListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ScheduleMainViewModel @Inject constructor(
    getScheduleItemListUseCase: GetScheduleItemListUseCase,
    getWeekListUseCase: GetWeekListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ScheduleMainState>(ScheduleMainState.Loading)
    val state = _state.asStateFlow()

    val scheduleItemList = getScheduleItemListUseCase()
    val weekList = getWeekListUseCase()

    private fun setContentState(weekList: List<Week>, scheduleItemList: List<ScheduleItem>) {
        _state.value = ScheduleMainState.Loading
        _state.value = ScheduleMainState.Content(weekList, scheduleItemList)
    }

    fun setWeekList(list: List<Week>) {
        val state = state.value as? ScheduleMainState.Content
        state?.let {
            setContentState(list, state.currencyScheduleItemList)
        } ?: setContentState(list, arrayListOf())
    }

    fun setScheduleItemList(list: List<ScheduleItem>) {
        val state = state.value as? ScheduleMainState.Content
        state?.let {
            setContentState(state.currencyWeekList, list)
        } ?: setContentState(arrayListOf(), list)
    }
}