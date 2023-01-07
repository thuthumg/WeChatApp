package com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.data.models.WeChatAppModelImpl
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.GroupChatPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.views.GroupChatView

class GroupChatPresenterImpl:GroupChatPresenter,
AbstractBasePresenter<GroupChatView>(){

    private val mWeChatAppModel = WeChatAppModelImpl


    override fun goToChatDetailFromContactList(contactName: String, chatId: String) {
      //  mView.selectContactItem(contactVO)
        mView.selectContactItem(contactName)
    }

    override fun goToChatDetailFromContactGroupList(
        contactName: String,
        chatId: String,
        sParam: String
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
            },
            onFailure = {
                mView.showError(it)
            }
        )
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