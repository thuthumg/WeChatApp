package com.padcmyanmar.ttm.wechatapp.data.vos

data class ContactsListVO (
    var nameFirstCharacter:String?="",
    var usersList:List<UserVO>?= listOf()
 )
