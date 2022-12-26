package com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.CreateNewMomentPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.views.CreateNewMomentView

class CreateNewMomentPresenterImpl:CreateNewMomentPresenter,
    AbstractBasePresenter<CreateNewMomentView>() {
    override fun onTapToChoosePhotoAndVideo() {
        mView.navigateToChoosePhotoAndVideo()
    }

    override fun onTapCreate() {
        mView.createFunction()
    }

    override fun onTapClose() {
        mView.closeFunction()
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }

    override fun goToChooseMediaType() {
       onTapToChoosePhotoAndVideo()
    }
}