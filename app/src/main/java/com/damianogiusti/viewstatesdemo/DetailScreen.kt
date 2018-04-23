package com.damianogiusti.viewstatesdemo

import android.content.Context
import android.util.AttributeSet
import com.damianogiusti.statedispatcher.ScreenState
import kotlin.reflect.KClass

/**
 * Created by Damiano Giusti on 20/04/18.
 */
class DetailScreen @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseScreen(context, attrs, defStyleAttr) {

    override val screenType: KClass<out ScreenState> = DetailState::class

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }
}