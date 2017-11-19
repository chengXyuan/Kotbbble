package de.carey.kotbbble.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.View
import de.carey.kotbbble.R
import de.carey.kotbbble.app.Constants
import de.carey.kotbbble.base.BaseMVPActivity
import de.carey.kotbbble.entity.User
import de.carey.kotbbble.ui.fragment.ShotsFragment
import de.carey.kotbbble.ui.iview.IMainView
import de.carey.kotbbble.ui.presenter.MainPresenter
import de.carey.kotbbble.util.ImageLoader
import de.carey.kotbbble.util.SPUtils
import de.carey.kotbbble.util.UserManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import org.jetbrains.anko.startActivity

class MainActivity : BaseMVPActivity<IMainView, MainPresenter>(), IMainView {

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initData(savedInstanceState: Bundle?) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_container, ShotsFragment()).commit()

        iv_main_menu.setOnClickListener { drawer_layout.openDrawer(Gravity.START) }
        drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {}

            override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {}

            override fun onDrawerClosed(drawerView: View?) {}

            override fun onDrawerOpened(drawerView: View?) {
                initUserInfo()
            }
        })
        navigation_view.setNavigationItemSelectedListener { item ->
            when (item.itemId) {

            }
            drawer_layout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        initUserInfo()
    }

    private fun initUserInfo() {
        val user = UserManager.instance.getUser()
        val token = SPUtils.instance.getString(Constants.SP_ACCESS_TOKEN, "")
        when {
            user == null && token.isNullOrEmpty() -> refreshUser(null)
            user == null && !token.isNullOrEmpty() -> mPresenter.getUserInfo()
            else -> refreshUser(user)
        }
    }

    override fun refreshUser(user: User?) {
        if (user == null) {
            user_name.text = "未登录!"
            sdv_avatar.setOnClickListener { startActivity<LoginActivity>() }
        } else {
            user_name.text = user.username ?: user.name
            ImageLoader.loadCircle(sdv_avatar, user.avatar_url)
            sdv_avatar.setOnClickListener { startActivity<UserActivity>() }
        }
    }
}
