package com.padcmyanmar.ttm.wechatapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.delegates.MediaTypeDataDelegate
import com.padcmyanmar.ttm.wechatapp.utils.VIEW_TYPE_ADD_MEDIA
import kotlinx.android.synthetic.main.view_holder_media_item_layout.view.*
abstract class BaseViewHolder<W>(itemView: View, delegate: MediaTypeDataDelegate) : RecyclerView.ViewHolder(itemView) {

    protected var mData: W? = null
    abstract fun bindData(data: W?)

}