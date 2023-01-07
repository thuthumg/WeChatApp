package com.padcmyanmar.ttm.wechatapp.mvp.presenters

import com.padcmyanmar.ttm.wechatapp.delegates.ChatsListDelegate
import com.padcmyanmar.ttm.wechatapp.mvp.views.ChatFragmentView

interface ChatFragmentPresenter:BasePresenter<ChatFragmentView>, ChatsListDelegate {

}