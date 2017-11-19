package de.carey.kotbbble.ui.iview

import de.carey.kotbbble.base.BaseView
import de.carey.kotbbble.entity.Shot

interface IShotView : BaseView {
    fun showShots(data: List<Shot>)

    fun onComplete()
}