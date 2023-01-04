package com.padcmyanmar.ttm.wechatapp.mvp.views

interface ProfileFragmentView:BaseView {

    fun onTapEditProfile(userName:String,
    dateOfBirth:String,
    genderType:String)
}