package com.padcmyanmar.ttm.wechatapp.mvp.presenters

import com.padcmyanmar.ttm.wechatapp.mvp.views.SignUpView

interface SignUpPresenter:BasePresenter<SignUpView>  {

    fun onTapSignUp(
        name: String,
        dateOfBirth: String,
        gender: String,
        password: String,
        phoneNo: String,
        onSuccess: (message:String)-> Unit,
        onFailure:(message:String)-> Unit
    )
}