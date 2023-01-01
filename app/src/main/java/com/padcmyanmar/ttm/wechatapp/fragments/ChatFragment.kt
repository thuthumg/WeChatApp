package com.padcmyanmar.ttm.wechatapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.activities.ChatDetailActivity
import com.padcmyanmar.ttm.wechatapp.adapters.ActiveChatsListAdapter
import com.padcmyanmar.ttm.wechatapp.adapters.ChatsListAdapter
import com.padcmyanmar.ttm.wechatapp.delegates.ChatsListDelegate
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment(),ChatsListDelegate {

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
        setUpChatActiveAdapter()
        setUpChatListAdapter()
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
            ChatsListAdapter(this)
        rvChatsList.adapter = mChatsListAdapter
        rvChatsList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        rvChatsList.isNestedScrollingEnabled = false
    }

    override fun goToChatDetail() {
        startActivity(Intent(context, ChatDetailActivity::class.java))
    }
}