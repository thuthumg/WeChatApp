package com.padcmyanmar.ttm.wechatapp.mvp.presenters

import com.padcmyanmar.ttm.wechatapp.delegates.MomentItemDelegate
import com.padcmyanmar.ttm.wechatapp.mvp.views.MomentFragmentView

interface MomentFragmentPresenter :BasePresenter<MomentFragmentView>, MomentItemDelegate {

}