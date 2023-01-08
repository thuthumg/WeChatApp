package com.padcmyanmar.ttm.wechatapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
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
    private var dateOfBirth:String? = ""
    private var gender:String? = ""
    private var qrCode:String? = ""
    private var profileImageUrl:String? = ""
    private var REQUEST_CODE_QR_SCAN = 101
    lateinit var userVO:UserVO
    companion object{
       const val  BUNDLE_PHONE_NUMBER = "BUNDLE_PHONE_NUMBER"
        const val  BUNDLE_USER_NAME = "BUNDLE_USER_NAME"
        const val  BUNDLE_DATE_OF_BIRTH = "BUNDLE_DATE_OF_BIRTH"
        const val  BUNDLE_GENDER_TYPE = "BUNDLE_GENDER_TYPE"
        const val  BUNDLE_QR_CODE = "BUNDLE_QR_CODE"
        const val BUNDLE_PROFILE_IMG_URL = "BUNDLE_PROFILE_IMG_URL"

        fun newIntent(
            context: Context,
            phoneNum: String,
            userName: String,
            dateOfBirth: String,
            gender: String,
            qrCode: String?,
            profileImageUrl: String?
        ):Intent{
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(BUNDLE_PHONE_NUMBER, phoneNum)
            intent.putExtra(BUNDLE_USER_NAME, userName)
            intent.putExtra(BUNDLE_DATE_OF_BIRTH, dateOfBirth)
            intent.putExtra(BUNDLE_GENDER_TYPE, gender)
            intent.putExtra(BUNDLE_QR_CODE, qrCode)
            intent.putExtra(BUNDLE_PROFILE_IMG_URL,profileImageUrl)
            return intent
        }
    }
    private fun getIntentParam() {

        phoneNumber = intent?.getStringExtra(BUNDLE_PHONE_NUMBER).toString()
        userName = intent?.getStringExtra(BUNDLE_USER_NAME).toString()
        dateOfBirth = intent?.getStringExtra(BUNDLE_DATE_OF_BIRTH).toString()
        gender = intent?.getStringExtra(BUNDLE_GENDER_TYPE).toString()
        qrCode = intent?.getStringExtra(BUNDLE_QR_CODE).toString()
        profileImageUrl = intent?.getStringExtra(BUNDLE_PROFILE_IMG_URL).toString()

        Log.d("mainactivity","from main qrcode $qrCode")
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
        ivCreateMoment.setOnClickListener {
            mPresenter.onTapCreateMoment()
        }
    }

    private fun setUpBottomNavUI() {
        mainToolbar.visibility  = View.VISIBLE
        val bundle = Bundle()
        bundle.putString(BUNDLE_PHONE_NUMBER, phoneNumber)
        val fragment = MomentsFragment()
        fragment.arguments = bundle
        createFragmentFunction(fragment)

        bottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.action_moment -> {
                    mainToolbar.visibility  = View.VISIBLE
                    mPresenter.onTapMomentsFragment()
                    return@setOnItemSelectedListener true
                }
                R.id.action_chat -> {
                    mainToolbar.visibility  = View.GONE
                    mPresenter.onTapChatFragment()

                    return@setOnItemSelectedListener true
                }
                R.id.action_contacts -> {
                    mainToolbar.visibility  = View.GONE
                    mPresenter.onTapContactsFragment()

                    return@setOnItemSelectedListener true
                }
                R.id.action_me -> {
                    mainToolbar.visibility  = View.GONE
                    mPresenter.onTapMeFragment()
                    return@setOnItemSelectedListener true
                }
                R.id.action_setting -> {
                    mainToolbar.visibility  = View.GONE
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


        val bundle = Bundle()
        bundle.putString(BUNDLE_PHONE_NUMBER, phoneNumber)


        val fragment = MomentsFragment()
        fragment.arguments = bundle
        createFragmentFunction(fragment)
    }


    override fun navigateToChatFragment() {

        val bundle = Bundle()
        bundle.putString(BUNDLE_PHONE_NUMBER, phoneNumber)

        val fragment = ChatFragment()
        fragment.arguments = bundle
        createFragmentFunction(fragment)
    }

    override fun navigateToContactsFragment() {

        Log.d("MainActivity","mainactivity = $phoneNumber")
        val bundle = Bundle()
        bundle.putString(BUNDLE_PHONE_NUMBER, phoneNumber)

        val fragment = ContactsFragment()
        fragment.arguments = bundle
        createFragmentFunction(fragment)
    }

    override fun navigateToMeFragment() {

        val bundle = Bundle()
        bundle.putString(BUNDLE_PHONE_NUMBER, phoneNumber)
        bundle.putString(BUNDLE_USER_NAME, userName)
        bundle.putString(BUNDLE_DATE_OF_BIRTH, dateOfBirth)
        bundle.putString(BUNDLE_GENDER_TYPE, gender)
        bundle.putString(BUNDLE_QR_CODE, qrCode)
        bundle.putString(BUNDLE_PROFILE_IMG_URL,profileImageUrl)


        val fragment = ProfileFragment()
        fragment.arguments = bundle
        createFragmentFunction(fragment)
    }

    override fun navigateToSettingFragment() {

        val bundle = Bundle()
        bundle.putString(BUNDLE_PHONE_NUMBER, phoneNumber)

        val fragment = SettingFragment()
        fragment.arguments = bundle
        createFragmentFunction(fragment)
    }


    private fun createFragmentFunction(fragmentParam:Fragment){
        supportFragmentManager.beginTransaction().replace(
            R.id.flFragment, fragmentParam, fragmentParam.javaClass.simpleName
        ).commit()
    }


}