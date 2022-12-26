package com.padcmyanmar.ttm.wechatapp.utils

import java.util.regex.Pattern

// Array of Months acting as a data pump
var monthsDataList = arrayOf("Month",
    "January", "February", "March", "April", "May",
    "June", "July", "August", "September", "October", "November", "December"
)


var OTP_DEMO_ONE_DIGIT = "1111"
var OTP_DEMO_TWO_DIGIT = "1234"

const val VIEW_TYPE_ADD_MEDIA = 1
const val VIEW_TYPE_IMAGE = 2

fun isValidMobile(phone: String): Boolean {
    return if (!Pattern.matches("[a-zA-Z]+", phone)) {
        phone.length in 7..13
    } else false
}