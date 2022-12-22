package com.padcmyanmar.ttm.wechatapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.adapters.MediaTypeDataAdapter
import com.padcmyanmar.ttm.wechatapp.delegates.MediaTypeDataDelegate
import kotlinx.android.synthetic.main.activity_create_new_moment.*
import kotlinx.android.synthetic.main.content_create_moment_layout.*
import kotlinx.android.synthetic.main.fragment_moments.*

class CreateNewMomentActivity : BaseActivity(),MediaTypeDataDelegate {

   lateinit var mMediaTypeDataAdapter:MediaTypeDataAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_moment)

        setUpMediaDataAdapter()
        clickListener()
    }

    private fun clickListener() {

        btnClose.setOnClickListener {
            hideKeyboard(this)
            finish()
        }
    }

    private fun setUpMediaDataAdapter(){
        mMediaTypeDataAdapter = MediaTypeDataAdapter(this)

        rvMediaTypeList.adapter = mMediaTypeDataAdapter
        rvMediaTypeList.layoutManager = GridLayoutManager(
            this,3)
       // rvMediaTypeList.isNestedScrollingEnabled = false
    }

    override fun goToChooseMediaType() {
    }
}