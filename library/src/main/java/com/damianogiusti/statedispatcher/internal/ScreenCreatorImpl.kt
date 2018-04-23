package com.damianogiusti.statedispatcher.internal

import android.view.LayoutInflater
import android.view.ViewGroup
import com.damianogiusti.statedispatcher.ScreenCreator
import com.damianogiusti.statedispatcher.ScreenState
import kotlin.reflect.KClass

/**
 * Created by Damiano Giusti on 23/04/18.
 */

internal class ScreenCreatorImpl(
    override val container: Lazy<ViewGroup>,
    private val screensAndLayouts: Map<KClass<out ScreenState>, Int>
) : ScreenCreator {

    private val screenContainer: ViewGroup
        get() = container.value

    private val inflater by lazy { LayoutInflater.from(screenContainer.context) }

    private val inflatedScreens = mutableSetOf<Int>()

    override fun create(state: ScreenState): ScreenState {
        val resId = requireNotNull(screensAndLayouts[state::class]) {
            "Cannot inflate a layout for a non mapped screen!"
        }
        if (resId !in inflatedScreens) {
            inflater.inflate(resId, screenContainer, true)
            inflatedScreens.add(resId)
        }
        return state
    }
}