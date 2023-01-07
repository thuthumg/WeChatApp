package com.padcmyanmar.ttm.wechatapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.data.vos.ChatMessageVO
import com.padcmyanmar.ttm.wechatapp.utils.*
import com.padcmyanmar.ttm.wechatapp.viewholders.*

class ChatDetailListAdapter : RecyclerView.Adapter<BaseViewHolder<ChatMessageVO>>() {

    private var mData: List<ChatMessageVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ChatMessageVO> {

        when(viewType)
        {
            VIEW_TYPE_SENDER ->{
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.view_holder_sender_msg_view, parent, false)
                return SendMessageListViewHolder(view)
            }
            VIEW_TYPE_RECEIVER->{
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.view_holder_receiver_msg_view, parent, false)
                return ReceiveMessageListViewHolder(view)
            }
            else->{
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.view_holder_sender_msg_view, parent, false)
                return SendMessageListViewHolder(view)
            }
        }

    }

    override fun onBindViewHolder(holder: BaseViewHolder<ChatMessageVO>, position: Int) {


        when(holder.itemViewType)
        {
            VIEW_TYPE_SENDER ->{

                var  mSendMessageListViewHolder: SendMessageListViewHolder = holder as SendMessageListViewHolder
                mSendMessageListViewHolder.bindData(mData[position])

            }
            VIEW_TYPE_RECEIVER ->{
                var mReceiveMessageListViewHolder : ReceiveMessageListViewHolder = holder as ReceiveMessageListViewHolder
                if(mData.isNotEmpty()){
                    mReceiveMessageListViewHolder.bindData(mData[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mData.count()
      //  return 5
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data: List<ChatMessageVO>){
        mData = data
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if( mData[position].user_id == mUserVO.id){
            VIEW_TYPE_SENDER
        }else{
            VIEW_TYPE_RECEIVER
        }
       // return super.getItemViewType(position)
    }
}