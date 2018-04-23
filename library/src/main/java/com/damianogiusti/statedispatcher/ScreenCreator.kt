package com.damianogiusti.statedispatcher

import android.view.ViewGroup

/**
 * Created by Damiano Giusti on 20/04/18.
 */
interface ScreenCreator {

    val container: Lazy<ViewGroup>

    fun create(state: ScreenState): ScreenState
}