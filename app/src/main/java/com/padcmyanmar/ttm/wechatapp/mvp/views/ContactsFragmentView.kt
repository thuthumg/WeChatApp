package com.padcmyanmar.ttm.wechatapp.mvp.views

import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO

interface ContactsFragmentView:BaseView {

    fun showContactsData(contactsList:ArrayList<UserVO>)

    fun navigateToChatDetailFromContactPage(contactName:UserVO)
}