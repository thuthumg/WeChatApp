package com.padcmyanmar.ttm.wechatapp.mvp.views

import com.padcmyanmar.ttm.wechatapp.data.vos.MomentVO

interface ProfileFragmentView:BaseView {

    fun onTapEditProfile(userName:String,
    dateOfBirth:String,
    genderType:String)

    fun showMomentData(mMomentVOList:ArrayList<MomentVO>)

    fun favouriteFunction(momentVO: MomentVO)

    fun bookMarkFunction(momentVO: MomentVO)

    fun editUserSuccess(msg:String)
}