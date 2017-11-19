package de.carey.kotbbble.ui.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import de.carey.kotbbble.R
import de.carey.kotbbble.entity.Comment
import de.carey.kotbbble.util.ImageLoader
import java.text.DateFormat

class CommentAdapter : BaseQuickAdapter<Comment, BaseViewHolder>(R.layout.item_shot_comment) {

    override fun convert(helper: BaseViewHolder, item: Comment) {
        val ivAvatar = helper.getView<SimpleDraweeView>(R.id.iv_avatar)
        ImageLoader.loadCircle(ivAvatar, item.user.avatar_url)
        helper.setText(R.id.tv_user_name, item.user.name)
        helper.setText(R.id.tv_comment_body, Html.fromHtml(item.body))
        helper.setText(R.id.tv_comment_time, DateFormat.getDateTimeInstance().format(item.created_at))
        helper.setText(R.id.tv_comment_like_count, item.likes_count.toString())
    }
}