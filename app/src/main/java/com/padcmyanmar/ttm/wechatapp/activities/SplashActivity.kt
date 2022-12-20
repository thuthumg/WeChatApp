package com.padcmyanmar.ttm.wechatapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.padcmyanmar.ttm.wechatapp.R
import kotlinx.android.synthetic.main.activity_login.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        btnLogin.setOnClickListener {
            startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
        }
    }
}