package com.padcmyanmar.ttm.wechatapp.mvp.views

import com.padcmyanmar.ttm.wechatapp.data.vos.MomentVO

interface MainView:BaseView {

    fun navigateToCreateMoment()
    fun navigateToMomentsFragment()
    fun navigateToChatFragment()
    fun navigateToContactsFragment()
    fun navigateToMeFragment()
    fun navigateToSettingFragment()


}