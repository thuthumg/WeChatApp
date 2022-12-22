package com.padcmyanmar.ttm.wechatapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.viewholders.MomentsListViewHolder

class MomentsListAdapter : RecyclerView.Adapter<MomentsListViewHolder>() {

   // private var mData: List<MomentsVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentsListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_moment_item,parent,false)

        return MomentsListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MomentsListViewHolder, position: Int) {
      // if(mData.isNotEmpty()){
          // holder.bindData(mData[position])
      // }
    }

    override fun getItemCount(): Int {
        return 10//mData.count()
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun setNewData(data: List<MomentsVO>){
//        mData = data
//        notifyDataSetChanged()
//    }
}
