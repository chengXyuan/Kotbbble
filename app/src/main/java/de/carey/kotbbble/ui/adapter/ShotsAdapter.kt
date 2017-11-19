package de.carey.kotbbble.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import de.carey.kotbbble.R
import de.carey.kotbbble.entity.Shot
import de.carey.kotbbble.util.ImageLoader

class ShotsAdapter : BaseQuickAdapter<Shot, BaseViewHolder>(R.layout.item_shots) {
    override fun convert(helper: BaseViewHolder, item: Shot) {
        val shotView = helper.getView<SimpleDraweeView>(R.id.iv_item_shot_preview)
        ImageLoader.loadImage(shotView, item.images.normal, item.images.teaser, item.animated)
    }
}