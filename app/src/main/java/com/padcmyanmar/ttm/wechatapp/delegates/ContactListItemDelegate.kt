package com.padcmyanmar.ttm.wechatapp.delegates

import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO

interface ContactListItemDelegate {

    fun goToCreateGroupChat(loginUserId:String)

    fun goToChatDetailFromContactList(contactName: String, chatId: String, contactProfile: String)

    fun goToChatDetailFromContactGroupList(
        contactName: String,
        chatId: String,
        contactProfile: String,
        CHAT_TYPE_GROUP: String
    )

    fun onTapSelectContactList(contactName: UserVO)
}