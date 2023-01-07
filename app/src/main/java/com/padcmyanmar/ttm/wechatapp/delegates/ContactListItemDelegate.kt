package com.padcmyanmar.ttm.wechatapp.delegates

import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO

interface ContactListItemDelegate {

    fun goToCreateGroupChat(loginUserId:String)

    fun goToChatDetailFromContactList(contactName: String,chatId:String)

    fun goToChatDetailFromContactGroupList(contactName: String,chatId:String,sParam:String)

    fun onTapSelectContactList(contactName: UserVO)
}