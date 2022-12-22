package com.padcmyanmar.ttm.wechatapp.mvp.views

interface LoginView:BaseView{

    fun loginFunction(phoneNo: String, password: String)

    fun navigateToBackFunction()
}