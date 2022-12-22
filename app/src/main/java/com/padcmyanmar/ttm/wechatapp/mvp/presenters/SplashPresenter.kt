package com.padcmyanmar.ttm.wechatapp.mvp.presenters

import com.padcmyanmar.ttm.wechatapp.mvp.views.SplashView

interface SplashPresenter:BasePresenter<SplashView> {

 fun onTapSignUp()
 fun onTapLogin()

}