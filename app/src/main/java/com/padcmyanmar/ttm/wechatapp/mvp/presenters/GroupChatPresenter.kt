package com.padcmyanmar.ttm.wechatapp.mvp.presenters

import android.graphics.Bitmap
import android.net.Uri
import com.padcmyanmar.ttm.wechatapp.delegates.ContactListItemDelegate
import com.padcmyanmar.ttm.wechatapp.delegates.SelectedContactListItemDelegate
import com.padcmyanmar.ttm.wechatapp.mvp.views.GroupChatView

interface GroupChatPresenter:BasePresenter<GroupChatView>,ContactListItemDelegate,
    SelectedContactListItemDelegate {
    fun onTapChkContacts(
        userId:String,
        onSuccess: (message:String)-> Unit,
        onFailure: (message:String)-> Unit
    )
    fun onTapCreateChatGroup(groupName:String, membersList:ArrayList<String>, groupPhoto: String,
                             onSuccess: (message: String) -> Unit,
                             onFailure: (message: String) -> Unit)

    fun onPhotoTaken(bitmap : Bitmap, onSuccess: (returnUrlString: String)->Unit)
    fun uploadImageAndVideoFile(imageEncoded: Uri, onSuccess: (urlString:String) -> Unit)

}