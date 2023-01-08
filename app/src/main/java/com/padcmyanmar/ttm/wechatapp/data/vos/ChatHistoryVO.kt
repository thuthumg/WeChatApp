package com.padcmyanmar.ttm.wechatapp.data.vos

data class ChatHistoryVO (
    var chatUserId:String? = "",
    var chatUserName:String? = "",
    var chatUserProfileUrl:String? = "",
    var chatMsg:String?="",
    var chatTime:String?="")