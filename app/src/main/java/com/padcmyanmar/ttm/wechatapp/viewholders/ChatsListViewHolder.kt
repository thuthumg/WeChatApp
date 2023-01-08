package com.padcmyanmar.ttm.wechatapp.viewholders

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.data.vos.ChatHistoryVO
import com.padcmyanmar.ttm.wechatapp.delegates.ChatsListDelegate
import kotlinx.android.synthetic.main.view_holder_chat_list.view.*

class ChatsListViewHolder(itemView: View, mDelegate: ChatsListDelegate, checkListType: String) : RecyclerView.ViewHolder(itemView){

   var mChatHistoryVO: ChatHistoryVO = ChatHistoryVO()
   init{
      itemView.cvChatItem.setOnClickListener {

         mDelegate.goToChatDetailFromChatFragmentPage(mChatHistoryVO,checkListType)
      }
   }

   fun bindData(chatHistoryVO: ChatHistoryVO) {
      Log.d("chatlist","check message = ${mChatHistoryVO.chatMsg}")
      mChatHistoryVO = chatHistoryVO
      itemView.tvChatUserName.text = mChatHistoryVO.chatUserName
       itemView.tvChatMessage.text = mChatHistoryVO.chatMsg
       itemView.tvChatTime.text = mChatHistoryVO.chatTime

      Glide.with(itemView.context)
         .load(mChatHistoryVO.chatUserProfileUrl)
         .placeholder(R.drawable.empty_image)
         .into(itemView.ivChatProfile)


    //  val sortedTimestampDescending = mChatHistoryVO.chatMsgListVO?.sortedByDescending { it.timestamp }
    //  itemView.tvChatMessage.text = sortedTimestampDescending?.firstOrNull()?.message


   }

}