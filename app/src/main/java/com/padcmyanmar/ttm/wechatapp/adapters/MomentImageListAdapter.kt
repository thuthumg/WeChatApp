package com.padcmyanmar.ttm.wechatapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.viewholders.MomentImageListViewHolder

class MomentImageListAdapter : RecyclerView.Adapter<MomentImageListViewHolder>() {

  //  private var mData: List<ImageVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentImageListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_image_item,parent,false)

        return MomentImageListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MomentImageListViewHolder, position: Int) {
//       if(mData.isNotEmpty()){
//           holder.bindData(mData[position])
//       }
    }

    override fun getItemCount(): Int {
        return 2 //mData.count()
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun setNewData(data: List<ImageVO>){
//        mData = data
//        notifyDataSetChanged()
//    }
}