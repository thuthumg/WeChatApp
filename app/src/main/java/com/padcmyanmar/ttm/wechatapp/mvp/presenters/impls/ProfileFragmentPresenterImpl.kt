package com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.data.models.WeChatAppModelImpl
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.AbstractBasePresenter

import com.padcmyanmar.ttm.wechatapp.mvp.presenters.ProfileFragmentPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.views.ProfileFragmentView


class ProfileFragmentPresenterImpl:ProfileFragmentPresenter,AbstractBasePresenter<ProfileFragmentView>(){

    private val mWeChatAppModel = WeChatAppModelImpl

    override fun onTapEditUser(
        userName: String,
        dateOfBirth: String,
        genderType: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mWeChatAppModel.editUser(userName,dateOfBirth,genderType, onSuccess = {
            onSuccess(it)
        }, onFailure = {
            onFailure(it)
        })
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }

}