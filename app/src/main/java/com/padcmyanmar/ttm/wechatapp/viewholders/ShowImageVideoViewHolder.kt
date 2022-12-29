package com.padcmyanmar.ttm.wechatapp.viewholders

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.view.View
import android.webkit.MimeTypeMap
import com.bumptech.glide.Glide
import com.padcmyanmar.ttm.wechatapp.delegates.MediaTypeDataDelegate
import com.padcmyanmar.ttm.wechatapp.utils.getFileExtensionFunc
import kotlinx.android.synthetic.main.view_holder_media_item_layout.view.*
import java.io.File


class ShowImageVideoViewHolder(itemView: View, delegate: MediaTypeDataDelegate) :
    BaseViewHolder<Uri>(itemView, delegate) {


    init {

    }
    override fun bindData(data: Uri?) {

        if(getFileExtensionFunc(itemView.context,data) == "jpg" ||
            getFileExtensionFunc(itemView.context,data) == "png" ||
            getFileExtensionFunc(itemView.context,data) == "jpeg")
        {
           // itemView.ivSelectedImage.visibility = View.VISIBLE
            itemView.flSelectedVideo.visibility = View.GONE
            Glide.with(itemView.context)
                .load(data)
                .into(itemView.ivSelectedImage)
        }
        else{

            itemView.flSelectedVideo.visibility = View.VISIBLE

            Glide.with(itemView.context)
                .load(data)
                .into(itemView.ivSelectedImage)
        }



    }




}