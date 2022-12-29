package com.padcmyanmar.ttm.wechatapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.fragments.*
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.MainPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls.MainPresenterImpl
import com.padcmyanmar.ttm.wechatapp.mvp.views.MainView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main_layout.*

class MainActivity : BaseActivity(), MainView {


    private lateinit var mPresenter: MainPresenter
    private var phoneNumber:String? = ""
    private var userName:String? = ""
    companion object{
        private const val  BUNDLE_PHONE_NUMBER = "BUNDLE_PHONE_NUMBER"
        private const val  BUNDLE_USER_NAME = "BUNDLE_USER_NAME"

        fun newIntent(context: Context, phoneNum:String, userName:String):Intent{
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(BUNDLE_PHONE_NUMBER, phoneNum)
            intent.putExtra(BUNDLE_USER_NAME, userName)
            return intent
        }
    }
    private fun getIntentParam() {

        phoneNumber = intent?.getStringExtra(BUNDLE_PHONE_NUMBER).toString()
        userName = intent?.getStringExtra(BUNDLE_USER_NAME).toString()


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpPresenter()
        mPresenter.onUiReady(this, this)
        getIntentParam()
        setUpBottomNavUI()
        clickListener()

    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<MainPresenterImpl, MainView>()
    }

    private fun clickListener() {
        mcvCreateMoment.setOnClickListener {
            mPresenter.onTapCreateMoment()
        }
    }

    private fun setUpBottomNavUI() {

        val fragment = MomentsFragment()
        createFragmentFunction(fragment)

        bottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.action_moment -> {
                    mPresenter.onTapMomentsFragment()
                    return@setOnItemSelectedListener true
                }
                R.id.action_chat -> {
                    mPresenter.onTapChatFragment()

                    return@setOnItemSelectedListener true
                }
                R.id.action_contacts -> {
                    mPresenter.onTapContactsFragment()

                    return@setOnItemSelectedListener true
                }
                R.id.action_me -> {

                    mPresenter.onTapMeFragment()
                    return@setOnItemSelectedListener true
                }
                R.id.action_setting -> {

                    mPresenter.onTapSettingFragment()
                    return@setOnItemSelectedListener true
                }

            }
            false

        }
    }

    override fun navigateToCreateMoment() {
        startActivity(phoneNumber?.let {phoneNumber->
            userName?.let {userName->
                CreateNewMomentActivity.newIntent(this@MainActivity,
                    phoneNum = phoneNumber,
                    userName = userName
                )
            }
        })

    }

    override fun navigateToMomentsFragment() {
        val fragment = MomentsFragment()
        createFragmentFunction(fragment)
    }


    override fun navigateToChatFragment() {
        val fragment = ChatFragment()
        createFragmentFunction(fragment)
    }

    override fun navigateToContactsFragment() {
        val fragment = ContactsFragment()
        createFragmentFunction(fragment)
    }

    override fun navigateToMeFragment() {
        val fragment = ProfileFragment()
        createFragmentFunction(fragment)
    }

    override fun navigateToSettingFragment() {
        val fragment = SettingFragment()
        createFragmentFunction(fragment)
    }


    private fun createFragmentFunction(fragmentParam:Fragment){
        supportFragmentManager.beginTransaction().replace(
            R.id.flFragment, fragmentParam, fragmentParam.javaClass.simpleName
        ).commit()
    }
}