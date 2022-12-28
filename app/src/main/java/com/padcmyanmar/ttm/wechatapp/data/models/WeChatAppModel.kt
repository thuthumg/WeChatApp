package com.padcmyanmar.ttm.wechatapp.data.models

import android.graphics.Bitmap
import android.net.Uri
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.network.FirebaseApi

interface WeChatAppModel {

    var mFirebaseApi : FirebaseApi

    fun addUser(name: String, dateOfBirth:String , gender:String, password:String,phoneNo:String,
                onSuccess :  (message:String)->Unit , onFailure : (message:String)-> Unit)


    fun getUsers(phoneNumber:String,
                 password: String,
                 onSuccess: (userLists: List<UserVO>) -> Unit,
                 onFailure: (message: String) -> Unit)

    fun uploadPhoto(image: Bitmap, onSuccess: (returnUrlString: String?) -> Unit)

    fun uploadImageAndVideoFile(fileUri: Uri, onSuccess: (returnUrlString: String?) -> Unit)

    fun addMoment(
    imgList: ArrayList<String>,
    description: String,
    onSuccess: (message: String) -> Unit,
    onFailure: (message: String) -> Unit
    )
}