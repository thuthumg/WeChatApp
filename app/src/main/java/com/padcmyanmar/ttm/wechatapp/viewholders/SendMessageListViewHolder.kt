package com.padcmyanmar.ttm.wechatapp.viewholders

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.data.vos.ChatMessageVO
import com.padcmyanmar.ttm.wechatapp.utils.covertTimeToText
import kotlinx.android.synthetic.main.view_holder_sender_msg_view.view.*
import java.text.SimpleDateFormat
import java.util.*

class SendMessageListViewHolder(itemView: View) : BaseViewHolder<ChatMessageVO>(itemView,null){

   init{
   }

   override fun bindData(data: ChatMessageVO?) {
     itemView.tvSendMsg.text = data?.message

       var dateFormat = SimpleDateFormat("hh:mm a", Locale.US)
       var  dateString:String = dateFormat.format(data?.timestamp?.toLong()?.let { Date(it) })
      itemView.tvSendTime.text = dateString
   }

}