package com.padcmyanmar.ttm.wechatapp.network

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
        onSuccess: (usersList: List<UserVO>) -> Unit,
        onFailure: (message: String) -> Unit
    )
}