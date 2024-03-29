package com.sbapps.scheduleplus.presentation.edit.week

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.usecases.scheduleitem.AddScheduleItemUseCase
import com.sbapps.scheduleplus.domain.usecases.scheduleitem.DeleteScheduleItemUseCase
import com.sbapps.scheduleplus.domain.usecases.scheduleitem.EditScheduleItemUseCase
import com.sbapps.scheduleplus.domain.usecases.scheduleitem.GetScheduleItemListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeekEditViewModel @Inject constructor(
    private val deleteScheduleItemUseCase: DeleteScheduleItemUseCase,
    private val addScheduleItemUseCase: AddScheduleItemUseCase,
    private val editScheduleItemUseCase: EditScheduleItemUseCase,
    getScheduleItemListUseCase: GetScheduleItemListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<WeekEditFragmentState>(WeekEditFragmentState.Loading)
    val state = _state.asStateFlow()

    val scheduleItemList = getScheduleItemListUseCase()

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
    fun setContentState(scheduleItemList: List<ScheduleItem>) {
        _state.value = WeekEditFragmentState.Content(scheduleItemList)
    }

    fun setScheduleEditDialogState(scheduleItem: ScheduleItem) {
        _state.value = WeekEditFragmentState.ScheduleItemEditDialog(scheduleItem)
    }

}