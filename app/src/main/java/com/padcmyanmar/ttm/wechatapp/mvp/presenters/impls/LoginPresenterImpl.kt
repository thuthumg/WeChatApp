package com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.activities.LoginActivity
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.LoginPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.views.LoginView

class LoginPresenterImpl:LoginPresenter,AbstractBasePresenter<LoginView>() {
    override fun onTapLogin(loginActivity: LoginActivity, phoneNo: String, password: String) {
       mView.loginFunction(phoneNo, password)
    }

    override fun onTapBackFunction() {
        mView.navigateToBackFunction()
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }


}