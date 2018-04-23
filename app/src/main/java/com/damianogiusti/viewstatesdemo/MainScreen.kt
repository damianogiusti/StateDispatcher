package com.damianogiusti.viewstatesdemo

import android.content.Context
import android.util.AttributeSet
import com.damianogiusti.statedispatcher.ScreenState
import kotlinx.android.synthetic.main.screen_main.view.*
import kotlin.reflect.KClass

/**
 * Created by Damiano Giusti on 20/04/18.
 */
class MainScreen @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseScreen(context, attrs, defStyleAttr) {

    override val screenType: KClass<out ScreenState> = MainState::class

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        hitMeButton.setOnClickListener {
            dispatcher.dispatch(DetailState)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }
}