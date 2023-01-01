package com.padcmyanmar.ttm.wechatapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.adapters.ActiveChatsListAdapter
import com.padcmyanmar.ttm.wechatapp.adapters.ChatDetailListAdapter
import kotlinx.android.synthetic.main.content_chat_detail_layout.*
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatDetailActivity : AppCompatActivity() {

    lateinit var mChatDetailListAdapter: ChatDetailListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_detail)

        setUpBottomNavigation()
        setUpMessageAdapter()
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

    private fun setUpBottomNavigation() {

//        val appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)
//        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)
//        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)
//
//        val appBarConfiguration = AppBarConfiguration(setOf(
//            R.id.navigation_gallery, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
       // bottomNavigationChat.itemIconTintList = null;
        //Add custom tab menu
        val galleryView = bottomNavigationChat.getChildAt(0) as BottomNavigationMenuView

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
        microphoneView.addView(viewCustom5)
    }
}