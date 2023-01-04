package com.padcmyanmar.ttm.wechatapp.activities


import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter

import androidx.core.content.ContextCompat

import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.SignUpPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls.SignUpPresenterImpl
import com.padcmyanmar.ttm.wechatapp.mvp.views.SignUpView

import com.padcmyanmar.ttm.wechatapp.utils.monthsDataList


import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.edtPassword
import kotlinx.android.synthetic.main.activity_sign_up.txtInputPassword

import java.util.*
import kotlin.collections.ArrayList


class SignUpActivity : BaseActivity(), SignUpView {

    var s = Calendar.getInstance().get(Calendar.YEAR)
    var yearsList = arrayListOf<String>()
    var daysDataList = arrayListOf<String>()
    var selectedYear = 0
    var selectedMonth = 0
    var paramDay = ""
    var paramMonth = ""
    var paramYear = ""
    var genderType = ""

    var phoneNumber = ""
    var userId = ""
    lateinit var mPresenter: SignUpPresenter

    companion object{
        private const val  BUNDLE_PHONE_NUMBER = "BUNDLE_PHONE_NUMBER"
        private const val  BUNDLE_USER_ID = "BUNDLE_USER_ID"


        fun newIntent(context: Context, phoneNum: String, userIdParam: String):Intent{
            val intent = Intent(context, SignUpActivity::class.java)
            intent.putExtra(BUNDLE_PHONE_NUMBER, phoneNum)
            intent.putExtra(BUNDLE_USER_ID,userIdParam)
            return intent
        }
    }

    private fun getIntentParam() {

        phoneNumber = intent?.getStringExtra(BUNDLE_PHONE_NUMBER).toString()
        userId = intent?.getStringExtra(BUNDLE_USER_ID).toString()


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        setUpPresenter()
        mPresenter.onUiReady(this, this)
        getIntentParam()

        setUpSpinner()
        setUpCheckBox()
        setUpEditTextAndButton()
        clickListener()

    }
    private fun setUpPresenter() {
        mPresenter = getPresenter<SignUpPresenterImpl, SignUpView>()
    }

    private fun setUpSpinner() {

        val dayAdapter = ArrayAdapter(
            this,
            R.layout.spinner_item_selected, callDayDataList(0, 0)
        )

        dayAdapter.setDropDownViewResource(R.layout.spinner_item_selected)

        val monthAdapter = ArrayAdapter(
            this,
            R.layout.spinner_item_selected, monthsDataList
        )
        monthAdapter.setDropDownViewResource(R.layout.spinner_item_selected)

        val yearAdapter = ArrayAdapter(
            this,
            R.layout.spinner_item_selected, callYearDataList()
        )
        monthAdapter.setDropDownViewResource(R.layout.spinner_item_selected)



        spDayEdit.adapter = dayAdapter
        spDayEdit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Toast.makeText(this@SignUpActivity, parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                paramDay = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        spMonthEdit.adapter = monthAdapter
        spMonthEdit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Toast.makeText(this@SignUpActivity, parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                paramMonth =  position.toString()//parent.getItemAtPosition(position).toString()
                selectedMonth = position
                callDayDataList(selectedMonth, selectedYear)


            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        spYearEdit.adapter = yearAdapter
        spYearEdit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Toast.makeText(this@SignUpActivity, parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                paramYear = parent.getItemAtPosition(position).toString()
                selectedYear = if (position == 0) 0 else
                    parent.getItemAtPosition(position).toString().toInt()

                callDayDataList(selectedMonth, selectedYear)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


    }
    private fun setUpCheckBox() {

        val styledText =
            "<font color='#888888' size='12px'>Agree To </font> <font color='#062743' size='12px'> Term And Service</font>."
//        textView.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE)
        checkboxTermAndService.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(styledText, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(styledText)
        }
    }
    private fun setUpEditTextAndButton() {
        btnSignUp.isEnabled = false
        btnSignUp.background = ContextCompat.getDrawable(this, R.drawable.login_btn_disable_bg)


        txtInputName.setEndIconOnClickListener {

            edtName.setText("")
            btnSignUp.isEnabled = false
            btnSignUp.background = ContextCompat.getDrawable(this, R.drawable.login_btn_disable_bg)
        }

        txtInputPassword.setEndIconOnClickListener {
            edtPassword.setText("")
            btnSignUp.isEnabled = false
            btnSignUp.background = ContextCompat.getDrawable(this, R.drawable.login_btn_disable_bg)
        }

        edtName.addTextChangedListener(object : TextWatcher {
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
                if (s.isNotEmpty() && edtPassword.text.toString().isNotEmpty()
                    && checkboxTermAndService.isChecked
                ) {
                    btnSignUp.isEnabled = true
                    btnSignUp.background = ContextCompat.getDrawable(
                        this@SignUpActivity,
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
                if (s.isNotEmpty() && edtName.text.toString().isNotEmpty()
                    && checkboxTermAndService.isChecked
                ) {
                    btnSignUp.isEnabled = true
                    btnSignUp.background = ContextCompat.getDrawable(
                        this@SignUpActivity,
                        R.drawable.login_btn_bg
                    )
                }

            }
        })

    }
    private fun clickListener() {


        rbGenderGroup.setOnCheckedChangeListener { radioGroup, i ->
            genderType = when (i) {
                R.id.rbMale -> {
                    "Male"
                }
                R.id.rbFemale -> {
                    "Female"

                }
                else -> {
                    "Other"
                }
            }
        }

        checkboxTermAndService.setOnCheckedChangeListener { compoundButton, b ->

            if (b) {
                btnSignUp.isEnabled = true
                btnSignUp.background = ContextCompat.getDrawable(
                    this@SignUpActivity,
                    R.drawable.login_btn_bg
                )
            } else {
                btnSignUp.isEnabled = false
                btnSignUp.background =
                    ContextCompat.getDrawable(this, R.drawable.login_btn_disable_bg)


            }

        }

        btnSignUpBack.setOnClickListener {
            onBackPressed()
        }
        btnSignUp.setOnClickListener {
            /* Log.d(
                 "SignUp", "$paramDay / $paramMonth / $paramYear  \n $genderType " +
                         "\n ${edtName.text.toString()}  \n ${checkboxTermAndService.isChecked}"

             )*/

            var paramDate = "$paramYear-$paramMonth-$paramDay"


            if(edtName.text.toString().isEmpty())
            {
                showError("Please fill the name.")
            }
            else if(paramDate.isEmpty())
            {
                showError("Please fill the date of birth. ")
            }
            else if(genderType.isEmpty())
            {
                showError("Please fill the gender.")
            }
            else if(edtPassword.text.toString().isEmpty()){
                showError("Please fill the password ")

            }else{
                mPresenter.onTapSignUp(
                    name = edtName.text.toString(),
                    dateOfBirth = "$paramDate",
                    gender = genderType,
                    password = edtPassword.text.toString(),
                    phoneNo = phoneNumber,
                    userId = userId,
                    onSuccess = {
                        showError("$it")
                    }
                ) {
                    showError("$it")
                }
            }

        }
    }


    private fun callYearDataList(): ArrayList<String> {
        for (year in s downTo 1900) {
            yearsList.add("$year")

        }
        yearsList.add(0, "Years")

        return yearsList
    }

    private fun callDayDataList(
        paramMonthID: Int,
        paramYearID: Int
    ): ArrayList<String> {

        val endDay =
            if (paramMonthID == 4 || paramMonthID == 6 || paramMonthID == 9 || paramMonthID == 11) {
                30
            } else {
                if (paramMonthID == 2) {
                    if (paramYearID % 4 == 0)
                        29
                    else
                        28
                } else {
                    31
                }

            }


        daysDataList.clear()
        for (day in 1..endDay) {
            daysDataList.add("$day")

        }
        daysDataList.add(0, "Days")

        return daysDataList
    }

    override fun signUpFunction() {
        startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
    }


}