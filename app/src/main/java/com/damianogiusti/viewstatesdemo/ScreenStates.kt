package com.damianogiusti.viewstatesdemo

import com.damianogiusti.statedispatcher.ScreenState

/**
 * Created by Damiano Giusti on 23/04/18.
 */

data class MainState(val title: String) : ScreenState

object DetailState : ScreenState