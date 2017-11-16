package de.carey.kotbbble.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.support.RxFragment
import org.greenrobot.eventbus.EventBus


abstract class BaseFragment : RxFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(getLayoutResId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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

    open fun useEventBus() = false

    open fun initPresenter() {}

    fun <T> bindLifecycle(): LifecycleTransformer<T> {
        return bindUntilEvent(FragmentEvent.DESTROY_VIEW)
    }

    abstract fun getLayoutResId(): Int

    abstract fun initData(savedInstanceState: Bundle?)
}