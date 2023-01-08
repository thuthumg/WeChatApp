package com.padcmyanmar.ttm.wechatapp.fragments

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
import com.padcmyanmar.ttm.wechatapp.data.vos.ChatGroupVO
import com.padcmyanmar.ttm.wechatapp.data.vos.ChatHistoryVO
import com.padcmyanmar.ttm.wechatapp.data.vos.ChatMessageVO
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.ChatFragmentPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls.ChatFragmentPresenterImpl
import com.padcmyanmar.ttm.wechatapp.mvp.views.ChatFragmentView
import com.padcmyanmar.ttm.wechatapp.utils.CHAT_TYPE_GROUP
import com.padcmyanmar.ttm.wechatapp.utils.CHAT_TYPE_PRIVATE
import com.padcmyanmar.ttm.wechatapp.utils.changeFromTimestampToDate
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment(),ChatFragmentView {

    lateinit var mPresenter: ChatFragmentPresenter

    lateinit var mActiveChatsListAdapter:ActiveChatsListAdapter
    lateinit var mChatsListAdapter:ChatsListAdapter
    lateinit var mGroupChatsListAdapter:ChatsListAdapter
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
        setUpGroupChatsListAdapter()
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
            ChatsListAdapter(mPresenter, CHAT_TYPE_PRIVATE)
        rvChatsList.adapter = mChatsListAdapter
        rvChatsList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        rvChatsList.isNestedScrollingEnabled = false
    }

    private fun setUpGroupChatsListAdapter() {
        mGroupChatsListAdapter =
            ChatsListAdapter(mPresenter, CHAT_TYPE_GROUP)
        rvGroupChatsList.adapter = mGroupChatsListAdapter
        rvGroupChatsList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        rvGroupChatsList.isNestedScrollingEnabled = false
    }



    override fun showChatMessageHistoryList(chatHistoryMessageList: List<ChatHistoryVO>) {
       mChatsListAdapter.setNewData(chatHistoryMessageList)
    }

    override fun navigateToChatDetailFromChatFragmentPage(
        chatHistoryVO: ChatHistoryVO,
        checkListType: String
    ) {

        when(checkListType)
        {
            CHAT_TYPE_PRIVATE->{
                startActivity(ChatDetailActivity.newIntent(
                    context = requireContext(),
                    chatUserName= chatHistoryVO.chatUserName.toString(),
                    chatUserId= chatHistoryVO.chatUserId.toString(),
                    chatUserProfile = chatHistoryVO.chatUserProfileUrl.toString(),
                    checkGroupOrPrivateChat = CHAT_TYPE_PRIVATE
                ))
            }
            CHAT_TYPE_GROUP->{
                startActivity(ChatDetailActivity.newIntent(
                    context = requireContext(),
                    chatUserName= chatHistoryVO.chatUserName.toString(),
                    chatUserId= chatHistoryVO.chatUserId.toString(),
                    chatUserProfile = chatHistoryVO.chatUserProfileUrl.toString(),
                    checkGroupOrPrivateChat = CHAT_TYPE_GROUP))
            }
        }


    }

    override fun showContactsData(contactsList: ArrayList<UserVO>) {
        mActiveChatsListAdapter.setNewData(contactsList)
    }

    override fun showGroupChatListData(groupChatHistoryListVO: ArrayList<ChatGroupVO>) {

        var chatHistoryMessageList: ArrayList<ChatHistoryVO> = arrayListOf()


        for (groupChatHistoryVO in groupChatHistoryListVO)
        {
            var mChatHistoryVO = ChatHistoryVO()

            mChatHistoryVO.chatUserId = groupChatHistoryVO.id
            mChatHistoryVO.chatUserName = groupChatHistoryVO.name
            mChatHistoryVO.chatUserProfileUrl = groupChatHistoryVO.profileUrl

            var sortedListData =  groupChatHistoryVO.message?.values?.toList()?.sortedByDescending{ msgVO: ChatMessageVO -> msgVO.timestamp }
            mChatHistoryVO.chatMsg =  sortedListData?.firstOrNull()?.message

            mChatHistoryVO.chatTime =   sortedListData?.firstOrNull()?.timestamp

                ?.let { changeFromTimestampToDate(it) }

            chatHistoryMessageList.add(mChatHistoryVO)

        }

        mGroupChatsListAdapter.setNewData(chatHistoryMessageList)
    }

    override fun showError(error: String) {

    }
}