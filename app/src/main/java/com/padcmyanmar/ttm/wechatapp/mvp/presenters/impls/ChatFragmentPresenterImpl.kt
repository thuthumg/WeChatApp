package com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.data.models.WeChatAppModelImpl
import com.padcmyanmar.ttm.wechatapp.data.vos.ChatHistoryVO
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.ChatFragmentPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.views.ChatFragmentView
import com.padcmyanmar.ttm.wechatapp.utils.mUserVO

class ChatFragmentPresenterImpl:ChatFragmentPresenter,
    AbstractBasePresenter<ChatFragmentView>(){

    private val mWeChatAppModel = WeChatAppModelImpl

    override fun onUiReady(context: Context, owner: LifecycleOwner) {
        mWeChatAppModel.getChatHistoryList(mUserVO.id.toString(),
        onSuccess = {
            mView.showChatMessageHistoryList(it)
        }, onFailure = {
            mView.showError(it)
            })


        mWeChatAppModel.getContacts(
            onSuccess = {
                mView.showContactsData(it)
            },
            onFailure = {
                mView.showError(it)
            }
        )
    }

    override fun goToChatDetailFromChatFragmentPage(mChatHistoryVO: ChatHistoryVO) {
        mView.navigateToChatDetailFromChatFragmentPage(mChatHistoryVO)
    }


}