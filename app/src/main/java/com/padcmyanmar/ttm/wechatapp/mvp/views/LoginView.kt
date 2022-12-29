package com.padcmyanmar.ttm.wechatapp.mvp.views

import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO

interface LoginView:BaseView{

    fun loginFunction(userVO: UserVO)

    fun navigateToBackFunction()
}