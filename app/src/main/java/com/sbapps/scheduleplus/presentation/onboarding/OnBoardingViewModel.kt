package com.sbapps.scheduleplus.presentation.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class OnBoardingViewModel @Inject constructor() : ViewModel() {

    private var onBoardingList = listOf<OnBoarding>()
    private val _currentOnBoarding by lazy {
        MutableStateFlow(onBoardingList.firstOrNull())
    }
    val currentOnBoarding = _currentOnBoarding.asStateFlow()

    fun setList(isNightMode: Boolean) {
        onBoardingList = if (isNightMode) {
            OnBoarding.nightList
        } else {
            OnBoarding.lightList
        }
        _currentOnBoarding.value = onBoardingList.first()
    }

    fun getNextOnBoarding(): Boolean {
        return if (onBoardingList.indexOf(_currentOnBoarding.value) < (onBoardingList.size - 1)) {
            _currentOnBoarding.value =
                onBoardingList[onBoardingList.indexOf(_currentOnBoarding.value) + 1]
            true
        } else {
            false
        }
    }

    fun getIndexOfOnBoarding() = onBoardingList.indexOf(_currentOnBoarding.value)
}