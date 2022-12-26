package com.padcmyanmar.ttm.wechatapp.network.auth

interface AuthManager {

    fun getOTP(phoneNumber:String , onSuccess:()->Unit,onFailure:(String)->Unit)

    fun verifyOTP(phoneNumber:String , otpCode :String, onSuccess:()->Unit,onFailure:(String)->Unit)
}