package de.carey.kotbbble.ui.iview

import de.carey.kotbbble.base.BaseView
import de.carey.kotbbble.entity.Comment

interface IShotDetailView : BaseView {

    fun showComments(comments: List<Comment>)
}