package de.carey.kotbbble.base

import com.trello.rxlifecycle2.LifecycleTransformer

interface BaseView {

    fun <T> bindLifecycle(): LifecycleTransformer<T>
}