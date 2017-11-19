package de.carey.kotbbble.ui.presenter

import de.carey.kotbbble.base.BasePresenter
import de.carey.kotbbble.ui.iview.IShotView

class ShotsPresenter : BasePresenter<IShotView>() {

    fun getShots(type: String = "", sort: String = "", time: String = "", pageIndex: Int) {
        mNetRepository.getShots(type, sort, time, pageIndex)
                .deal({ data -> mView.showShots(data) }, complete = { mView.onComplete() })
    }
}