package com.padcmyanmar.ttm.wechatapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.activities.ChatDetailActivity
import com.padcmyanmar.ttm.wechatapp.adapters.ActiveChatsListAdapter
import com.padcmyanmar.ttm.wechatapp.adapters.ChatsListAdapter
import com.padcmyanmar.ttm.wechatapp.data.vos.ChatHistoryVO
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.ChatFragmentPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls.ChatFragmentPresenterImpl
import com.padcmyanmar.ttm.wechatapp.mvp.views.ChatFragmentView
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment(),ChatFragmentView {

    lateinit var mPresenter: ChatFragmentPresenter

    lateinit var mActiveChatsListAdapter:ActiveChatsListAdapter
    lateinit var mChatsListAdapter:ChatsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       setUpPresenter()
        setUpChatActiveAdapter()
        setUpChatListAdapter()

        context?.let { mPresenter.onUiReady(it,this) }
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ChatFragmentPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpChatActiveAdapter() {
        mActiveChatsListAdapter =
            ActiveChatsListAdapter()
        rvActiveChatsList.adapter = mActiveChatsListAdapter
        rvActiveChatsList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )
        rvActiveChatsList.isNestedScrollingEnabled = false
    }

    private fun setUpChatListAdapter() {
        mChatsListAdapter =
            ChatsListAdapter(mPresenter)
        rvChatsList.adapter = mChatsListAdapter
        rvChatsList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        rvChatsList.isNestedScrollingEnabled = false
    }



    override fun showChatMessageHistoryList(chatHistoryMessageList: List<ChatHistoryVO>) {
        Log.d("chatfragment","message list size ${chatHistoryMessageList.size}")
       mChatsListAdapter.setNewData(chatHistoryMessageList)
    }

    override fun navigateToChatDetailFromChatFragmentPage(chatHistoryVO: ChatHistoryVO) {

        startActivity(ChatDetailActivity.newIntent(
            context = requireContext(),
            chatUserName= chatHistoryVO.chatUserName.toString(),
            chatUserId= chatHistoryVO.chatUserId.toString(),
            checkGroupOrPrivateChat = "Private"
        ))

    }

    override fun showContactsData(contactsList: ArrayList<UserVO>) {
        mActiveChatsListAdapter.setNewData(contactsList)
    }

    override fun showError(error: String) {

    }
}