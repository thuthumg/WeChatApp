package com.padcmyanmar.ttm.wechatapp.delegates

import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO

interface SelectedContactListItemDelegate {

    fun onTapSelectContactCancel(contactVO:UserVO)
}