package com.padcmyanmar.ttm.wechatapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.utils.VIEW_TYPE_ADD_GROUP
import com.padcmyanmar.ttm.wechatapp.utils.VIEW_TYPE_GROUP
import com.padcmyanmar.ttm.wechatapp.viewholders.*

class ChatGroupsListAdapter() : RecyclerView.Adapter<BaseViewHolderForChatGroup>() {

  //  private var mData: ArrayList<Uri> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolderForChatGroup {

        when(viewType)
        {
            VIEW_TYPE_ADD_GROUP ->{
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.view_holder_add_chat_group_item, parent, false)
                return AddChatGroupViewHolder(view)
            }
            VIEW_TYPE_GROUP ->{
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.view_holder_chat_group_item, parent, false)
                return ChatGroupsListViewHolder(view)
            }
            else->{
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.view_holder_add_chat_group_item, parent, false)
                return AddChatGroupViewHolder(view)
            }
        }

    }

    override fun onBindViewHolder(holder: BaseViewHolderForChatGroup, position: Int) {

//        when(holder.itemViewType)
//        {
//            VIEW_TYPE_ADD_GROUP ->{
//
//                var  addChatGroupViewHolder: AddChatGroupViewHolder = holder as AddChatGroupViewHolder
//                addChatGroupViewHolder.bindData(null)
//
//            }
//            VIEW_TYPE_GROUP ->{
//                var chatGroupsListViewHolder : ChatGroupsListViewHolder = holder as ChatGroupsListViewHolder
//                chatGroupsListViewHolder.bindData(mData[position])
//            }
//        }

    }

    override fun getItemCount(): Int {

        return 3
    /*    return if(mData.isEmpty()) {
            1
        }else{
            mData.count()+1
        }*/
    }

  /*  @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data:ArrayList<Uri>){
        mData = data
        notifyDataSetChanged()
    }*/


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