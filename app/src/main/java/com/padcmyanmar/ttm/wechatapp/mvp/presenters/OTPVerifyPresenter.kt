package com.padcmyanmar.ttm.wechatapp.mvp.presenters

import com.padcmyanmar.ttm.wechatapp.mvp.views.OTPVerificationView

interface OTPVerifyPresenter:BasePresenter<OTPVerificationView> {

    fun onTapGetOTPCode()

    fun onTapVerify()

    fun onTapBackFunction()
}