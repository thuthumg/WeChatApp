package com.padcmyanmar.ttm.wechatapp.viewholders

import android.net.Uri
import android.view.View
import com.bumptech.glide.Glide
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.delegates.MediaTypeDataDelegate
import kotlinx.android.synthetic.main.view_holder_media_item_layout.view.*

class AddImageVideoViewHolder(itemView: View, delegate: MediaTypeDataDelegate) :
    BaseViewHolder<Uri>(itemView, delegate) {

    init {
        itemView.setOnClickListener {
            delegate.goToChooseMediaType()
        }
    }

    override fun bindData(data: Uri?) {
        Glide.with(itemView.context)
            .load(R.drawable.ic_baseline_add_24)
            .into(itemView.ivSelectedImage)
    }
}