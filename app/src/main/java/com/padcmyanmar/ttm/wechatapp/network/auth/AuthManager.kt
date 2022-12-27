package com.padcmyanmar.ttm.wechatapp.network.auth

import android.app.Activity
import com.google.firebase.auth.FirebaseUser

interface AuthManager {
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

    fun getCurrentUser(): FirebaseUser?
}