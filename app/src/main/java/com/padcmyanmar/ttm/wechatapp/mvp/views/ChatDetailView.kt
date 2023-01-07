package com.padcmyanmar.ttm.wechatapp.mvp.views

import com.padcmyanmar.ttm.wechatapp.data.vos.ChatMessageVO

interface ChatDetailView:BaseView {
    fun showChatMessageList(chatMessagesList: List<ChatMessageVO>)

}