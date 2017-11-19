package de.carey.kotbbble.widget

import com.chad.library.adapter.base.loadmore.LoadMoreView
import de.carey.kotbbble.R

class CustomLoadMoreView : LoadMoreView() {

    override fun getLayoutId() = R.layout.layout_load_more

    override fun getLoadingViewId() = R.id.lay_load_more_loading_view

    override fun getLoadEndViewId() = R.id.lay_load_more_load_fail_view

    override fun getLoadFailViewId() = R.id.tv_load_more_no_more
}