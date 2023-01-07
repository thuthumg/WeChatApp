package com.padcmyanmar.ttm.wechatapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.viewholders.ActiveChatsListViewHolder

class ActiveChatsListAdapter : RecyclerView.Adapter<ActiveChatsListViewHolder>() {

    private var mData: List<UserVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveChatsListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_active_chat_list,parent,false)

        return ActiveChatsListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActiveChatsListViewHolder, position: Int) {
       if(mData.isNotEmpty()){
           holder.bindData(mData[position])
       }
    }

    override fun getItemCount(): Int {
        return mData.count()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data: List<UserVO>){
        mData = data
        notifyDataSetChanged()
    }
}