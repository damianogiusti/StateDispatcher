package com.damianogiusti.viewstatesdemo

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.damianogiusti.statedispatcher.ScreenState
import com.damianogiusti.statedispatcher.StateDispatcher
import io.reactivex.disposables.CompositeDisposable
import kotlin.reflect.KClass

/**
 * Created by Damiano Giusti on 20/04/18.
 */
abstract class BaseScreen @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val dispatcher: StateDispatcher
        get() = (context as BaseActivity).dispatcher

    abstract val screenType: KClass<out ScreenState>

    private val compositeDisposable = CompositeDisposable()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        compositeDisposable.add(dispatcher
            .showEvents(screenType)
            .subscribe({
                visibility = View.VISIBLE
            }))
        compositeDisposable.add(dispatcher
            .hideEvents(screenType)
            .subscribe({
                visibility = View.GONE
            }))
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        compositeDisposable.clear()
    }
}