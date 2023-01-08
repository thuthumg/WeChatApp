package com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.data.models.WeChatAppModelImpl
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.ChatDetailPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.views.ChatDetailView

class ChatDetailPresenterImpl : ChatDetailPresenter, AbstractBasePresenter<ChatDetailView>() {


    private val mWeChatAppModel = WeChatAppModelImpl

    override fun onTapSendMsg(
        senderId: String,
        receiverId: String,
        msg: String,
        senderName: String,
        fileUrl: String,
        profileUrl: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {

        mWeChatAppModel.sendMessage(
            senderId = senderId,
            receiverId = receiverId,
            msg = msg,
            senderName = senderName,
            fileUrl = fileUrl,
            profileUrl = profileUrl,
            onSuccess = {
                onSuccess(it)
            }
        ) {
            onFailure(it)
        }
    }

    override fun sendGroupMessage(
        senderId: String,
        receiverId: String,
        msg: String,
        senderName: String,
        fileUrl: String,
        profileUrl: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mWeChatAppModel.sendGroupMessage(
            senderId = senderId,
            receiverId = receiverId,
            msg = msg,
            senderName = senderName,
            fileUrl = fileUrl,
            profileUrl = profileUrl,
            onSuccess = {
                onSuccess(it)
            }
        ) {
            onFailure(it)
        }
    }

    override fun uploadFileCreate(fileUri: Uri, onSuccess: (returnUrlString: String?) -> Unit) {
        mWeChatAppModel.uploadImageAndVideoFile(fileUri,  onSuccess = {
           onSuccess(it)
        })
    }

    override fun onUiReadyInChatDetails(
        owner: LifecycleOwner,
        receiverId: String,
        checkPrivateOrGroup: String
    ) {
        mWeChatAppModel.getChatMessageList(receiverId,
            checkPrivateOrGroup,
            onSuccess = {

                for(chatData in it)
                {
                    Log.d("presenter","chatdata = ${chatData.file} ${chatData.message}")
                }


                mView.showChatMessageList(it)
            },
            onFailure = {
                mView.showError(it)
            })
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }
}