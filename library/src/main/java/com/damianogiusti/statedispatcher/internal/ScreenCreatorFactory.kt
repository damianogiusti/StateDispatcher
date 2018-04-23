package com.damianogiusti.statedispatcher.internal

import android.view.ViewGroup
import com.damianogiusti.statedispatcher.ScreenCreator
import com.damianogiusti.statedispatcher.ScreenState
import kotlin.reflect.KClass

/**
 * Created by Damiano Giusti on 23/04/18.
 */
internal interface ScreenCreatorFactory {

    fun newScreenCreator(
        container: Lazy<ViewGroup>,
        screensAndLayouts: Map<KClass<out ScreenState>, Int>
    ): ScreenCreator

    companion object {
        fun newInstance(): ScreenCreatorFactory = ScreenCreatorFactoryImpl()
    }
}

private class ScreenCreatorFactoryImpl : ScreenCreatorFactory {
    override fun newScreenCreator(
        container: Lazy<ViewGroup>,
        screensAndLayouts: Map<KClass<out ScreenState>, Int>
    ): ScreenCreator {
        return ScreenCreatorImpl(container, screensAndLayouts)
    }
}

