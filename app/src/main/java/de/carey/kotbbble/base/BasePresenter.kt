package de.carey.kotbbble.base

import com.orhanobut.logger.Logger
import de.carey.kotbbble.api.NetRepository
import de.carey.kotbbble.util.RxSchedulers
import io.reactivex.Flowable
import org.greenrobot.eventbus.EventBus


abstract class BasePresenter<V : BaseView> {

    open lateinit var mView: V
    open lateinit var mNetRepository: NetRepository

    fun setView(view: V) {
        mView = view
        mNetRepository = NetRepository.instance
        onAttached()
    }

    open fun onAttached() {
        if (useEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    open fun onDetached() {
        if (useEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    open fun useEventBus() = false

    protected inline fun <T> Flowable<T>.deal(crossinline success: (T) -> Unit) {
        deal(success, { errorCode, msg -> Logger.d("code=$errorCode, msg: $msg") })
    }

    protected inline fun <T> Flowable<T>.deal(crossinline success: (T) -> Unit,
                                              crossinline failure: (code: Int, msg: String) -> Unit) {
        this.compose(mView.bindLifecycle())
                .compose(RxSchedulers.threadModeFlowable())
                .subscribe(object : NetSubscriber<T>() {
                    override fun onSuccess(data: T) {
                        success(data)
                    }

                    override fun onFailure(code: Int, msg: String) {
                        failure(code, msg)
                    }
                })
    }

}