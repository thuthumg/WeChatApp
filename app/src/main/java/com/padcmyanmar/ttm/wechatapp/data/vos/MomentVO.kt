package com.padcmyanmar.ttm.wechatapp.data.vos

class MomentVO(
    var id:String? = "",
    var name: String? = "",
    var phoneNumber: String? = "",
    var description: String? = "",
    var timestamp: String? = "",
    var photoOrVideoUrlLink: ArrayList<MediaDataVO>? = arrayListOf(),
    var likedId: ArrayList<String> ?= arrayListOf()
    //var timestampField: Long? = 0
)