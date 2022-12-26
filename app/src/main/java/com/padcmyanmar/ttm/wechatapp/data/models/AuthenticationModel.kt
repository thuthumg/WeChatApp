package com.padcmyanmar.ttm.wechatapp.data.models

import com.padcmyanmar.ttm.wechatapp.network.auth.AuthManager

interface AuthenticationModel {

    var mAuthManager: AuthManager

    fun getOTP(phoneNumber: String, onSuccess:()->Unit , onFailure:(String)-> Unit)

    fun verifyOTP(phoneNumber: String,otpCode:String, onSuccess: () -> Unit, onFailure: (String) -> Unit)

}