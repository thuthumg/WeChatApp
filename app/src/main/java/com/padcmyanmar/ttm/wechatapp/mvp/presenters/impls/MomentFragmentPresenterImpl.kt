package com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.data.models.WeChatAppModelImpl
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.MomentFragmentPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.views.MomentFragmentView

class MomentFragmentPresenterImpl : MomentFragmentPresenter, AbstractBasePresenter<MomentFragmentView>(){
    private val mWeChatAppModel = WeChatAppModelImpl

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

        mWeChatAppModel.getMomentData(
            onSuccess = {
                mView.showMomentData(it)
            },
            onFailure = {
                mView.showError(it)
            })
    }

    override fun onTapFavorite(id: String?,likeUserPhoneNumber:String?) {
        mView.favouriteFunction(id,likeUserPhoneNumber)
    }

    override fun onTapSavePost() {

    }
}