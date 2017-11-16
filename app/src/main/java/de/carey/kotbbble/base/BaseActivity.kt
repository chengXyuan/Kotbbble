package de.carey.kotbbble.base

import android.os.Bundle
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.greenrobot.eventbus.EventBus


abstract class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        initStatusBar()
        initPresenter()
        initData(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        if (useEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onStop() {
        super.onStop()
        if (useEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    open fun initStatusBar() {}

    open fun initPresenter() {}

    open fun useEventBus() = false

    fun <T> bindLifecycle(): LifecycleTransformer<T> {
        return bindUntilEvent(ActivityEvent.DESTROY)
    }

    protected abstract fun getLayoutResId(): Int

    protected abstract fun initData(savedInstanceState: Bundle?)
}