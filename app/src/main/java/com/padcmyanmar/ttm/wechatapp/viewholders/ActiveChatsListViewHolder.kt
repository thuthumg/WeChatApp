package com.padcmyanmar.ttm.wechatapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import kotlinx.android.synthetic.main.view_holder_active_chat_list.view.*

class ActiveChatsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    init{
   }

    fun bindData(userVO: UserVO) {
        itemView.tvActiveProfileName.text = userVO.name
    }

}