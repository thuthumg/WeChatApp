package com.padcmyanmar.ttm.wechatapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.padcmyanmar.ttm.wechatapp.data.vos.ChatHistoryVO
import com.padcmyanmar.ttm.wechatapp.delegates.ChatsListDelegate
import kotlinx.android.synthetic.main.view_holder_chat_list.view.*

class ChatsListViewHolder(itemView: View, mDelegate: ChatsListDelegate) : RecyclerView.ViewHolder(itemView){

   var mChatHistoryVO: ChatHistoryVO = ChatHistoryVO()
   init{
      itemView.cvChatItem.setOnClickListener {
         mDelegate.goToChatDetailFromChatFragmentPage(mChatHistoryVO)
      }
   }

   fun bindData(chatHistoryVO: ChatHistoryVO) {
      mChatHistoryVO = chatHistoryVO
      itemView.tvChatUserName.text = mChatHistoryVO.chatUserName
       itemView.tvChatMessage.text = mChatHistoryVO.chatMsg
       itemView.tvChatTime.text = mChatHistoryVO.chatTime


    //  val sortedTimestampDescending = mChatHistoryVO.chatMsgListVO?.sortedByDescending { it.timestamp }
    //  itemView.tvChatMessage.text = sortedTimestampDescending?.firstOrNull()?.message


   }

}