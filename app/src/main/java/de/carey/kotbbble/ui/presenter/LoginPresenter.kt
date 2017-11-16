package de.carey.kotbbble.ui.presenter

import de.carey.kotbbble.application.Constants
import de.carey.kotbbble.base.BasePresenter
import de.carey.kotbbble.ui.iview.ILoginView
import de.carey.kotbbble.util.SPUtils
import de.carey.kotbbble.util.UserManager


class LoginPresenter : BasePresenter<ILoginView>() {

    fun getAccessToken(code: String) {
        mNetRepository.getAccessToken(code).deal { data ->
            SPUtils.instance.putString(Constants.SP_ACCESS_TOKEN, data.access_token)
            getUserInfo()
        }
    }

    fun getUserInfo() {
        mNetRepository.getUserInfo().deal { data -> UserManager.instance.setUser(data) }
        mView.getUserInfoSuccess()
    }
}