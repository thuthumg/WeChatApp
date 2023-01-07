package com.padcmyanmar.ttm.wechatapp.viewholders

import android.view.View
import com.padcmyanmar.ttm.wechatapp.data.vos.ChatGroupVO
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.delegates.ContactListItemDelegate
import kotlinx.android.synthetic.main.view_holder_chat_group_item.view.*


class ChatGroupsListViewHolder(itemView: View, mDelegate: ContactListItemDelegate) :BaseViewHolderForChatGroup(itemView){
    var mContactVO:ChatGroupVO = ChatGroupVO()
    init{
        itemView.setOnClickListener {
              mDelegate.goToChatDetailFromContactGroupList(mContactVO.name.toString(),mContactVO.id.toString(),"GroupChat")
        }
    }

    fun bindData(data: ChatGroupVO){
        mContactVO = data
        itemView.tvGroupName.text = mContactVO.name.toString()

    }
}