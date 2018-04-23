package com.damianogiusti.statedispatcher

import android.view.ViewGroup

/**
 * Created by Damiano Giusti on 23/04/18.
 */
interface ScreenContainer {

    val containerLayout: Lazy<ViewGroup>
}