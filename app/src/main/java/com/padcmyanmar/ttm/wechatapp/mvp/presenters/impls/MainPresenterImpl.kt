package com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.data.models.WeChatAppModelImpl
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.MainPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.views.MainView

class MainPresenterImpl : MainPresenter, AbstractBasePresenter<MainView>() {





    override fun onTapCreateMoment() {
        mView.navigateToCreateMoment()
    }

    override fun onTapMomentsFragment() {
        mView.navigateToMomentsFragment()
    }

    override fun onTapChatFragment() {
        mView.navigateToChatFragment()
    }

    override fun onTapContactsFragment() {
        mView.navigateToContactsFragment()
    }

    override fun onTapMeFragment() {
        mView.navigateToMeFragment()
    }

    override fun onTapSettingFragment() {
        mView.navigateToSettingFragment()
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {


    }
}