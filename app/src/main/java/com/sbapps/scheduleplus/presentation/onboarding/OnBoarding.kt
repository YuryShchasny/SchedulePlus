package com.sbapps.scheduleplus.presentation.onboarding

import com.sbapps.scheduleplus.R

class OnBoarding(
    val titleResId: Int,
    val descriptionResId: Int,
    val imageResId: Int
) {
    companion object {
        val nightList = listOf(
            OnBoarding(
                R.string.on_boarding_title_1,
                R.string.on_boarding_description_1,
                R.drawable.night_on_boarding_1
            ),
            OnBoarding(
                R.string.on_boarding_title_2,
                R.string.on_boarding_description_2,
                R.drawable.night_on_boarding_2
            ),
            OnBoarding(
                R.string.on_boarding_title_3,
                R.string.on_boarding_description_3,
                R.drawable.night_on_boarding_3
            ),
            OnBoarding(
                R.string.on_boarding_title_4,
                R.string.on_boarding_description_4,
                R.drawable.night_on_boarding_4
            ),
        )
        val lightList = listOf(
            OnBoarding(
                R.string.on_boarding_title_1,
                R.string.on_boarding_description_1,
                R.drawable.light_on_boarding_1
            ),
            OnBoarding(
                R.string.on_boarding_title_2,
                R.string.on_boarding_description_2,
                R.drawable.light_on_boarding_2
            ),
            OnBoarding(
                R.string.on_boarding_title_3,
                R.string.on_boarding_description_3,
                R.drawable.light_on_boarding_3
            ),
            OnBoarding(
                R.string.on_boarding_title_4,
                R.string.on_boarding_description_4,
                R.drawable.light_on_boarding_4
            ),
        )
    }
}