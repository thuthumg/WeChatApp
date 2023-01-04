package com.padcmyanmar.ttm.wechatapp.mvp.presenters

import com.padcmyanmar.ttm.wechatapp.mvp.views.ProfileFragmentView

interface ProfileFragmentPresenter:BasePresenter<ProfileFragmentView> {

    fun onTapEditUser(userName:String,
    dateOfBirth:String,
    genderType:String,
    onSuccess: (message:String)-> Unit,
    onFailure: (message:String)-> Unit)
}