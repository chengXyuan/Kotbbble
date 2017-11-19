package de.carey.kotbbble.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import de.carey.kotbbble.R
import de.carey.kotbbble.app.Constants
import de.carey.kotbbble.base.BaseMVPActivity
import de.carey.kotbbble.ui.iview.ILoginView
import de.carey.kotbbble.ui.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult


class LoginActivity : BaseMVPActivity<ILoginView, LoginPresenter>(), ILoginView {

    companion object {
        val EXTRA_AUTHORIZE_CODE = "extra_authorize_code"
        val REQUEST_CODE_AUTHORIZE = 1
    }

    override fun getLayoutResId() = R.layout.activity_login

    override fun initData(savedInstanceState: Bundle?) {
        ib_back.setOnClickListener { finish() }
        btn_login.setOnClickListener {
            startActivityForResult<AuthorizeActivity>(REQUEST_CODE_AUTHORIZE,
                    WebActivity.WEB_LOAD_URL to Constants.OAUTH_URL)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) =
            if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_AUTHORIZE) {
                val code = data?.getStringExtra(EXTRA_AUTHORIZE_CODE)
                if (!TextUtils.isEmpty(code))
                    mPresenter.getAccessToken(code!!)
                progress_bar.visibility = View.VISIBLE
                progress_bar.isIndeterminate = true
                btn_login.visibility = View.GONE
                tv_login_welcome.text = "欢迎回来"
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }

    override fun getUserInfoSuccess() {
        startActivity<MainActivity>()
    }

}
