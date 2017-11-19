package de.carey.kotbbble.ui.presenter

import de.carey.kotbbble.base.BasePresenter
import de.carey.kotbbble.ui.iview.IShotDetailView

class ShotDetailPresenter : BasePresenter<IShotDetailView>() {

    fun getComments(shotId: Int, sort: String?, pageIndex: Int) {
        mNetRepository.getComments(shotId, sort, pageIndex)
                .deal({})
    }
}