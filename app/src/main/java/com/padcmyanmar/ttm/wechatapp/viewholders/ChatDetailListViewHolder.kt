package com.padcmyanmar.ttm.wechatapp.viewholders

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.adapters.ChatDetailListAdapter
import com.padcmyanmar.ttm.wechatapp.adapters.ReceiveMessageListAdapter
import com.padcmyanmar.ttm.wechatapp.adapters.SendMessageListAdapter
import kotlinx.android.synthetic.main.content_chat_detail_layout.*
import kotlinx.android.synthetic.main.view_holder_chat_detail_list.view.*

class ChatDetailListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
   lateinit var mSendMessageListAdapter: SendMessageListAdapter
   lateinit var mReceiveMessageListAdapter: ReceiveMessageListAdapter

   init{
      setUpSendMessageAdapter()
      setUpReceiveMessageAdapter()
   }
//
//    fun bindData(data:){
//
//    }

   private fun setUpSendMessageAdapter() {
      mSendMessageListAdapter =
         SendMessageListAdapter()
      itemView.rvSendMsgList.adapter = mSendMessageListAdapter
      itemView.rvSendMsgList.layoutManager = LinearLayoutManager(
         itemView.context,
         LinearLayoutManager.VERTICAL, false
      )
      itemView.rvSendMsgList.isNestedScrollingEnabled = false
   }

   private fun setUpReceiveMessageAdapter() {
      mReceiveMessageListAdapter =
         ReceiveMessageListAdapter()
      itemView.rvReceiveMsgList.adapter = mReceiveMessageListAdapter
      itemView.rvReceiveMsgList.layoutManager = LinearLayoutManager(
         itemView.context,
         LinearLayoutManager.VERTICAL, false
      )
      itemView.rvReceiveMsgList.isNestedScrollingEnabled = false
   }
}