package com.padcmyanmar.ttm.wechatapp.mvp.views

import com.padcmyanmar.ttm.wechatapp.data.vos.ChatGroupVO
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO

interface ContactsFragmentView:BaseView {

    fun showContactsData(contactsList:ArrayList<UserVO>)

    fun navigateToChatDetailFromContactPage(contactName: String, chatId: String)

    fun navigateToChatGroupCreate(loginUserId:String)

    fun showChatGroupsList(chatGroupList:ArrayList<ChatGroupVO>)

    fun navigateToChatGroupDetailPage(contactName: String, chatId: String,sParam:String)
}