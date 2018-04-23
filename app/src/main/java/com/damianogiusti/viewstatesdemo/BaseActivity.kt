package com.damianogiusti.viewstatesdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.damianogiusti.statedispatcher.ScreenContainer

/**
 * Created by Damiano Giusti on 20/04/18.
 */
abstract class BaseActivity : AppCompatActivity(), ScreenContainer {

    val dispatcher by lazy { applicationContext.dispatcher }

    override fun getApplicationContext(): MainApplication {
        return super.getApplicationContext() as MainApplication
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dispatcher
            .exitEvents()
            .take(1)
            .subscribe({
                finish()
            })
    }

    override fun onBackPressed() {
        dispatcher.goBack()
    }
}