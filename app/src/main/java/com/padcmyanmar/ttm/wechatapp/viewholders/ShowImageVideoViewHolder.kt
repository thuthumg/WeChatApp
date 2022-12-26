package com.padcmyanmar.ttm.wechatapp.viewholders

import android.net.Uri
import android.view.View
import com.bumptech.glide.Glide
import com.padcmyanmar.ttm.wechatapp.delegates.MediaTypeDataDelegate
import kotlinx.android.synthetic.main.view_holder_media_item_layout.view.*

class ShowImageVideoViewHolder(itemView: View, delegate: MediaTypeDataDelegate) :
    BaseViewHolder<Uri>(itemView, delegate) {

    init {
    }

    override fun bindData(data: Uri?) {
        Glide.with(itemView.context)
            .load(data)
            .into(itemView.ivSelectedImage)

    }

}