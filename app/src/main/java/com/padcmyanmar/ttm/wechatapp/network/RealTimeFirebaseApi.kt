package com.padcmyanmar.ttm.wechatapp.network

import com.padcmyanmar.ttm.wechatapp.data.vos.ChatGroupVO
import com.padcmyanmar.ttm.wechatapp.data.vos.ChatHistoryVO
import com.padcmyanmar.ttm.wechatapp.data.vos.ChatMessageVO

interface RealTimeFirebaseApi {

    fun sendMessage(senderId:String,receiverId:String,msg:String,senderName:String,
                    onSuccess: (message: String) -> Unit,
                    onFailure: (message: String) -> Unit)


    fun getChatMessageList(receiverId:String,
                           checkPrivateOrGroup:String,
                           onSuccess:(chatMsgList: List<ChatMessageVO>)->Unit,
                           onFailure: (message: String) -> Unit)

    fun getChatHistoryList(senderId:String,
                           onSuccess:(chatHistoryListVO: List<ChatHistoryVO>)->Unit,
                           onFailure: (message: String) -> Unit)


    fun createChatGroup(groupName:String,membersList:ArrayList<String>,groupPhoto:String,
                        onSuccess: (message: String) -> Unit,
                        onFailure: (message: String) -> Unit)

    fun getChatGroupsList(onSuccess:(chatGroupList:ArrayList<ChatGroupVO>)->Unit,
                          onFailure: (message: String) -> Unit)
    fun sendGroupMessage(
    senderId: String,
    receiverId: String,
    msg: String,
    senderName: String,
    onSuccess: (message: String) -> Unit,
    onFailure: (message: String) -> Unit
    )

}