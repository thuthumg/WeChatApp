package com.padcmyanmar.ttm.wechatapp.mvp.presenters

import com.padcmyanmar.ttm.wechatapp.activities.LoginActivity
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.mvp.views.LoginView

interface LoginPresenter:BasePresenter<LoginView>{

    fun onTapLogin(loginActivity: LoginActivity,
                   phoneNo: String, password: String,
                   onSuccess: (String, UserVO)-> Unit, onFailure: (String) -> Unit)

    fun onTapBackFunction()

    fun goToMainPage(userVO: UserVO)
}