package com.padcmyanmar.ttm.wechatapp.delegates

import com.padcmyanmar.ttm.wechatapp.data.vos.MomentVO

interface MomentItemDelegate {
    fun onTapFavorite(momentVO: MomentVO)

    fun onTapSavePost()
}