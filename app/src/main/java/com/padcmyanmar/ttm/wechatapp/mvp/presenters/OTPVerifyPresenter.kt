package com.padcmyanmar.ttm.wechatapp.mvp.presenters

import com.padcmyanmar.ttm.wechatapp.mvp.views.OTPVerificationView

interface OTPVerifyPresenter:BasePresenter<OTPVerificationView> {


    fun onTapGetOTPCode(phoneNumber: String)

    fun onTapVerify(phoneNumber: String,otpCode:String)

    fun onTapBackFunction()
}