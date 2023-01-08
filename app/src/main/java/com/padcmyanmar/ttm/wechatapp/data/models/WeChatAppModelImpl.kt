package com.padcmyanmar.ttm.wechatapp.data.models

import android.graphics.Bitmap
import android.net.Uri
import com.padcmyanmar.ttm.wechatapp.data.vos.*
import com.padcmyanmar.ttm.wechatapp.network.CloudFirestoreFirebaseApiImpl
import com.padcmyanmar.ttm.wechatapp.network.FirebaseApi
import com.padcmyanmar.ttm.wechatapp.network.RealTimeFirebaseApi
import com.padcmyanmar.ttm.wechatapp.network.RealtimeDatabaseFirebaseApiImpl

object WeChatAppModelImpl : WeChatAppModel {
    override var mFirebaseApi: FirebaseApi = CloudFirestoreFirebaseApiImpl

    override var mFirebaseRealTimeApi: RealTimeFirebaseApi = RealtimeDatabaseFirebaseApiImpl

    override fun addUser(
        name: String,
        dateOfBirth: String,
        gender: String,
        password: String,
        phoneNo: String,
        userId: String,
        imageUrl: String,
        activeStatus: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseApi.addUser(
            name = name,
            dateOfBirth = dateOfBirth,
            gender = gender,
            password = password,
            phoneNum = phoneNo,
            userId = userId,
            profileImageUrl = imageUrl,
            activeStatus = activeStatus,
            onSuccess,
            onFailure
        )
    }

    override fun getUser(
        phoneNumber: String,
        password: String,
        onSuccess: (userVO: UserVO) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseApi.getUser(
            phoneNum = phoneNumber,
            password = password,
            onSuccess = {
                onSuccess(it)
            },
            onFailure = {
                onFailure(it)
            })
    }

    override fun uploadImageAndVideoFile(
        fileUri: Uri,
        onSuccess: (returnUrlString: String?) -> Unit
    ) {
        mFirebaseApi.uploadImageAndVideoFile(
            fileUri = fileUri,
            onSuccess = {
                onSuccess(it)
            }
        )
    }

    override fun addMoment(
        imgList: ArrayList<MediaDataVO>,
        likeIdList: ArrayList<String>,
        description: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseApi.addMoment(
            imgList = imgList,
            likedId = likeIdList,
            description = description,
            onSuccess = {
                onSuccess(it)
            }
        ) {
            onFailure(it)
        }
    }


    override fun getMomentData(
        onSuccess: (momentsList: ArrayList<MomentVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseApi.getMomentData(
            onSuccess = {
                onSuccess(it)
            },
            onFailure = {
                onFailure(it)
            })
    }

    override fun getMomentDataByBookMarkList(
        onSuccess: (momentsList: ArrayList<MomentVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseApi.getMomentDataByBookMarkList(
            onSuccess = {
                onSuccess(it)
            },
            onFailure = {
                onFailure(it)
            })
    }

    override fun editMoment(
        momentVO: MomentVO,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseApi.editMoment(
            momentVO,
            onSuccess = {
                onSuccess(it)
            }
        ) {
            onFailure(it)
        }
    }

    override fun addContacts(
        userId: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseApi.addContacts(userId = userId,
            onSuccess = {
                onSuccess(it)
            },
            onFailure = {
                onFailure(it)
            })
    }

    override fun getContacts(
        onSuccess: (contactsList: ArrayList<UserVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseApi.getContacts(
            onSuccess = {
                onSuccess(it)
            },
            onFailure = {
                onFailure(it)
            }
        )
    }

    override fun editUser(
        userName: String, dateOfBirth: String, genderType: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseApi.editUser(userName, dateOfBirth, genderType, onSuccess = {
            onSuccess(it)
        }, onFailure = {
            onFailure(it)
        })

    }

    override fun sendMessage(
        senderId: String,
        receiverId: String,
        msg: String,
        senderName: String,
        fileUrl: String,
        profileUrl: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseRealTimeApi.sendMessage(
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

    override fun getChatMessageList(
        receiverId: String,
        checkPrivateOrGroup: String,
        onSuccess: (chatMsgList: List<ChatMessageVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseRealTimeApi.getChatMessageList(receiverId = receiverId,
            checkPrivateOrGroup,
            onSuccess = {
                onSuccess(it)
            },
            onFailure = {
                onFailure(it)
            })
    }

    override fun getChatHistoryList(
        senderId: String,
        onSuccess: (chatHistoryListVO: List<ChatHistoryVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseRealTimeApi.getChatHistoryList(senderId = senderId,
            onSuccess = { onSuccess(it) }, onFailure = { onFailure(it) })
    }

    override fun createChatGroup(
        groupName: String,
        membersList: ArrayList<String>,
        groupPhoto: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
         mFirebaseRealTimeApi.createChatGroup(
            groupName = groupName,
            membersList = membersList,
            groupPhoto = groupPhoto,
            onSuccess = { onSuccess(it) },
            onFailure = { onFailure(it) }
        )


    }

    override fun getChatGroupsList(
        onSuccess: (chatGroupList: ArrayList<ChatGroupVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseRealTimeApi.getChatGroupsList(
            onSuccess = { onSuccess(it) },
            onFailure = { onFailure(it) }
        )
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
        mFirebaseRealTimeApi.sendGroupMessage(
            senderId = senderId,
            receiverId = receiverId,
            msg = msg,
            senderName = senderName,
            fileUrl = fileUrl,
            profileUrl = profileUrl,
            onSuccess = { onSuccess(it) }
        ) { onFailure(it) }
    }

    override fun uploadImageAndUpdateGrocery(
        userVO: UserVO,
        image: Bitmap,
        onSuccess: (returnUrlString: String?) -> Unit
    ) {


        mFirebaseApi.uploadImageAndEditUserVO(image, userVO, onSuccess = { onSuccess(it) })
    }


}