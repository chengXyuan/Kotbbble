package de.carey.kotbbble.ui.activity

import android.os.Bundle
import de.carey.kotbbble.R
import de.carey.kotbbble.base.BaseMVPActivity
import de.carey.kotbbble.entity.User
import de.carey.kotbbble.ui.iview.IMainView
import de.carey.kotbbble.ui.presenter.MainPresenter

class MainActivity : BaseMVPActivity<MainPresenter>(), IMainView {

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun refreshUser(user: User) {

    }
}
