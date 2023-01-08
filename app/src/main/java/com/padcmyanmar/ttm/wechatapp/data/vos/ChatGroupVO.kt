package com.padcmyanmar.ttm.wechatapp.data.vos

class ChatGroupVO(
    var id:String? = "",
    var name:String? = "",
    var message:Map<String,ChatMessageVO>? = hashMapOf(),
    var membersList:ArrayList<String>? = arrayListOf(),
    var profileUrl:String? = ""
)
