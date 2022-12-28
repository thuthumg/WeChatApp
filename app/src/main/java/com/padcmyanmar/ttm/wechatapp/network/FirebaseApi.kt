package com.padcmyanmar.ttm.wechatapp.network

import android.graphics.Bitmap
import android.net.Uri
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO

interface FirebaseApi {
    fun addUser(
        name: String,
        dateOfBirth: String,
        gender: String,
        password: String,
        phoneNum: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    )


    fun getUsers(
        phoneNum:String,
        password: String,
        onSuccess: (usersList: List<UserVO>) -> Unit,
        onFailure: (message: String) -> Unit
    )
    fun addMoment(
    imgList: ArrayList<String>,
    description: String,
    onSuccess: (message: String) -> Unit,
    onFailure: (message: String) -> Unit
    )

//    fun addMoment(
//        name: String,
//        description: String,
//        timestamp: String,
//        phoneNum: String,
//        photoOrVideoLink: String
//    )

    fun uploadImageUserVO(
        image: Bitmap,
        onSuccess: (returnUrlString: String?) -> Unit
    )
    fun uploadImageAndVideoFile(
        fileUri: Uri,
        onSuccess: (returnUrlString: String?) -> Unit
    )

}