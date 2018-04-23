package com.damianogiusti.viewstatesdemo

import android.app.Application
import com.damianogiusti.statedispatcher.StateDispatcher
import com.damianogiusti.statedispatcher.StateDispatcherBuilder

/**
 * Created by Damiano Giusti on 20/04/18.
 */

class MainApplication : Application() {

    val dispatcher: StateDispatcher = StateDispatcherBuilder(this)
        .addScreen(MainState::class, R.layout.screen_main)
        .addScreen(DetailState::class, R.layout.screen_detail)
        .build()
}