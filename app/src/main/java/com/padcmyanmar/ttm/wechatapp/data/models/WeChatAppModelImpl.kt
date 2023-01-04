package com.padcmyanmar.ttm.wechatapp.data.models

import android.net.Uri
import com.padcmyanmar.ttm.wechatapp.data.vos.MediaDataVO
import com.padcmyanmar.ttm.wechatapp.data.vos.MomentVO
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.network.CloudFirestoreFirebaseApiImpl
import com.padcmyanmar.ttm.wechatapp.network.FirebaseApi
import com.padcmyanmar.ttm.wechatapp.network.RealtimeDatabaseFirebaseApiImpl

object WeChatAppModelImpl: WeChatAppModel  {
    override var mFirebaseApi: FirebaseApi = CloudFirestoreFirebaseApiImpl

    override var mFirebaseRealTimeApi: FirebaseApi = RealtimeDatabaseFirebaseApiImpl

    override fun addUser(
        name: String,
        dateOfBirth: String,
        gender: String,
        password: String,
        phoneNo: String,
        userId:String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseApi.addUser(name = name,
            dateOfBirth =  dateOfBirth,
            gender =  gender,
            password = password,
            phoneNum = phoneNo,
            userId = userId,
            onSuccess,
            onFailure)
    }

    override fun getUser(
        phoneNo:String,
        password: String,
        onSuccess: (userVO: UserVO) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseApi.getUser(
            phoneNum = phoneNo,
            password = password,
            onSuccess = {
                        onSuccess(it)
            },
            onFailure = {
                onFailure(it)
            })
    }


//    override fun uploadPhoto(image: Bitmap, onSuccess: (returnUrlString: String?) -> Unit){
//        mFirebaseApi.uploadImageUserVO(
//            image = image,
//            onSuccess = onSuccess
//        )
//    }
    override fun uploadImageAndVideoFile(fileUri: Uri, onSuccess: (returnUrlString: String?) -> Unit){
        mFirebaseApi.uploadImageAndVideoFile(
            fileUri = fileUri,
            onSuccess = {
                onSuccess(it)
            }
        )
    }

    override fun addMoment(
        imgList: ArrayList<MediaDataVO>,
        likeIdList:ArrayList<String>,
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


    override fun getMomentData  (
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

    override fun editMoment(
        momentVO:MomentVO,
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

    override fun editUser(userName: String, dateOfBirth: String, genderType: String,
                             onSuccess: (message: String) -> Unit,
                             onFailure: (message: String) -> Unit) {
        mFirebaseApi.editUser(userName,dateOfBirth,genderType, onSuccess = {
            onSuccess(it)
        }, onFailure = {
            onFailure(it)
        })

    }

    override fun sendMessage( senderId: String,
                              receiverId: String,
                              msg: String,
                              senderName: String,
                              onSuccess: (message: String) -> Unit,
                              onFailure: (message: String) -> Unit
    ) {
        mFirebaseRealTimeApi.sendMessage(
            senderId =  senderId,
            receiverId = receiverId,
            msg =  msg,
            senderName = senderName,
            onSuccess = {
                onSuccess(it)
            },
            onFailure = {
                onFailure(it)
            }
        )
    }


}