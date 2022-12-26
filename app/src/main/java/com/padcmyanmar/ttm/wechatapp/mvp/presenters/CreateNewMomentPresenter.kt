package com.padcmyanmar.ttm.wechatapp.mvp.presenters

import com.padcmyanmar.ttm.wechatapp.delegates.MediaTypeDataDelegate
import com.padcmyanmar.ttm.wechatapp.mvp.views.CreateNewMomentView

interface CreateNewMomentPresenter : BasePresenter<CreateNewMomentView>, MediaTypeDataDelegate {

    fun onTapToChoosePhotoAndVideo()

    fun onTapCreate()

    fun onTapClose()

}