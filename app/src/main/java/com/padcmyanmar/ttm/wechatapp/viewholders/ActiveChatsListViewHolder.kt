package com.padcmyanmar.ttm.wechatapp.viewholders

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import kotlinx.android.synthetic.main.view_holder_active_chat_list.view.*

class ActiveChatsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    init{
   }

    fun bindData(userVO: UserVO) {

        Log.d("activechats","userVO ${userVO.profileUrl}")


        itemView.tvActiveProfileName.text = userVO.name
        Glide.with(itemView.context)
            .load(userVO.profileUrl)
            .placeholder(R.drawable.empty_image)
            .into(itemView.ivActiveChatProfile)
    }

}