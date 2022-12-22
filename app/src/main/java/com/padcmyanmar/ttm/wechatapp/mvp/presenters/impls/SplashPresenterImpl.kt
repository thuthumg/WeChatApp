package com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.SplashPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.views.SplashView

class SplashPresenterImpl: SplashPresenter, AbstractBasePresenter<SplashView>() {

    override fun onTapSignUp() {
       mView.navigateToSignUpScreen()
    }

    override fun onTapLogin() {
        mView.navigateToLoginScreen()
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }
}