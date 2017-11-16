package de.carey.kotbbble.ui.activity

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.view.Gravity
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.orhanobut.logger.Logger
import de.carey.kotbbble.R
import de.carey.kotbbble.base.BaseMVPActivity
import de.carey.kotbbble.entity.User
import de.carey.kotbbble.ui.iview.IMainView
import de.carey.kotbbble.ui.presenter.MainPresenter
import de.carey.kotbbble.util.ImageLoader
import de.carey.kotbbble.util.UserManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : BaseMVPActivity<IMainView, MainPresenter>(), IMainView {

    private lateinit var sdvAvatar: SimpleDraweeView
    private lateinit var tvUsername: TextView

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initData(savedInstanceState: Bundle?) {
        Logger.d("initData")
        initDrawer()
    }

    private fun initDrawer() {
        iv_main_menu.setOnClickListener { drawer_layout.openDrawer(Gravity.START) }
        navigation_view.setNavigationItemSelectedListener { item ->
            when (item.itemId) {

            }
            drawer_layout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }
        val headerView = navigation_view.getHeaderView(0)
        sdvAvatar = headerView.findViewById<SimpleDraweeView>(R.id.sdv_avatar)
        tvUsername = headerView.findViewById<TextView>(R.id.user_name)
        refreshUser(UserManager.instance.getUser())
    }

    override fun refreshUser(user: User?) {
        if (user == null) {
            sdvAvatar.setOnClickListener { startActivity<LoginActivity>() }
            tvUsername.text = "未登录!"
        } else {
            sdvAvatar.setOnClickListener { startActivity<UserActivity>() }
            ImageLoader.loadCircle(sdvAvatar, user.avatar_url)
            tvUsername.text = user.username ?: user.name
        }
    }
}
