package de.carey.kotbbble.ui.iview

import de.carey.kotbbble.base.BaseView
import de.carey.kotbbble.entity.User

interface IMainView : BaseView {
    fun refreshUser(user: User?)
}