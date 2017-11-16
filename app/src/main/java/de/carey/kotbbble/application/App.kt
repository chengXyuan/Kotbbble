package de.carey.kotbbble.application

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.tencent.bugly.Bugly
import de.carey.kotbbble.util.delegate.NotNullSingleValueVar


class App : Application() {

    companion object {
        var instance: App by NotNullSingleValueVar.DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Thread { Fresco.initialize(this) }.start()
        initBugly()
    }

    private fun initBugly() {
        Bugly.init(applicationContext, "", true)
    }
}