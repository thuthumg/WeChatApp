package com.padcmyanmar.ttm.wechatapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.delegates.ChatsListDelegate
import kotlinx.android.synthetic.main.view_holder_chat_list.view.*

class ChatsListViewHolder(itemView: View, mDelegate: ChatsListDelegate) : RecyclerView.ViewHolder(itemView){

   init{
      itemView.cvChatItem.setOnClickListener {
         mDelegate.goToChatDetail()
      }
   }

//    fun bindData(data:){
//
//    }
}