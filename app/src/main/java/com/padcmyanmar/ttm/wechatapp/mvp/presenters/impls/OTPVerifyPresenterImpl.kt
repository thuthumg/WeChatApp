package com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.data.models.AuthenticationModel
import com.padcmyanmar.ttm.wechatapp.data.models.AuthenticationModelImpl
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.OTPVerifyPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.views.OTPVerificationView

class OTPVerifyPresenterImpl:OTPVerifyPresenter, AbstractBasePresenter<OTPVerificationView>() {


    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpl

    override fun onTapGetOTPCode(phoneNumber: String) {

        mAuthenticationModel.getOTP(phoneNumber,
            onSuccess = {
                mView.otpFunction()
            }, onFailure = {
                mView.showError(it)
            })

        //
    }

    override fun onTapVerify(phoneNumber: String,otpCode:String) {
        //  mView.verifyFunction()
        mAuthenticationModel.verifyOTP(phoneNumber = phoneNumber,
            otpCode = otpCode, onSuccess = {
                mView.verifyFunction()
            }, onFailure = {
                mView.showError(it)
            })
    }

    override fun onTapBackFunction() {
        mView.navigateToBackFunction()
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }
}