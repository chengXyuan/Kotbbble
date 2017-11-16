package de.carey.kotbbble.util

import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RxSchedulers {

    companion object {

        fun <T> threadModeFlowable(subscribeThread: Scheduler = Schedulers.io(),
                                   unSubscribeThread: Scheduler = Schedulers.io(),
                                   observeThread: Scheduler = AndroidSchedulers.mainThread())
                : FlowableTransformer<T, T> {
            return FlowableTransformer { upstream ->
                upstream.subscribeOn(subscribeThread)
                        .unsubscribeOn(unSubscribeThread)
                        .observeOn(observeThread)
            }
        }

        fun <T> threadModeObservable(subscribeThread: Scheduler = Schedulers.io(),
                                     unSubscribeThread: Scheduler = Schedulers.io(),
                                     observeThread: Scheduler = AndroidSchedulers.mainThread())
                : ObservableTransformer<T, T> {
            return ObservableTransformer { upstream ->
                upstream.subscribeOn(subscribeThread)
                        .unsubscribeOn(unSubscribeThread)
                        .observeOn(observeThread)
            }
        }
    }
}