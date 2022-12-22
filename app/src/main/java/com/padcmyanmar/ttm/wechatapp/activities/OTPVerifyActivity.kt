package com.padcmyanmar.ttm.wechatapp.activities

import `in`.aabhasjindal.otptextview.OTPListener
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty

import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.OTPVerifyPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls.OTPVerifyPresenterImpl
import com.padcmyanmar.ttm.wechatapp.mvp.views.OTPVerificationView
import com.padcmyanmar.ttm.wechatapp.utils.OTP_DEMO_ONE_DIGIT
import com.padcmyanmar.ttm.wechatapp.utils.OTP_DEMO_TWO_DIGIT

import kotlinx.android.synthetic.main.activity_otp_verify.*
import kotlinx.android.synthetic.main.activity_otp_verify.edtPhoneNo
import kotlinx.android.synthetic.main.activity_otp_verify.txtInputPhoneNumber



class OTPVerifyActivity : BaseActivity(), OTPVerificationView {

    lateinit var mPresenter: OTPVerifyPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verify)

        setUpPresenter()
        mPresenter.onUiReady(this, this)
        setUpOTP()
        setUpUI()
        clickListener()

    }

    private fun setUpUI() {

        btnGetOTP.isEnabled = false
        btnGetOTP.background = ContextCompat.getDrawable(this, R.drawable.login_btn_disable_bg)

        btnVerify.isEnabled = false
        btnVerify.background = ContextCompat.getDrawable(this, R.drawable.login_btn_disable_bg)

        txtInputPhoneNumber.setEndIconOnClickListener {

            edtPhoneNo.setText("")
            btnGetOTP.isEnabled = false
            btnGetOTP.background = ContextCompat.getDrawable(this, R.drawable.login_btn_disable_bg)

            btnVerify.isEnabled = false
            btnVerify.background = ContextCompat.getDrawable(this, R.drawable.login_btn_disable_bg)
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
                if (s.isNotEmpty() && otpView.isNotEmpty()) {

                    btnGetOTP.isEnabled = true
                    btnGetOTP.background = ContextCompat.getDrawable(
                        this@OTPVerifyActivity,
                        R.drawable.login_btn_bg
                    )


                }
            }
        })

    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<OTPVerifyPresenterImpl, OTPVerificationView>()
    }

    private fun clickListener() {
        // errorButton.setOnClickListener { otpTextView?.showError() }

        btnGetOTP.setOnClickListener {

        }

        btnOTPVerifyPageBack.setOnClickListener {
            mPresenter.onTapBackFunction()
        }

        btnVerify.setOnClickListener {
            otpView?.showSuccess()
            mPresenter.onTapVerify()
        }
    }

    private fun setUpOTP() {
        otpView?.requestFocusOTP()
        otpView?.otpListener = object : OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                // Toast.makeText(this@OTPVerifyActivity, "The OTP is $otp", Toast.LENGTH_SHORT).show()

                if(otp == OTP_DEMO_ONE_DIGIT || otp == OTP_DEMO_TWO_DIGIT)
                {
                    btnVerify.isEnabled = true
                    btnVerify.background = ContextCompat.getDrawable(
                        this@OTPVerifyActivity,
                        R.drawable.login_btn_bg
                    )
                }else{
                    otpView?.showError()
                    Toast.makeText(this@OTPVerifyActivity, "The OTP Code is wrong. ", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    override fun otpFunction() {

    }

    override fun verifyFunction() {
        startActivity(Intent(this@OTPVerifyActivity, SignUpActivity::class.java))
    }

    override fun navigateToBackFunction() {
        onBackPressed()
    }

}