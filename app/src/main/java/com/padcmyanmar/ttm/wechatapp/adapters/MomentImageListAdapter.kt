package com.padcmyanmar.ttm.wechatapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.data.vos.MediaDataVO
import com.padcmyanmar.ttm.wechatapp.viewholders.MomentImageListViewHolder
import kotlinx.android.synthetic.main.view_holder_image_item.view.*

class MomentImageListAdapter() : RecyclerView.Adapter<MomentImageListViewHolder>() {

    private var mData: ArrayList<MediaDataVO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentImageListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_image_item,parent,false)

        return MomentImageListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MomentImageListViewHolder, position: Int) {


       // if(paramPosition == 0)
//        if(mData.count() == 1)
//        {
//
//            val layoutParams: ViewGroup.LayoutParams = holder.itemView.mcvImage.layoutParams
//            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
//            holder.itemView.mcvImage.layoutParams = layoutParams
//        }else{
//            val layoutParams: ViewGroup.LayoutParams = holder.itemView.mcvImage.layoutParams
//            layoutParams.width = 300
//            holder.itemView.mcvImage.layoutParams = layoutParams
//        }

       if(mData.isNotEmpty()){
           holder.bindData(mData[position],mData.count())
       }
    }

    override fun getItemCount(): Int {
        return mData.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data:ArrayList<MediaDataVO>){
        mData = data
        notifyDataSetChanged()
    }
}