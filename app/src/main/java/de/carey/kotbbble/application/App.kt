package de.carey.kotbbble.application

import android.app.Application
import android.os.Environment
import com.facebook.drawee.backends.pipeline.Fresco
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import de.carey.kotbbble.BuildConfig
import de.carey.kotbbble.R
import de.carey.kotbbble.ui.activity.MainActivity
import de.carey.kotbbble.util.delegate.NotNullSingleValueVar


class App : Application() {

    companion object {
        var instance: App by NotNullSingleValueVar.DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        //只在主进程里进行初始化,避免重复初始化
        //if (Utils.isMainProcess(this)) {
        //Fresco
        Thread { Fresco.initialize(this) }.start()
        //Bugly
        initBugly()
        //Logger
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?) = BuildConfig.DEBUG
        })
        //}
    }

    private fun initBugly() {
        Beta.initDelay = 3000
        Beta.largeIconId = R.mipmap.ic_launcher
        Beta.canShowUpgradeActs.add(MainActivity::class.java)
        Beta.smallIconId = R.drawable.ic_update_black_24dp
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        Bugly.init(applicationContext, Constants.BUGLY_ID, true)
    }
}