package com.padcmyanmar.ttm.wechatapp.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.mvp.views.ChatDetailView

interface ChatDetailPresenter:BasePresenter<ChatDetailView> {

    fun onTapSendMsg( senderId: String,
                      receiverId: String,
                      msg: String,
                      senderName: String,
                      onSuccess: (message: String) -> Unit,
                      onFailure: (message: String) -> Unit
    )

    fun sendGroupMessage(
        senderId: String,
        receiverId: String,
        msg: String,
        senderName: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    )


    fun onUiReadyInChatDetails(owner: LifecycleOwner, receiverId: String,checkPrivateOrGroup:String)
}