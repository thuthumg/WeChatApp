package com.padcmyanmar.ttm.wechatapp.delegates

import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO

interface ContactListItemDelegate {

    fun goToChatDetailFromContactList(contactName: UserVO)
}