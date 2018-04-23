package com.damianogiusti.statedispatcher

/**
 * Created by Damiano Giusti on 20/04/18.
 */

interface NavigationState {
    object Exit : NavigationState
    object GoBack : NavigationState
    data class Show(val screen: ScreenState) : NavigationState
    data class Hide(val screen: ScreenState) : NavigationState
}

interface ScreenState: NavigationState

