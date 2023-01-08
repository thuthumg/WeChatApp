package com.padcmyanmar.ttm.wechatapp.delegates

import com.padcmyanmar.ttm.wechatapp.data.vos.ChatHistoryVO

interface ChatsListDelegate {


    fun goToChatDetailFromChatFragmentPage(mChatHistoryVO: ChatHistoryVO, checkListType: String)
}