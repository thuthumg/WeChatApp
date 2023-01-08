package com.padcmyanmar.ttm.wechatapp.data.vos

data class UserVO(
    var id:String? = "",
    var name:String? = "",
    var dateOfBirth : String?= "",
    var genderType:String? = "",
    var password : String? = "",
    var phoneNumber:String? = "",
    var profileUrl:String? = "",
    var isSelected:Boolean = false,
    var activeStatus:String = "0"
)