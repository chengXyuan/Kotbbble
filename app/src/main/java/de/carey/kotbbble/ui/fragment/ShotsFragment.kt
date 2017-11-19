package de.carey.kotbbble.ui.fragment


import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import de.carey.kotbbble.R
import de.carey.kotbbble.app.Constants
import de.carey.kotbbble.base.BaseMVPFragment
import de.carey.kotbbble.entity.Shot
import de.carey.kotbbble.ui.activity.ShotDetailActivity
import de.carey.kotbbble.ui.adapter.ShotsAdapter
import de.carey.kotbbble.ui.iview.IShotView
import de.carey.kotbbble.ui.presenter.ShotsPresenter
import de.carey.kotbbble.widget.CustomLoadMoreView
import kotlinx.android.synthetic.main.fragment_shots.*
import org.jetbrains.anko.startActivity


class ShotsFragment : BaseMVPFragment<IShotView, ShotsPresenter>(), IShotView, BaseQuickAdapter.RequestLoadMoreListener {

    private var pageIndex = 1
    private val shotsAdapter = ShotsAdapter()

    override fun getLayoutResId() = R.layout.fragment_shots

    override fun initData(savedInstanceState: Bundle?) {
        swipe_refresh_layout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))
        swipe_refresh_layout.setOnRefreshListener { onRefresh() }
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = GridLayoutManager(context, 2)
        shotsAdapter.setEmptyView(R.layout.layout_loading, recycler_view.parent as ViewGroup)
        shotsAdapter.setLoadMoreView(CustomLoadMoreView())
        shotsAdapter.setOnLoadMoreListener(this, recycler_view)
        shotsAdapter.setOnItemClickListener { _, _, position ->
            val shot = shotsAdapter.data[position]
            context.startActivity<ShotDetailActivity>(ShotDetailActivity.EXTRA_SHOT_ENTITY to shot)
        }
        recycler_view.adapter = shotsAdapter

        getShots()
    }

    private fun onRefresh() {
        pageIndex = 1
        getShots()
    }

    override fun onLoadMoreRequested() {
        pageIndex++
        getShots()
    }

    private fun getShots() {
        mPresenter.getShots(pageIndex = pageIndex)
    }

    override fun showShots(data: List<Shot>) {
        if (pageIndex == 1) {
            shotsAdapter.setNewData(data)
            shotsAdapter.disableLoadMoreIfNotFullPage()
        } else {
            shotsAdapter.addData(data)
        }
        if (data.size < Constants.PAGE_SIZE) {
            shotsAdapter.loadMoreEnd()
        } else {
            shotsAdapter.loadMoreComplete()
        }
    }

    override fun onComplete() {
        if (swipe_refresh_layout.isRefreshing) {
            swipe_refresh_layout.isRefreshing = false
        }
    }
}
