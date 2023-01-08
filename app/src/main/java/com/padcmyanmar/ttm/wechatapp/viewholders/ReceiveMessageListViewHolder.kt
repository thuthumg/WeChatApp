package com.padcmyanmar.ttm.wechatapp.viewholders

import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.padcmyanmar.ttm.wechatapp.R
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
     // itemView.tvReceiveMsg.text = data?.message
      val dateFormat = SimpleDateFormat("hh:mm a", Locale.US)
      val  dateString:String = dateFormat.format(data?.timestamp?.toLong()?.let { Date(it) })
      Log.d("receivermessage","check receiver file = ${data?.file}")

      if(data?.file == null || data?.file == "" || data?.file == "null")
      {
         Log.d("receivermessage","check receiver file  condition1")
         itemView.llReceiverImageMessage.visibility = View.GONE
      }else{
         Log.d("receivermessage","check receiver file  condition2")
         itemView.llReceiverImageMessage.visibility = View.VISIBLE
         Glide.with(itemView.context)
            .load(data?.file)
            .placeholder(R.drawable.empty_image)
            .into(itemView.ivReceiverImageMessage)
         itemView.tvReceiveImageTime.text = dateString
      }

      if(data?.message == null || data?.message == "")
      {
         itemView.cvReceiverTextMessage.visibility = View.GONE
      }else{
         itemView.cvReceiverTextMessage.visibility = View.VISIBLE
         itemView.tvReceiveMsg.text = data?.message
         itemView.tvReceiveTime.text = dateString
      }

      Glide.with(itemView.context)
         .load(data?.profileUrl)
         .placeholder(R.drawable.profile_sample)
         .into(itemView.ivReceiverChatProfile)

   }


}