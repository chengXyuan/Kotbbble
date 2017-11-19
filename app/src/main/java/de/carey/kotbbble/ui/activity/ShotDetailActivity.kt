package de.carey.kotbbble.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
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
import de.carey.kotbbble.widget.CustomLoadMoreView
import de.carey.kotbbble.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_shot_detail.*

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

    private fun initRecyclerView() {
        mAdapter = CommentAdapter()
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.addItemDecoration(DividerItemDecoration(this,
                DividerItemDecoration.LIST_VERTICAL, 1))
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        mAdapter.setOnLoadMoreListener(this, recycler_view)
        //mAdapter.addHeaderView(shot_header)
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
