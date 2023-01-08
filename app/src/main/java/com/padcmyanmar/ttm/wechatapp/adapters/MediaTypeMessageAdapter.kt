package com.padcmyanmar.ttm.wechatapp.adapters

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.viewholders.MediaTypeMessageViewHolder

class MediaTypeMessageAdapter : RecyclerView.Adapter<MediaTypeMessageViewHolder>() {

    private var mData: ArrayList<Uri> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  MediaTypeMessageViewHolder{

        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_media_type_message_layout, parent, false)
            return MediaTypeMessageViewHolder(view)



    }

    override fun onBindViewHolder(holder:MediaTypeMessageViewHolder, position: Int) {
        holder.bindData(mData[position])


    }

    override fun getItemCount(): Int {
        return mData.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data:ArrayList<Uri>){
        mData = data
        notifyDataSetChanged()
    }


}