package de.carey.kotbbble.ui.presenter

import de.carey.kotbbble.app.Constants
import de.carey.kotbbble.base.BasePresenter
import de.carey.kotbbble.ui.iview.IMainView
import de.carey.kotbbble.util.SPUtils
import de.carey.kotbbble.util.UserManager


class MainPresenter : BasePresenter<IMainView>() {

    fun getAccessToken(code: String) {
        mNetRepository.getAccessToken(code).deal({ data ->
            SPUtils.instance.putString(Constants.SP_ACCESS_TOKEN, data.access_token)
            getUserInfo()
        })
    }

    fun getUserInfo() {
        mNetRepository.getUserInfo().deal({ data -> UserManager.instance.setUser(data) })
    }
}