package de.carey.kotbbble.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import de.carey.kotbbble.R
import de.carey.kotbbble.app.Constants
import de.carey.kotbbble.base.BaseMVPActivity
import de.carey.kotbbble.entity.Comment
import de.carey.kotbbble.entity.Shot
import de.carey.kotbbble.ui.adapter.CommentAdapter
import de.carey.kotbbble.ui.iview.IShotDetailView
import de.carey.kotbbble.ui.presenter.ShotDetailPresenter
import de.carey.kotbbble.util.ImageLoader
import de.carey.kotbbble.util.htmlToStringNoP
import de.carey.kotbbble.widget.CustomLoadMoreView
import de.carey.kotbbble.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_shot_detail.*
import kotlinx.android.synthetic.main.layout_shot_header.view.*
import java.text.DateFormat

class ShotDetailActivity : BaseMVPActivity<IShotDetailView, ShotDetailPresenter>(), IShotDetailView, BaseQuickAdapter.RequestLoadMoreListener {

    companion object {
        val EXTRA_SHOT_ENTITY = "extra_shot_entity"
    }

    private lateinit var mShot: Shot
    private lateinit var mAdapter: CommentAdapter
    private var pageIndex = 1
    private var sort: String? = null

    override fun getLayoutResId() = R.layout.activity_shot_detail

    override fun initData(savedInstanceState: Bundle?) {
        mShot = intent.getSerializableExtra(EXTRA_SHOT_ENTITY) as Shot
        tool_bar.title = mShot.title
        tool_bar.setNavigationOnClickListener { onBackPressed() }
        ImageLoader.loadImage(iv_shot_view, mShot.images.hidpi ?: mShot.images.normal,
                mShot.images.teaser, mShot.animated)

        initRecyclerView()
        getComments()
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    private fun initRecyclerView() {
        mAdapter = CommentAdapter()
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.addItemDecoration(DividerItemDecoration(this,
                DividerItemDecoration.LIST_VERTICAL, 1))
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        mAdapter.setOnLoadMoreListener(this, recycler_view)
        val inflate = layoutInflater.inflate(R.layout.layout_shot_header, null, false)
        mAdapter.addHeaderView(inflate)
        with(mShot) {
            inflate.view_count.text = views_count.toString()
            inflate.like_count.text = likes_count.toString()
            inflate.bucket_count.text = buckets_count.toString()
            inflate.tv_attachment_count.text = attachments_count.toString()
            ImageLoader.loadCircle(inflate.iv_avatar, user.avatar_url)
            inflate.tv_user_name.text = user.username
            team?.let {
                inflate.tv_for.visibility = View.VISIBLE
                inflate.tv_team_name.text = team.username
            }
            user.location?.let {
                inflate.tv_in.visibility = View.VISIBLE
                inflate.tv_location.visibility = View.VISIBLE
                inflate.tv_location.text = user.location
            }
            inflate.tv_create_time.text = DateFormat.getDateInstance().format(user.created_at)
            description?.let {
                inflate.tv_description.visibility = View.VISIBLE
                inflate.divider3.visibility = View.VISIBLE
                description.htmlToStringNoP(inflate.tv_description)
            }
            inflate.tv_comment_count.text = "$comments_count Responses"
        }

        recycler_view.adapter = mAdapter
    }

    override fun onLoadMoreRequested() {
        pageIndex++
        getComments()
    }

    private fun getComments() {
        mPresenter.getComments(mShot.id, sort, pageIndex)
    }

    override fun showComments(comments: List<Comment>) {
        if (pageIndex == 1) {
            mAdapter.setNewData(comments)
            mAdapter.disableLoadMoreIfNotFullPage()
        } else {
            mAdapter.addData(comments)
        }
        if (comments.size < Constants.PAGE_SIZE) {
            mAdapter.loadMoreEnd()
        } else {
            mAdapter.loadMoreComplete()
        }
    }
}
