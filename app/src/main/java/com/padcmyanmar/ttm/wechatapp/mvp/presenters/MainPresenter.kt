package com.padcmyanmar.ttm.wechatapp.mvp.presenters

import com.padcmyanmar.ttm.wechatapp.mvp.views.MainView

interface MainPresenter:BasePresenter<MainView> {

    fun onTapCreateMoment()

    fun onTapMomentsFragment()


    fun onTapChatFragment()
    fun onTapContactsFragment()
    fun onTapMeFragment()
    fun onTapSettingFragment()
}