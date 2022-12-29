package com.padcmyanmar.ttm.wechatapp.delegates

interface MomentItemDelegate {
    fun onTapFavorite(id: String?,likeUserPhoneNumber:String?)

    fun onTapSavePost()
}