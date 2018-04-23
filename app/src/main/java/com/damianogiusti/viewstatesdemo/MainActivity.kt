package com.damianogiusti.viewstatesdemo

import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout

class MainActivity : BaseActivity() {

    override val containerLayout: Lazy<ViewGroup> = lazy { findViewById<FrameLayout>(R.id.screenContainer) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dispatcher.dispatch(dispatcher.latest ?: MainState(getString(R.string.main_screen_title)))
    }
}
