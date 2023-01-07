package com.padcmyanmar.ttm.wechatapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.data.vos.ChatGroupVO
import com.padcmyanmar.ttm.wechatapp.delegates.ContactListItemDelegate
import com.padcmyanmar.ttm.wechatapp.utils.VIEW_TYPE_ADD_GROUP
import com.padcmyanmar.ttm.wechatapp.utils.VIEW_TYPE_GROUP
import com.padcmyanmar.ttm.wechatapp.viewholders.*

class ChatGroupsListAdapter(var mDelegate: ContactListItemDelegate) : RecyclerView.Adapter<BaseViewHolderForChatGroup>() {

    private var mData: ArrayList<ChatGroupVO> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolderForChatGroup {

        when(viewType)
        {
            VIEW_TYPE_ADD_GROUP ->{
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.view_holder_add_chat_group_item, parent, false)
                return AddChatGroupViewHolder(view,mDelegate)
            }
            VIEW_TYPE_GROUP ->{
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.view_holder_chat_group_item, parent, false)
                return ChatGroupsListViewHolder(view,mDelegate)
            }
            else->{
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.view_holder_add_chat_group_item, parent, false)
                return AddChatGroupViewHolder(view,mDelegate)
            }
        }

    }

    override fun onBindViewHolder(holder: BaseViewHolderForChatGroup, position: Int) {

        when(holder.itemViewType)
        {
            VIEW_TYPE_ADD_GROUP ->{

//                var  addChatGroupViewHolder: AddChatGroupViewHolder = holder as AddChatGroupViewHolder
//                addChatGroupViewHolder.bindData(null)

            }
            VIEW_TYPE_GROUP ->{
                var chatGroupsListViewHolder : ChatGroupsListViewHolder = holder as ChatGroupsListViewHolder
                chatGroupsListViewHolder.bindData(mData[position-1])
            }
        }

    }

    override fun getItemCount(): Int {

        return if(mData.isEmpty()) {
            1
        }else{
            mData.count()+1
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data:ArrayList<ChatGroupVO>){
        mData = data
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {


        return when (position) {
            0 -> {
                VIEW_TYPE_ADD_GROUP
            }
            else -> {
                VIEW_TYPE_GROUP
            }

        }
    }
}