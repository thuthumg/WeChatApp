package com.padcmyanmar.ttm.wechatapp.mvp.presenters

import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.mvp.views.ChatDetailView

interface ChatDetailPresenter:BasePresenter<ChatDetailView> {

    fun onTapSendMsg( senderId: String,
                      receiverId: String,
                      msg: String,
                      senderName: String,
                      fileUrl:String,
                      profileUrl:String,
                      onSuccess: (message: String) -> Unit,
                      onFailure: (message: String) -> Unit
    )

    fun sendGroupMessage(
        senderId: String,
        receiverId: String,
        msg: String,
        senderName: String,
        fileUrl:String,
        profileUrl:String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    )

    fun uploadFileCreate(fileUri: Uri, onSuccess: (returnUrlString: String?) -> Unit)


    fun onUiReadyInChatDetails(owner: LifecycleOwner, receiverId: String,checkPrivateOrGroup:String)
}