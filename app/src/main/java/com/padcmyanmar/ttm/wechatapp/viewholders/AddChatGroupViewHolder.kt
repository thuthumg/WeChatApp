package com.padcmyanmar.ttm.wechatapp.viewholders

import android.view.View
import com.padcmyanmar.ttm.wechatapp.delegates.ContactListItemDelegate
import com.padcmyanmar.ttm.wechatapp.utils.mUserVO


class AddChatGroupViewHolder(itemView: View, mDelegate: ContactListItemDelegate) : BaseViewHolderForChatGroup(itemView) {

    init{
        itemView.setOnClickListener {
            mDelegate.goToCreateGroupChat(mUserVO.id.toString())
        }
    }

//    fun bindData(data:){
//
//    }
}