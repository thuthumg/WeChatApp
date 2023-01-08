package com.padcmyanmar.ttm.wechatapp.mvp.views

import com.padcmyanmar.ttm.wechatapp.data.vos.ChatGroupVO
import com.padcmyanmar.ttm.wechatapp.data.vos.ChatHistoryVO
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO

interface ChatFragmentView:BaseView {
   fun showChatMessageHistoryList(
       chatHistoryMessageList: List<ChatHistoryVO>
   )
    fun navigateToChatDetailFromChatFragmentPage(
        chatHistoryVO: ChatHistoryVO,
        checkListType: String
    )
    fun showContactsData(contactsList:ArrayList<UserVO>)
    fun showGroupChatListData(groupChatHistoryListVO:ArrayList<ChatGroupVO>)
}