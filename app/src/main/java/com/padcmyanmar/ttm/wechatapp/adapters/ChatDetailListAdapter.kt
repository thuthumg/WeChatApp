package com.padcmyanmar.ttm.wechatapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.viewholders.ChatDetailListViewHolder

class ChatDetailListAdapter : RecyclerView.Adapter<ChatDetailListViewHolder>() {

  //  private var mData: List<> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatDetailListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_chat_detail_list,parent,false)

        return ChatDetailListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatDetailListViewHolder, position: Int) {
//       if(mData.isNotEmpty()){
//           holder.bindData(mData[position])
//       }
    }

    override fun getItemCount(): Int {
       // return mData.count()
        return 5
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun setNewData(data: List<>){
//        mData = data
//        notifyDataSetChanged()
//    }
}