package com.padcmyanmar.ttm.wechatapp.viewholders

import android.util.Log
import android.view.View
import com.padcmyanmar.ttm.wechatapp.data.vos.ChatMessageVO
import com.padcmyanmar.ttm.wechatapp.utils.covertTimeToText
import kotlinx.android.synthetic.main.view_holder_receiver_msg_view.view.*
import kotlinx.android.synthetic.main.view_holder_sender_msg_view.view.*
import java.text.SimpleDateFormat
import java.util.*

class ReceiveMessageListViewHolder(itemView: View) : BaseViewHolder<ChatMessageVO>(itemView,null){

   init{
   }

   override fun bindData(data: ChatMessageVO?) {
      itemView.tvReceiveMsg.text = data?.message
      val dateFormat = SimpleDateFormat("hh:mm a", Locale.US)
      val  dateString:String = dateFormat.format(data?.timestamp?.toLong()?.let { Date(it) })
      itemView.tvReceiveTime.text = dateString

   }


}