package com.padcmyanmar.ttm.wechatapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.SplashPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls.SplashPresenterImpl
import com.padcmyanmar.ttm.wechatapp.mvp.views.SplashView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btnLogin
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity(),SplashView {
    private lateinit var mPresenter: SplashPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        setUpPresenter()
        mPresenter.onUiReady(this,this)

        setUpClickListener()
    }

    private fun setUpClickListener() {
        btnLogin.setOnClickListener {
            mPresenter.onTapLogin()
        }

        btnSignUp.setOnClickListener {
            mPresenter.onTapSignUp()
        }
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<SplashPresenterImpl, SplashView>()
    }

    override fun navigateToSignUpScreen() {
        startActivity(Intent(this@SplashActivity,OTPVerifyActivity::class.java))

    }

    override fun navigateToLoginScreen() {
        startActivity(Intent(this@SplashActivity,LoginActivity::class.java))

    }

}