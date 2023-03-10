package com.padcmyanmar.ttm.wechatapp.utils

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
const val REALTIME_DB_INSTANCE_PATH = "https://wechatapp-e0922-default-rtdb.asia-southeast1.firebasedatabase.app/"
const val USER_COLLECTION = "users"
const val MOMENTS_COLLECTION="moments"
const val CONTACTS_COLLECTION = "contacts"
const val GROUPS_COLLECTION = "groups"
const val CONTACTS_AND_MESSAGES_COLLECTION = "contacts&Messages"

const val USER_DOCUMENT_FIELD_UID = "id"
const val USER_DOCUMENT_FIELD_NAME = "name"
const val USER_DOCUMENT_FIELD_DATE_OF_BIRTH = "dateOfBirth"
const val USER_DOCUMENT_FIELD_GENDER_TYPE = "genderType"
const val USER_DOCUMENT_FIELD_PASSWORD = "password"
const val USER_DOCUMENT_FIELD_PHONE_NUM = "phoneNumber"
const val USER_DOCUMENT_FIELD_PROFILE_URL = "profileUrl"
const val USER_DOCUMENT_FIELD_LOGIN_ACTIVE_STATUS = "activeStatus"

const val MOMENT_DOCUMENT_FIELD_LIKED_ID = "likedId"
const val MOMENT_DOCUMENT_FIELD_BOOK_MARKED_ID = "bookMarkedId"
const val MOMENT_DOCUMENT_FIELD_TIMESTAMP = "timestamp"
const val MOMENT_DOCUMENT_FIELD_MEDIA_TYPE = "mediaType"
const val MOMENT_DOCUMENT_FIELD_MEDIA_DATA_LINK = "mediaDataLink"
const val MOMENT_DOCUMENT_FIELD_DESCRIPTION = "description"
const val MOMENT_DOCUMENT_PHOTO_VIDEO_LINK = "photoOrVideoUrlLink"


const val CHAT_MSG_DOCUMENT_FIELD_FILE = "file"
const val CHAT_MSG_DOCUMENT_FIELD_SEND_USER_NAME = "name"
const val CHAT_MSG_DOCUMENT_FIELD_MESSAGE = "message"
const val CHAT_MSG_DOCUMENT_FIELD_SEND_USER_PROFILE_URL = "profileUrl"
const val CHAT_MSG_DOCUMENT_FIELD_TIMESTAMP = "timestamp"
const val CHAT_MSG_DOCUMENT_FIELD_SEND_USER_ID = "userId"

//const val DOCUMENT_FIELD_PROFILE_URL_CONTACT = "profileImageUrl"

const val GROUP_DOCUMENT_FIELD_GROUP_NAME = "name"
const val GROUP_DOCUMENT_FIELD_GROUP_MESSAGE = "message"
const val GROUP_DOCUMENT_FIELD_GROUP_MEMBERS = "membersList"
const val GROUP_DOCUMENT_FIELD_GROUP_PROFILE_URL = "profileUrl"

const val CHAT_TYPE_GROUP = "Group"
const val CHAT_TYPE_PRIVATE = "PRIVATE"


var mUserVO: UserVO = UserVO()

// Array of Months acting as a data pump
var monthsDataList = arrayOf("Month",
    "January", "February", "March", "April", "May",
    "June", "July", "August", "September", "October", "November", "December"
)


var OTP_DEMO_ONE_DIGIT = "1111"
var OTP_DEMO_TWO_DIGIT = "1234"

const val VIEW_TYPE_ADD_MEDIA = 1
const val VIEW_TYPE_IMAGE = 2

const val VIEW_TYPE_ADD_GROUP = 1
const val VIEW_TYPE_GROUP = 2

const val VIEW_TYPE_SENDER = 1
const val VIEW_TYPE_RECEIVER = 2

const val VIEW_TYPE_GROUP_SELECT = 1
const val VIEW_TYPE_CONTACT = 2



fun isValidMobile(phone: String): Boolean {
    return if (!Pattern.matches("[a-zA-Z]+", phone)) {
        phone.length in 7..13
    } else false
}

fun Context.getFileExtension(uri: Uri): String? = when (uri.scheme) {
    // get file extension
    ContentResolver.SCHEME_FILE -> File(uri.path!!).extension
    // get actual name of file
    //ContentResolver.SCHEME_FILE -> File(uri.path!!).name
    ContentResolver.SCHEME_CONTENT -> getCursorContent(uri)
    else -> null
}

private fun Context.getCursorContent(uri: Uri): String? = try {
    contentResolver.query(uri, null, null, null, null)?.let { cursor ->
        cursor.run {
            val mimeTypeMap: MimeTypeMap = MimeTypeMap.getSingleton()
            if (moveToFirst()) mimeTypeMap.getExtensionFromMimeType(
                contentResolver.getType(
                    uri
                )
            )
            // case for get actual name of file
            //if (moveToFirst()) getString(getColumnIndex(OpenableColumns.DISPLAY_NAME))
            else null
        }.also { cursor.close() }
    }
} catch (e: Exception) {
    null
}



fun getFileExtensionFunc(context: Context,uri: Uri?): String? = when (uri?.scheme) {
    // get file extension
    ContentResolver.SCHEME_FILE -> File(uri.path!!).extension
    // get actual name of file
    //ContentResolver.SCHEME_FILE -> File(uri.path!!).name
    ContentResolver.SCHEME_CONTENT -> getCursorContent(context,uri)
    else -> null
}

@JvmName("getCursorContent1")
fun getCursorContent(context: Context, uri: Uri): String? = try {
    context.contentResolver.query(uri, null, null, null, null)?.let { cursor ->
        cursor.run {
            val mimeTypeMap: MimeTypeMap = MimeTypeMap.getSingleton()
            if (moveToFirst()) mimeTypeMap.getExtensionFromMimeType(
                context.contentResolver.getType(
                    uri
                )
            )
            // case for get actual name of file
            //if (moveToFirst()) getString(getColumnIndex(OpenableColumns.DISPLAY_NAME))
            else null
        }.also { cursor.close() }
    }
} catch (e: Exception) {
    null
}


fun covertTimeToText(dataDate: String?): String? {
    var convTime: String? = null
    val prefix = ""
    val suffix = "Ago"
    try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.US)
        val pasTime: Date = dateFormat.parse(dataDate)
        val nowTime = Date()
        val dateDiff: Long = nowTime.time - pasTime.time
        val second: Long = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
        val minute: Long = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
        val hour: Long = TimeUnit.MILLISECONDS.toHours(dateDiff)
        val day: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)
        if (second < 60) {
           // convTime = "$second Seconds $suffix"
            convTime = "Just Now"
        } else if (minute < 60) {
            convTime = "$minute Minutes $suffix"
        } else if (hour < 24) {
            convTime = "$hour Hours $suffix"
        } else if (day >= 7) {
            convTime = if (day > 360) {
                (day / 360).toString() + " Years " + suffix
            } else if (day > 30) {
                (day / 30).toString() + " Months " + suffix
            } else {
                (day / 7).toString() + " Week " + suffix
            }
        } else if (day < 7) {
            convTime = "$day Days $suffix"
        }
    } catch (e: ParseException) {
        e.printStackTrace()
        Log.e("ConvTimeE", e.message.toString())
    }
    return convTime
}

fun checkItem(arr: ArrayList<String>, item: String): Boolean {
    return arr.indexOf(item) != -1
}

var STORAGE_PERMISSIONS = arrayOf(
    Manifest.permission.CAMERA

)
fun changeFromTimestampToDate(timestamp:Long):String{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

     return   dateFormat.format(timestamp?.let { Date(it) })
}
/*  Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.VIBRATE*/
