package com.padcmyanmar.ttm.wechatapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.delegates.SelectedContactListItemDelegate
import com.padcmyanmar.ttm.wechatapp.viewholders.SelectedChatItemViewHolder

class SelectedChatItemListAdapter(var mDelegate: SelectedContactListItemDelegate) : RecyclerView.Adapter<SelectedChatItemViewHolder>() {

    private var mData: List<UserVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedChatItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_chat_item,parent,false)

        return SelectedChatItemViewHolder(view,mDelegate)
    }

    override fun onBindViewHolder(holder: SelectedChatItemViewHolder, position: Int) {
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