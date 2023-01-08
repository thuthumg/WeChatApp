package com.padcmyanmar.ttm.wechatapp.viewholders

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide

import com.padcmyanmar.ttm.wechatapp.utils.getFileExtensionFunc
import kotlinx.android.synthetic.main.view_holder_media_type_message_layout.view.*


class MediaTypeMessageViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){


    init {

    }
    fun bindData(data: Uri?) {

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