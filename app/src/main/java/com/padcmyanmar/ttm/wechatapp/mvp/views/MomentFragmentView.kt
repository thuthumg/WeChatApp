package com.padcmyanmar.ttm.wechatapp.mvp.views

import com.padcmyanmar.ttm.wechatapp.data.vos.MomentVO

interface MomentFragmentView:BaseView {

    fun showMomentData(momentList:ArrayList<MomentVO>)

    fun favouriteFunction(momentVO: MomentVO)

    fun bookMarkFunction(momentVO: MomentVO)
}