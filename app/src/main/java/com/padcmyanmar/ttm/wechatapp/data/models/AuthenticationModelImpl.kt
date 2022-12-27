package com.padcmyanmar.ttm.wechatapp.data.models

import android.app.Activity
import com.padcmyanmar.ttm.wechatapp.network.auth.AuthManager
import com.padcmyanmar.ttm.wechatapp.network.auth.PhoneAuth
import com.padcmyanmar.ttm.wechatapp.network.auth.PhoneAuthActivity

object AuthenticationModelImpl : AuthenticationModel {
    override var mAuthManager: AuthManager = PhoneAuth


    override fun getOTP(context: Activity, phoneNumber: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {

        mAuthManager.getOTP(context,phoneNumber,onSuccess,onFailure)
    }

    override fun verifyOTP(
        context: Activity,
        phoneNumber: String,
        otpCode: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        mAuthManager.verifyOTP(context , phoneNumber,otpCode, onSuccess = {
            onSuccess
        },
        onFailure = {
            onFailure(it)
        })
    }
}