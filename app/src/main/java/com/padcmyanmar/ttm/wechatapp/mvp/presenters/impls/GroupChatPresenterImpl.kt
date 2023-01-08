package com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.data.models.WeChatAppModelImpl
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.GroupChatPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.views.GroupChatView
import com.padcmyanmar.ttm.wechatapp.utils.mUserVO

class GroupChatPresenterImpl:GroupChatPresenter,
AbstractBasePresenter<GroupChatView>(){

    private val mWeChatAppModel = WeChatAppModelImpl


    override fun goToChatDetailFromContactList(
        contactName: String,
        chatId: String,
        contactProfile: String
    ) {
      //  mView.selectContactItem(contactVO)
        mView.selectContactItem(contactName)
    }

    override fun goToChatDetailFromContactGroupList(
        contactName: String,
        chatId: String,
        contactProfile: String,
        CHAT_TYPE_GROUP: String
    ) {

    }

    override fun onTapSelectContactList(contactName: UserVO) {
        mView.onTapSelectContactList(contactName)
    }

    override fun onTapSelectContactCancel(contactVO: UserVO) {
       mView.onTapSelectContactCancel(contactVO)
    }

    override fun onTapChkContacts(
        userId: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {

    }

    override fun onTapCreateChatGroup(
        groupName: String,
        membersList: ArrayList<String>,
        groupPhoto: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mWeChatAppModel.createChatGroup(
            groupName = groupName,
            membersList = membersList,
            groupPhoto = groupPhoto,
            onSuccess = {
                mView.showError(it)
            }, onFailure = {
                mView.showError(it)
            }
        )
    }

    override fun onPhotoTaken(bitmap: Bitmap, onSuccess: (returnUrlString: String) -> Unit) {

        mWeChatAppModel.uploadImageAndUpdateGrocery(mUserVO, bitmap, onSuccess = {
            it?.let {
                    it1 -> onSuccess(it1)
            }
        })
    }

    override fun uploadImageAndVideoFile(
        imageEncoded: Uri,
        onSuccess: (urlString: String) -> Unit
    ) {
        mWeChatAppModel.uploadImageAndVideoFile(imageEncoded,  onSuccess = {

            it?.let { it1 -> onSuccess(it1) }
        })
    }


    override fun onUiReady(context: Context, owner: LifecycleOwner) {
        mWeChatAppModel.getContacts(
            onSuccess = {
                mView.showContactsData(it)
            },
            onFailure = {
                mView.showError(it)
            }
        )
    }

    override fun goToCreateGroupChat(loginUserId: String) {

    }
}