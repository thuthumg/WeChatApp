package com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.data.models.WeChatAppModelImpl
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.SignUpPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.views.SignUpView

class SignUpPresenterImpl:SignUpPresenter, AbstractBasePresenter<SignUpView>() {

    private val mWeChatModel = WeChatAppModelImpl

    override fun onTapSignUp(
        name: String,
        dateOfBirth: String,
        gender: String,
        password: String,
        phoneNo: String,
        onSuccess: (message:String)-> Unit,
        onFailure:(message:String)-> Unit
    ) {
        mWeChatModel.addUser(
            name,
            dateOfBirth,
            gender,
            password,
            phoneNo,
            onSuccess = {
                onSuccess(it)
                mView.signUpFunction()
            },
            onFailure = {
                onFailure(it)
            }
        )
        // mView.signUpFunction()


    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }

}