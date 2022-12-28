package com.padcmyanmar.ttm.wechatapp.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.LoginPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls.LoginPresenterImpl
import com.padcmyanmar.ttm.wechatapp.mvp.views.LoginView
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity(), LoginView {

    private lateinit var mPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setUpPresenter()
        mPresenter.onUiReady(this,this)
        setUpUI()
        clickListener()
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(phoneNumber)       // Phone number to verify
//            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity(this)                 // Activity (for callback binding)
//            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun setUpUI() {
        btnLogin.isEnabled = false
        btnLogin.background = ContextCompat.getDrawable(this, R.drawable.login_btn_disable_bg)

        txtInputPhoneNumber.setEndIconOnClickListener {

            edtPhoneNo.setText("")
            btnLogin.isEnabled = false
            btnLogin.background = ContextCompat.getDrawable(this, R.drawable.login_btn_disable_bg)
        }

        txtInputPassword.setEndIconOnClickListener {
            edtPassword.setText("")
            btnLogin.isEnabled = false
            btnLogin.background = ContextCompat.getDrawable(this, R.drawable.login_btn_disable_bg)
        }

        edtPhoneNo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isNotEmpty() && edtPassword.text.toString().isNotEmpty()) {
                    btnLogin.isEnabled = true
                    btnLogin.background = ContextCompat.getDrawable(
                        this@LoginActivity,
                        R.drawable.login_btn_bg
                    )
                }
            }
        })

        edtPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isNotEmpty() && edtPhoneNo.text.toString().isNotEmpty())
                {
                    btnLogin.isEnabled = true
                    btnLogin.background = ContextCompat.getDrawable(this@LoginActivity,
                        R.drawable.login_btn_bg)
                }

            }
        })
    }

    private fun clickListener() {
        btnLogin.setOnClickListener {

            if(edtPhoneNo.text.toString().isNotEmpty() &&
                edtPassword.text.toString().isNotEmpty())

            {
                mPresenter.onTapLogin(this,edtPhoneNo.text.toString(),
                    edtPassword.text.toString(),
                    onSuccess = {
                        showError(it)
                        Thread.sleep(2000)
                        mPresenter.goToMainPage()
                    }
                ) {
                    showError(it)
                }
            }
            else{
                showError("Please fill the Phone No and Password.")
            }
        }

        btnLoginBack.setOnClickListener {
            mPresenter.onTapBackFunction()
        }
    }

    private fun setUpPresenter() {

        mPresenter = getPresenter<LoginPresenterImpl, LoginView>()
    }

    override fun loginFunction() {
        startActivity(Intent(this@LoginActivity,MainActivity::class.java))

    }

    override fun navigateToBackFunction() {
        onBackPressed()
    }

}