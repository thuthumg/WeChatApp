package com.padcmyanmar.ttm.wechatapp.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
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
        mPresenter.onUiReady(this, this)

        setUpUI()
        clickListener()
    }

    private fun setUpPresenter() {

        mPresenter = getPresenter<LoginPresenterImpl, LoginView>()
    }

    private fun setUpUI() {
        btnLogin.isEnabled = false
        btnLogin.background = ContextCompat.getDrawable(this, R.drawable.login_btn_disable_bg)

        clearIconClickListener()
        editTextClickListener()

    }

    private fun editTextClickListener() {
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
                if (s.isNotEmpty() && edtPhoneNo.text.toString().isNotEmpty()) {
                    btnLogin.isEnabled = true
                    btnLogin.background = ContextCompat.getDrawable(
                        this@LoginActivity,
                        R.drawable.login_btn_bg
                    )
                }

            }
        })
    }

    private fun clearIconClickListener() {
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

    }

    private fun clickListener() {


        btnLogin.setOnClickListener {

            pbLoading.visibility = View.VISIBLE
            if (edtPhoneNo.text.toString().isNotEmpty() &&
                edtPassword.text.toString().isNotEmpty()
            ) {
                mPresenter.onTapLogin(this, edtPhoneNo.text.toString(),
                    edtPassword.text.toString(),
                    onSuccess = { msg, userVO ->

                            pbLoading.visibility = View.GONE
                           // showError(msg)
                            mPresenter.goToMainPage(userVO)
                            finish()


                    }, onFailure = {
                        pbLoading.visibility = View.GONE
                        showError(it)
                    })

            } else {
                showError("Please fill the Phone No and Password.")
            }
        }

        btnLoginBack.setOnClickListener {
            mPresenter.onTapBackFunction()
        }
    }


    override fun loginFunction(userVO: UserVO) {

        Log.d("loginactivity","user profile = "+ userVO.profileUrl)


        startActivity(
            MainActivity.newIntent(
                this@LoginActivity,
                userVO.phoneNumber.toString(),
                userVO.name.toString(),
                userVO.dateOfBirth.toString(),
                userVO.genderType.toString(),
                userVO.id,
                userVO.profileUrl
            )
        )

        finish()

    }

    override fun navigateToBackFunction() {
        onBackPressed()
    }

}