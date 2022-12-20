package com.padcmyanmar.ttm.wechatapp.activities

import `in`.aabhasjindal.otptextview.OTPListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.padcmyanmar.ttm.wechatapp.R
import kotlinx.android.synthetic.main.activity_otpverify.*
import kotlin.Result.Companion.success


class OTPVerifyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverify)
        otp_view?.requestFocusOTP()
        otp_view?.otpListener = object : OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                Toast.makeText(this@OTPVerifyActivity, "The OTP is $otp", Toast.LENGTH_SHORT).show()
            }
        }
       // errorButton.setOnClickListener { otpTextView?.showError() }
        btnVerify.setOnClickListener { otp_view?.showSuccess() }
    }
}