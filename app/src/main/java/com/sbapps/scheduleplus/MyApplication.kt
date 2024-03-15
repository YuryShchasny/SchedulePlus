package com.sbapps.scheduleplus

import android.app.Application
import com.sbapps.scheduleplus.di.DaggerApplicationComponent

class MyApplication : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }
}