package com.padcmyanmar.ttm.wechatapp.data.vos

class ChatGroupVO (
    var id:String? = "",
    var name:String? = "",
    var message:ArrayList<ChatMessageVO>? = arrayListOf(),
    var membersList:ArrayList<String>? = arrayListOf(),
    var photo:String? = ""

)