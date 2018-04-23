package com.damianogiusti.statedispatcher

import android.app.Application
import com.damianogiusti.statedispatcher.internal.ScreenCreatorFactory
import com.damianogiusti.statedispatcher.internal.StateDispatcherImpl
import io.reactivex.Flowable
import kotlin.reflect.KClass

/**
 * Created by Damiano Giusti on 20/04/18.
 */
interface StateDispatcher {

    val latest: NavigationState?

    fun dispatch(state: NavigationState)

    fun goBack()

    fun showEvents(screenType: KClass<out ScreenState>): Flowable<NavigationState.Show>

    fun hideEvents(screenType: KClass<out ScreenState>): Flowable<NavigationState.Hide>

    fun exitEvents(): Flowable<NavigationState.Exit>
}

class StateDispatcherBuilder(
    private val application: Application
) {
    private var screensAndLayouts = mutableMapOf<KClass<out ScreenState>, Int>()

    fun addScreen(type: KClass<out ScreenState>, layoutResId: Int): StateDispatcherBuilder {
        screensAndLayouts[type] = layoutResId
        return this
    }

    fun build(): StateDispatcher {
        return StateDispatcherImpl(
            application = application,
            screensAndLayouts = screensAndLayouts,
            screenCreatorFactory = ScreenCreatorFactory.newInstance()
        )
    }
}

