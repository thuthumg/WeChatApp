package com.padcmyanmar.ttm.wechatapp.data.models

import android.graphics.Bitmap
import android.net.Uri
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.network.CloudFirestoreFirebaseApiImpl
import com.padcmyanmar.ttm.wechatapp.network.FirebaseApi

object WeChatAppModelImpl: WeChatAppModel  {
    override var mFirebaseApi: FirebaseApi = CloudFirestoreFirebaseApiImpl


    override fun addUser(
        name: String,
        dateOfBirth: String,
        gender: String,
        password: String,
        phoneNo: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseApi.addUser(name = name,
            dateOfBirth =  dateOfBirth,
            gender =  gender,
            password = password,
            phoneNum = phoneNo,
            onSuccess,
            onFailure)
    }

    override fun getUsers(
        phoneNo:String,
        password: String,
        onSuccess: (userLists: List<UserVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseApi.getUsers(
            phoneNum = phoneNo,
            password = password,
            onSuccess = onSuccess,
            onFailure = onFailure)
    }


    override fun uploadPhoto(image: Bitmap, onSuccess: (returnUrlString: String?) -> Unit){
        mFirebaseApi.uploadImageUserVO(
            image = image,
            onSuccess = onSuccess
        )
    }
    override fun uploadImageAndVideoFile(fileUri: Uri, onSuccess: (returnUrlString: String?) -> Unit){
        mFirebaseApi.uploadImageAndVideoFile(
            fileUri = fileUri,
            onSuccess = onSuccess
        )
    }

    override fun addMoment(
        imgList: ArrayList<String>,
        description: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseApi.addMoment(
            imgList = imgList,
            description = description,
            onSuccess,
            onFailure)
    }

}