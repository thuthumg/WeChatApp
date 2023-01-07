package com.padcmyanmar.ttm.wechatapp.mvp.views

import com.padcmyanmar.ttm.wechatapp.data.vos.ChatHistoryVO
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO

interface ChatFragmentView:BaseView {
   fun showChatMessageHistoryList(
       chatHistoryMessageList: List<ChatHistoryVO>
   )

    fun navigateToChatDetailFromChatFragmentPage(chatHistoryVO: ChatHistoryVO)
    fun showContactsData(contactsList:ArrayList<UserVO>)
}