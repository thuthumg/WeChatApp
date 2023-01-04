package com.padcmyanmar.ttm.wechatapp.data.models

import android.net.Uri
import com.padcmyanmar.ttm.wechatapp.data.vos.MediaDataVO
import com.padcmyanmar.ttm.wechatapp.data.vos.MomentVO
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.network.FirebaseApi

interface WeChatAppModel {

    var mFirebaseApi : FirebaseApi
    var mFirebaseRealTimeApi : FirebaseApi
    fun addUser(name: String, dateOfBirth:String , gender:String, password:String,phoneNo:String,
                userId:String,onSuccess :  (message:String)->Unit , onFailure : (message:String)-> Unit)


    fun getUser(phoneNumber:String,
                 password: String,
                 onSuccess: (userVO: UserVO) -> Unit,
                 onFailure: (message: String) -> Unit)

//    fun uploadPhoto(image: Bitmap, onSuccess: (returnUrlString: String?) -> Unit)

    fun uploadImageAndVideoFile(fileUri: Uri, onSuccess: (returnUrlString: String?) -> Unit)


    fun getMomentData(
        onSuccess: (momentsList: ArrayList<MomentVO>) -> Unit,
        onFailure: (message: String) -> Unit
    )
    fun addMoment(
        imgList: ArrayList<MediaDataVO>,
        likeIdList:ArrayList<String>,
        description: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    )
//    fun addMoment(
//        imgList: ArrayList<String>,
//        likeIdList: ArrayList<String>,
//        description: String,
//        onSuccess: (message: String) -> Unit,
//        onFailure: (message: String) -> Unit
//    )
    fun editMoment(
    momentVO: MomentVO,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    )

    fun addContacts(
        userId: String,
        onSuccess:(message:String)-> Unit,
        onFailure: (message: String) -> Unit
    )

    fun getContacts(
        onSuccess: (contactsList: ArrayList<UserVO>) -> Unit,
        onFailure: (message: String) -> Unit
    )

    fun editUser(
        userName:String,
        dateOfBirth: String,
        genderType:String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    )

    fun sendMessage(
        senderId: String,
        receiverId: String,
        msg: String,
        senderName: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit

    )
}