package com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.data.models.WeChatAppModelImpl
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.ChatDetailPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.views.ChatDetailView

class ChatDetailPresenterImpl:ChatDetailPresenter, AbstractBasePresenter<ChatDetailView>() {


    private val mWeChatAppModel = WeChatAppModelImpl

    override fun onTapSendMsg(
        senderId: String,
        receiverId: String,
        msg: String,
        senderName: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {

        mWeChatAppModel.sendMessage(
            senderId = senderId,
            receiverId = receiverId,
            msg = msg,
            senderName = senderName,
            onSuccess = {
                onSuccess(it)
            },
            onFailure = {
                onFailure(it)
            }
        )
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }
}