package com.sbapps.scheduleplus.presentation.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sbapps.scheduleplus.domain.entity.OnBoarding

class OnBoardingViewModel : ViewModel() {

    private var onBoardingList = listOf<OnBoarding>()
    private var _currentOnBoarding = MutableLiveData<OnBoarding>()
    val currentOnBoarding: LiveData<OnBoarding> = _currentOnBoarding

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