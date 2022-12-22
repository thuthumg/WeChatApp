package com.padcmyanmar.ttm.wechatapp.activities


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.utils.daysDataList
import com.padcmyanmar.ttm.wechatapp.utils.monthsDataList
import com.padcmyanmar.ttm.wechatapp.utils.yearsDataList
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        setUpSpinner()
        setUpCheckBox()
        clickListener()

    }

    private fun clickListener() {
        btnSignUp.setOnClickListener {
            startActivity(Intent(this@SignUpActivity,MainActivity::class.java))
        }
    }

    private fun setUpCheckBox() {

        val styledText = "<font color='#888888' size='12px'>Agree To </font> <font color='#062743' size='12px'> Term And Service</font>."
//        textView.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE)


        checkboxTermAndService.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(styledText, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(styledText)
        }
    }

    private fun setUpSpinner() {
        val dayAdapter = ArrayAdapter(this,
            R.layout.spinner_item_selected, daysDataList)
        dayAdapter.setDropDownViewResource(R.layout.spinner_item_selected)

        val monthAdapter = ArrayAdapter(this,
            R.layout.spinner_item_selected, monthsDataList)
        monthAdapter.setDropDownViewResource(R.layout.spinner_item_selected)

        val yearAdapter = ArrayAdapter(this,
            R.layout.spinner_item_selected, yearsDataList)
        monthAdapter.setDropDownViewResource(R.layout.spinner_item_selected)



        spDay.adapter = dayAdapter
        spDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Toast.makeText(this@SignUpActivity, parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        spMonth.adapter = monthAdapter
        spMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Toast.makeText(this@SignUpActivity, parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        spYear.adapter = yearAdapter
        spYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Toast.makeText(this@SignUpActivity, parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


    }
}