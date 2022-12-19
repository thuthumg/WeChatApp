package com.padcmyanmar.ttm.wechatapp.mvp.presenters

import androidx.lifecycle.ViewModel
import com.padcmyanmar.ttm.wechatapp.mvp.views.BaseView

abstract class AbstractBasePresenter<T : BaseView> : BasePresenter<T>, ViewModel() {

    protected lateinit var mView: T

    override fun initPresenter(view: T) {
        mView = view
    }

}