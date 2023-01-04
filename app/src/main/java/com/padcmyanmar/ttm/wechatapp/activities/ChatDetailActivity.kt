package com.padcmyanmar.ttm.wechatapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.padcmyanmar.ttm.wechatapp.R

import com.padcmyanmar.ttm.wechatapp.adapters.ChatDetailListAdapter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.ChatDetailPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls.ChatDetailPresenterImpl

import com.padcmyanmar.ttm.wechatapp.mvp.views.ChatDetailView
import com.padcmyanmar.ttm.wechatapp.network.auth.PhoneAuth
import kotlinx.android.synthetic.main.activity_chat_detail.*
import kotlinx.android.synthetic.main.content_chat_detail_layout.*


class ChatDetailActivity : BaseActivity(),ChatDetailView {

    lateinit var mPresenter: ChatDetailPresenter
    lateinit var mChatDetailListAdapter: ChatDetailListAdapter
    var mChatUserId = ""
    var mChatUserPhoneNo = ""

    companion object {
        private const val BUNDLE_CHAT_USER_NAME = "BUNDLE_CHAT_USER_NAME"
        private const val BUNDLE_CHAT_USER_ID = "BUNDLE_CHAT_USER_ID"
        private const val BUNDLE_CHAT_USER_PHONE_NUM = "BUNDLE_CHAT_USER_PHONE_NUM"

        fun newIntent(
            context: Context,
            chatUserName: String,
            chatUserId: String,
            chatUserPhoneNum: String
        ): Intent {
            val intent = Intent(context, ChatDetailActivity::class.java)
            intent.putExtra(BUNDLE_CHAT_USER_NAME, chatUserName)
            intent.putExtra(BUNDLE_CHAT_USER_ID, chatUserId)
            intent.putExtra(BUNDLE_CHAT_USER_PHONE_NUM, chatUserPhoneNum)
            return intent
        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_detail)

        //  setUpBottomButtonLayout()
        setUpPresenter()
        getIntentData()
        setUpMessageAdapter()
        clickListener()
        mPresenter.onUiReady(this,this)
    }

    private fun clickListener() {
        ivSendMessage.setOnClickListener {
            hideKeyboard(this)
            mPresenter.onTapSendMsg(
                senderId=PhoneAuth.getCurrentUser()?.uid.toString(),
                receiverId= mChatUserId,
                msg= edtTypeMsg.text.toString(),
                senderName= PhoneAuth.getCurrentUser()?.displayName.toString(),
                onSuccess = {
                            showError(it)
                },
            onFailure={
                showError(it)
            }

            )
        }
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ChatDetailPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun getIntentData() {
        tvChatName.text = intent?.getStringExtra(BUNDLE_CHAT_USER_NAME).toString()
        mChatUserId = intent?.getStringExtra(BUNDLE_CHAT_USER_ID).toString()
        mChatUserPhoneNo = intent?.getStringExtra(BUNDLE_CHAT_USER_PHONE_NUM).toString()
    }


    private fun setUpMessageAdapter() {
        mChatDetailListAdapter =
            ChatDetailListAdapter()
        rvChatDetailList.adapter = mChatDetailListAdapter
        rvChatDetailList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        rvChatDetailList.isNestedScrollingEnabled = false
    }

    private fun setUpBottomButtonLayout() {


        //Add custom tab menu
        /*val galleryView = bottomNavigationChat.getChildAt(0) as BottomNavigationMenuView

        val positionView1 = galleryView.getChildAt(1)
       val cameraView = positionView1 as BottomNavigationItemView

        val positionView2 = galleryView.getChildAt(2)
        val gifView = positionView2 as BottomNavigationItemView

        val positionView3 = galleryView.getChildAt(3)
        val locationView = positionView3 as BottomNavigationItemView


        val positionView4 = galleryView.getChildAt(4)
        val microphoneView = positionView4 as BottomNavigationItemView

        val viewCustom1 = LayoutInflater.from(this).inflate(R.layout.bottom_gallery, galleryView, false)
        bottomNavigationChat.addView(viewCustom1)

        val viewCustom2 = LayoutInflater.from(this).inflate(R.layout.bottom_camera, positionView1, false)
        cameraView.addView(viewCustom2)

        val viewCustom3 = LayoutInflater.from(this).inflate(R.layout.bottom_gif, positionView2, false)
        gifView.addView(viewCustom3)

        val viewCustom4 = LayoutInflater.from(this).inflate(R.layout.bottom_location, positionView3, false)
        locationView.addView(viewCustom4)

        val viewCustom5 = LayoutInflater.from(this).inflate(R.layout.bottom_microphone, positionView4, false)
        microphoneView.addView(viewCustom5)*/
    }
//
//    override fun onTapSendMessage(
//        senderId: String,
//        receiverId: String,
//        msg: String,
//        senderName: String
//    ) {
//
//    }

    override fun showError(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }
}