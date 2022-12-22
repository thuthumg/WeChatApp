package com.padcmyanmar.ttm.wechatapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Profile
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.adapters.MomentsListAdapter
import com.padcmyanmar.ttm.wechatapp.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main_layout.*
import kotlinx.android.synthetic.main.fragment_moments.*
import kotlinx.android.synthetic.main.view_holder_moment_item.*

class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpBottomNavUI()
        clickListener()

    }

    private fun clickListener() {
        mcvCreateMoment.setOnClickListener {
            startActivity(Intent(this@MainActivity,CreateNewMomentActivity::class.java))
        }
    }

    private fun setUpBottomNavUI() {
        val fragment = MomentsFragment()
        supportFragmentManager.beginTransaction().replace(R.id.flFragment, fragment, fragment.javaClass.simpleName)
            .commit()

        bottomNavigation.setOnItemSelectedListener {

            when(it.itemId){
                R.id.action_moment -> {
                    val fragment = MomentsFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.flFragment,fragment,fragment.javaClass.simpleName
                    ).commit()

                    return@setOnItemSelectedListener true
                }
                R.id.action_chat -> {
                    val fragment = ChatFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.flFragment,fragment,fragment.javaClass.simpleName
                    ).commit()

                    return@setOnItemSelectedListener true
                }
                R.id.action_contacts -> {
                    val fragment = ContactsFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.flFragment,fragment,fragment.javaClass.simpleName
                    ).commit()

                    return@setOnItemSelectedListener true
                }
                R.id.action_me -> {
                    val fragment = ProfileFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.flFragment,fragment,fragment.javaClass.simpleName
                    ).commit()

                    return@setOnItemSelectedListener true
                }
                R.id.action_setting -> {
                    val fragment = SettingFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.flFragment,fragment,fragment.javaClass.simpleName
                    ).commit()

                    return@setOnItemSelectedListener true
                }

            }
            false

        }
    }
}