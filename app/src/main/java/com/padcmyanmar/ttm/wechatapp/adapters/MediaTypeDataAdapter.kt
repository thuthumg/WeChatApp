package com.padcmyanmar.ttm.wechatapp.adapters

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.delegates.MediaTypeDataDelegate
import com.padcmyanmar.ttm.wechatapp.utils.VIEW_TYPE_ADD_MEDIA
import com.padcmyanmar.ttm.wechatapp.utils.VIEW_TYPE_IMAGE
import com.padcmyanmar.ttm.wechatapp.viewholders.AddImageVideoViewHolder
import com.padcmyanmar.ttm.wechatapp.viewholders.BaseViewHolder
import com.padcmyanmar.ttm.wechatapp.viewholders.ShowImageVideoViewHolder

class MediaTypeDataAdapter(private val mDelegate:MediaTypeDataDelegate) : RecyclerView.Adapter<BaseViewHolder<Uri>>() {

    private var mData: ArrayList<Uri> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Uri> {

        when(viewType)
        {
            VIEW_TYPE_ADD_MEDIA ->{
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.view_holder_media_choose_layout, parent, false)
                return AddImageVideoViewHolder(view,mDelegate)
            }
            VIEW_TYPE_IMAGE->{
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.view_holder_media_item_layout, parent, false)
                return ShowImageVideoViewHolder(view,mDelegate)
            }
            else->{
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.view_holder_media_item_layout, parent, false)
                return ShowImageVideoViewHolder(view,mDelegate)
            }
        }

    }

    override fun onBindViewHolder(holder: BaseViewHolder<Uri>, position: Int) {

        when(holder.itemViewType)
        {
            VIEW_TYPE_ADD_MEDIA->{

               var  addImageVideo:AddImageVideoViewHolder = holder as AddImageVideoViewHolder
                addImageVideo.bindData(null)

            }
            VIEW_TYPE_IMAGE ->{
                var imageViewHolder :ShowImageVideoViewHolder = holder as ShowImageVideoViewHolder
                    imageViewHolder.bindData(mData[position])
            }
        }

    }

    override fun getItemCount(): Int {


        return if(mData.isEmpty()) {
            1
        }else{
            mData.count()+1
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data:ArrayList<Uri>){
        mData = data
        notifyDataSetChanged()
    }

    
    override fun getItemViewType(position: Int): Int {

        return when (position) {
            itemCount - 1 -> {
                VIEW_TYPE_ADD_MEDIA
            }
            else -> {
                VIEW_TYPE_IMAGE
            }

        }
    }
}