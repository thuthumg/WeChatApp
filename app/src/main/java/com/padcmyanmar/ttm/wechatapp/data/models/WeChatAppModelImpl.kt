package com.padcmyanmar.ttm.wechatapp.data.models

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
        onSuccess: (userLists: List<UserVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseApi.getUsers(onSuccess,onFailure)
    }


}