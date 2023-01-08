package com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.data.models.WeChatAppModelImpl
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.AbstractBasePresenter

import com.padcmyanmar.ttm.wechatapp.mvp.presenters.ProfileFragmentPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.views.ProfileFragmentView
import com.padcmyanmar.ttm.wechatapp.utils.mUserVO


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

    override fun onPhotoTaken(bitmap: Bitmap, onSuccess: (returnUrlString: String) -> Unit) {



        mWeChatAppModel.uploadImageAndUpdateGrocery(mUserVO, bitmap, onSuccess = {
                it?.let {
                        it1 -> onSuccess(it1)
                }
            })

    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {
        mWeChatAppModel.getMomentDataByBookMarkList(
            onSuccess = {
                mView.showMomentData(it)
            },
            onFailure = {
                mView.showError(it)
            })
    }

}