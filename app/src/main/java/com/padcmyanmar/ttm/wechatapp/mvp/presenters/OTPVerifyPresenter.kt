package com.padcmyanmar.ttm.wechatapp.mvp.presenters

import android.app.Activity
import com.padcmyanmar.ttm.wechatapp.mvp.views.OTPVerificationView

interface OTPVerifyPresenter:BasePresenter<OTPVerificationView> {


    fun onTapGetOTPCode(context: Activity, phoneNumber: String)

    fun onTapVerify(context: Activity,phoneNumber: String,otpCode:String)

    fun onTapBackFunction()
}