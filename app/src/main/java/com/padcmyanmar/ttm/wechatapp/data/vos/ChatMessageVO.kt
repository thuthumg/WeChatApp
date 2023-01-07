package com.padcmyanmar.ttm.wechatapp.data.vos

import android.util.Log
import java.sql.Timestamp

class ChatMessageVO (
    var file:String? = "",
    var message:String? = "",
    var name:String?="",
    var profile_pic:String?="",
    var timestamp: Long? = 0,
    var user_id:String?="")