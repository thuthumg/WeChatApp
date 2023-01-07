package com.padcmyanmar.ttm.wechatapp.mvp.presenters

import com.padcmyanmar.ttm.wechatapp.data.vos.ChatGroupVO
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.delegates.ContactListItemDelegate
import com.padcmyanmar.ttm.wechatapp.mvp.views.ContactsFragmentView

interface ContactsFragmentPresenter:BasePresenter<ContactsFragmentView>, ContactListItemDelegate {

    fun onTapAddContacts(
        userId:String,
        onSuccess: (message:String)-> Unit,
        onFailure: (message:String)-> Unit
    )

    fun getContactsList(
        onSuccess: (contactsList: ArrayList<UserVO>) -> Unit,
        onFailure: (message: String) -> Unit
    )

//    fun getChatGroupsList(onSuccess:(chatGroupList:ArrayList<ChatGroupVO>)->Unit,
//                          onFailure: (message: String) -> Unit)
}