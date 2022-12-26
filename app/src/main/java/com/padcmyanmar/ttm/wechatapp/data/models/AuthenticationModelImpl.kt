package com.padcmyanmar.ttm.wechatapp.data.models

import com.padcmyanmar.ttm.wechatapp.network.auth.AuthManager
import com.padcmyanmar.ttm.wechatapp.network.auth.PhoneAuthActivity

object AuthenticationModelImpl : AuthenticationModel {
    override var mAuthManager: AuthManager = PhoneAuthActivity


    override fun getOTP(phoneNumber: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {

        mAuthManager.getOTP(phoneNumber,onSuccess,onFailure)
    }

    override fun verifyOTP(
        phoneNumber: String,
        otpCode: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        mAuthManager.verifyOTP(phoneNumber,otpCode, onSuccess = {
            onSuccess
        },
        onFailure = {
            onFailure(it)
        })
    }
}