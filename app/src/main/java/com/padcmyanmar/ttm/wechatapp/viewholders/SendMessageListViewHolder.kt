package com.padcmyanmar.ttm.wechatapp.viewholders

import android.util.Log
import android.view.View
import androidx.core.net.toUri

import com.bumptech.glide.Glide
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.data.vos.ChatMessageVO
import com.padcmyanmar.ttm.wechatapp.utils.loadBitMapFromUri
import com.padcmyanmar.ttm.wechatapp.utils.scaleToRatio
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_chat_detail.*

import kotlinx.android.synthetic.main.view_holder_sender_msg_view.view.*
import java.text.SimpleDateFormat
import java.util.*


class SendMessageListViewHolder(itemView: View) : BaseViewHolder<ChatMessageVO>(itemView,null){

   init{
   }

   override fun bindData(data: ChatMessageVO?) {
    // itemView.tvSendMsg.text = data?.message

       var dateFormat = SimpleDateFormat("hh:mm a", Locale.US)
       var  dateString:String = dateFormat.format(data?.timestamp?.toLong()?.let { Date(it) })

        Log.d("sendmessage","check file = ${data?.file}")
       if(data?.file == null || data?.file == "" || data?.file == "null")
       {
           Log.d("sendmessage","check file condition 1")
           itemView.llSenderImageMessage.visibility = View.GONE
       }else{
           Log.d("sendmessage","check file condition 2")
           itemView.llSenderImageMessage.visibility = View.VISIBLE

          Glide.with(itemView.context)
              .load(data?.file)
              .placeholder(R.drawable.error_image_bg)
              .into(itemView.ivImageMessage)
           itemView.tvSendTimeImage.text = dateString
       }

       if(data?.message == null || data?.message == "")
       {
           itemView.cvSenderTextMessage.visibility = View.GONE
       }else{
           itemView.cvSenderTextMessage.visibility = View.VISIBLE
           itemView.tvSendMsg.text = data?.message
           itemView.tvSendTime.text = dateString

       }



   }

}