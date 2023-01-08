package com.padcmyanmar.ttm.wechatapp.data.models

import android.graphics.Bitmap
import android.net.Uri
import com.padcmyanmar.ttm.wechatapp.data.vos.*
import com.padcmyanmar.ttm.wechatapp.network.FirebaseApi
import com.padcmyanmar.ttm.wechatapp.network.RealTimeFirebaseApi

interface WeChatAppModel {

    var mFirebaseApi: FirebaseApi
    var mFirebaseRealTimeApi: RealTimeFirebaseApi
    fun addUser(
        name: String,
        dateOfBirth: String,
        gender: String,
        password: String,
        phoneNo: String,
        userId: String,
        imageUrl: String,
        activeStatus:String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    )


    fun getUser(
        phoneNumber: String,
        password: String,
        onSuccess: (userVO: UserVO) -> Unit,
        onFailure: (message: String) -> Unit
    )

    fun uploadImageAndVideoFile(fileUri: Uri, onSuccess: (returnUrlString: String?) -> Unit)

    fun getMomentData(
        onSuccess: (momentsList: ArrayList<MomentVO>) -> Unit,
        onFailure: (message: String) -> Unit
    )

    fun getMomentDataByBookMarkList(
        onSuccess: (momentsList: ArrayList<MomentVO>) -> Unit,
        onFailure: (message: String) -> Unit
    )


    fun addMoment(
        imgList: ArrayList<MediaDataVO>,
        likeIdList: ArrayList<String>,
        description: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    )

    fun editMoment(
        momentVO: MomentVO,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    )

    fun addContacts(
        userId: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    )

    fun getContacts(
        onSuccess: (contactsList: ArrayList<UserVO>) -> Unit,
        onFailure: (message: String) -> Unit
    )

    fun editUser(
        userName: String,
        dateOfBirth: String,
        genderType: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    )

    fun sendMessage(
        senderId: String,
        receiverId: String,
        msg: String,
        senderName: String,
        fileUrl: String,
        profileUrl: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit

    )


    fun getChatMessageList(
        receiverId: String,
        checkPrivateOrGroup: String,
        onSuccess: (chatMsgList: List<ChatMessageVO>) -> Unit,
        onFailure: (message: String) -> Unit
    )


    fun getChatHistoryList(
        senderId: String,
        onSuccess: (chatHistoryListVO: List<ChatHistoryVO>) -> Unit,
        onFailure: (message: String) -> Unit
    )

    fun createChatGroup(
        groupName: String, membersList: ArrayList<String>, groupPhoto: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    )

    fun getChatGroupsList(
        onSuccess: (chatGroupList: ArrayList<ChatGroupVO>) -> Unit,
        onFailure: (message: String) -> Unit
    )

    fun sendGroupMessage(
        senderId: String,
        receiverId: String,
        msg: String,
        senderName: String,
        fileUrl: String,
        profileUrl: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    )

    fun uploadImageAndUpdateGrocery(
        userVO: UserVO,
        image: Bitmap,
        onSuccess: (returnUrlString: String?) -> Unit
    )

}