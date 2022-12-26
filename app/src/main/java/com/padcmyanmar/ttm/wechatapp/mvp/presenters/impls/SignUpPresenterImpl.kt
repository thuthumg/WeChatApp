package com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.SignUpPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.views.SignUpView

class SignUpPresenterImpl:SignUpPresenter, AbstractBasePresenter<SignUpView>() {



    override fun onTapSignUp() {
        mView.signUpFunction()
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }

}