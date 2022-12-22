package com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.OTPVerifyPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.views.OTPVerificationView

class OTPVerifyPresenterImpl:OTPVerifyPresenter, AbstractBasePresenter<OTPVerificationView>() {
    override fun onTapGetOTPCode() {
        mView.otpFunction()
    }

    override fun onTapVerify() {
        mView.verifyFunction()
    }

    override fun onTapBackFunction() {
        mView.navigateToBackFunction()
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }
}