package com.padcmyanmar.ttm.wechatapp.mvp.presenters

import com.padcmyanmar.ttm.wechatapp.mvp.views.SignUpView

interface SignUpPresenter:BasePresenter<SignUpView>  {

    fun onTapSignUp()
}