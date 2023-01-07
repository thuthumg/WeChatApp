package com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.data.models.WeChatAppModelImpl
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.ContactsFragmentPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.views.ContactsFragmentView

class ContactsFragmentPresenterImpl:ContactsFragmentPresenter,
    AbstractBasePresenter<ContactsFragmentView>() {


    private val mWeChatAppModel = WeChatAppModelImpl


    override fun onTapAddContacts(
        userId: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mWeChatAppModel.addContacts(
            userId = userId,
            onSuccess={
                onSuccess(it)
            },
            onFailure = {
                onFailure(it)
            }
        )
    }

    override fun getContactsList(
        onSuccess: (contactsList: ArrayList<UserVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
       mWeChatAppModel.getContacts(
           onSuccess = {
               onSuccess(it)
           },
           onFailure = {
               onFailure(it)
           }
       )
    }

//    override fun getChatGroupsList(
//        onSuccess: (chatGroupList: ArrayList<ChatGroupVO>) -> Unit,
//        onFailure: (message: String) -> Unit
//    ) {
//        mWeChatAppModel.getChatGroupsList(onSuccess = {
//            mView.showChatGroupsList(it)
//        },
//        onFailure = {
//            mView.showError(it)
//        })
//    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {
        mWeChatAppModel.getContacts(
            onSuccess = {
                mView.showContactsData(it)
            },
            onFailure = {
                mView.showError(it)
            }
        )

        mWeChatAppModel.getChatGroupsList(
            onSuccess = {
                mView.showChatGroupsList(it)
            },
            onFailure = {
                mView.showError(it)
            }
        )
    }

    override fun goToCreateGroupChat(loginUserId: String) {
       mView.navigateToChatGroupCreate(loginUserId)
    }

    override fun goToChatDetailFromContactList(contactName: String, chatId: String) {
        mView.navigateToChatDetailFromContactPage(contactName,chatId)
    }

    override fun goToChatDetailFromContactGroupList(contactName: String, chatId: String,sParam:String) {
        mView.navigateToChatGroupDetailPage(contactName, chatId,sParam)
    }

    override fun onTapSelectContactList(contactName: UserVO) {

    }


}