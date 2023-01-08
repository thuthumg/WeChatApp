package com.padcmyanmar.ttm.wechatapp.data.vos

data class ChatMessageVO (
    var file:String? = "",
    var message:String? = "",
    var name:String?="",
    var profileUrl:String?="",
    var timestamp: Long? = 0,
    var userId:String?="")