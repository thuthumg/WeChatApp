package com.padcmyanmar.ttm.wechatapp.data.models

import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.network.FirebaseApi

interface WeChatAppModel {

    var mFirebaseApi : FirebaseApi

    fun addUser(name: String, dateOfBirth:String , gender:String, password:String,phoneNo:String,
    onSuccess :  (message:String)->Unit , onFailure : (message:String)-> Unit)


    fun getUsers(onSuccess: (userLists: List<UserVO>) -> Unit,onFailure: (message: String) -> Unit)

}