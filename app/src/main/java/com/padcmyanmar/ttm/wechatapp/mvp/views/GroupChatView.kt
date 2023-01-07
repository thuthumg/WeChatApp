package com.padcmyanmar.ttm.wechatapp.mvp.views

import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO

interface GroupChatView:BaseView {
    fun showContactsData(contactsList:ArrayList<UserVO>)
   // fun selectContactItem(contactVO: UserVO)
    fun selectContactItem(contactName: String)
    fun onTapSelectContactList(contactVO:UserVO)
    fun onTapSelectContactCancel(contactVO:UserVO)
//    fun onTapCreateChatGroup(groupName:String,membersList:ArrayList<String>,groupPhoto:String,
//                             onSuccess: (message: String) -> Unit,
//                             onFailure: (message: String) -> Unit)
}