package com.padcmyanmar.ttm.wechatapp.data.models

import android.app.Activity
import com.padcmyanmar.ttm.wechatapp.network.auth.AuthManager

interface AuthenticationModel {

    var mAuthManager: AuthManager

    fun getOTP(
        context: Activity,
        phoneNumber: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun verifyOTP(
        context: Activity,
        phoneNumber: String,
        otpCode: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
}