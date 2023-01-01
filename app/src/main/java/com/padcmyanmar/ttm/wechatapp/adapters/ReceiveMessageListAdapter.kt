package com.padcmyanmar.ttm.wechatapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.viewholders.ReceiveMessageListViewHolder

class ReceiveMessageListAdapter : RecyclerView.Adapter<ReceiveMessageListViewHolder>() {

    //private var mData: List<> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiveMessageListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_receiver_msg_view,parent,false)

        return ReceiveMessageListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReceiveMessageListViewHolder, position: Int) {
//       if(mData.isNotEmpty()){
//           holder.bindData(mData[position])
//       }
    }

    override fun getItemCount(): Int {
       // return mData.count()
        return 4
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun setNewData(data: List<>){
//        mData = data
//        notifyDataSetChanged()
//    }
}