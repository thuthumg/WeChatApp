package com.padcmyanmar.ttm.wechatapp.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.data.vos.MomentVO
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.ProfileFragmentPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls.ProfileFragmentPresenterImpl
import com.padcmyanmar.ttm.wechatapp.mvp.views.ProfileFragmentView
import com.padcmyanmar.ttm.wechatapp.utils.monthsDataList
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.dialog_edit_profile.*
import kotlinx.android.synthetic.main.dialog_edit_profile.edtName
import kotlinx.android.synthetic.main.dialog_edit_profile.spDayEdit
import kotlinx.android.synthetic.main.dialog_edit_profile.spMonthEdit
import kotlinx.android.synthetic.main.dialog_edit_profile.spYearEdit
import java.util.*
import kotlin.collections.ArrayList

class ProfileDialogFragment : DialogFragment(), ProfileFragmentView {
    lateinit var mPresenter: ProfileFragmentPresenter
    var userName:String? = ""
    var birthDate:String? = ""
    var genderType:String? = ""
    var uid:String? = ""


    var s = Calendar.getInstance().get(Calendar.YEAR)
    var yearsList = arrayListOf<String>()
    var daysDataList = arrayListOf<String>()
    var selectedYear = 0
    var selectedMonth = 0
    var paramDay = ""
    var paramMonth = ""
    var paramYear = ""



    companion object {
        const val TAG_PROFILE_EDIT_DIALOG = "TAG_PROFILE_EDIT_DIALOG"
        const val BUNDLE_USER_ID = "BUNDLE_USER_ID"
        const val BUNDLE_USER_NAME_EDIT = "BUNDLE_USER_NAME_EDIT"
        const val BUNDLE_DATE_OF_BIRTH_EDIT = "BUNDLE_DATE_OF_BIRTH_EDIT"
        const val BUNDLE_GENDER_TYPE_EDIT = "BUNDLE_GENDER_TYPE_EDIT"

        fun newFragment(): ProfileDialogFragment {
            return ProfileDialogFragment()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner_bg);
        return inflater.inflate(R.layout.dialog_edit_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()
        getArgumentData()
        setUpSpinner()
        setUpView()
        clickListener()
        context?.let { mPresenter.onUiReady(it, this) }
    }
    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ProfileFragmentPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun clickListener() {
       btnCancel.setOnClickListener {
           dismiss()
       }
        btnSave.setOnClickListener {
            var paramDate = "$paramYear-$paramMonth-$paramDay"


            if(edtName.text.toString().isEmpty())
            {
                showError("Please fill the name.")
            }
            else if(paramDate.isEmpty())
            {
                showError("Please fill the date of birth. ")
            }
            else if(genderType?.isEmpty() == true)
            {
                showError("Please fill the gender.")
            }else{
                mPresenter.onTapEditUser(
                    userName = edtName.text.toString(),
                    dateOfBirth = "$paramDate",
                    genderType = genderType?:"",
                    onSuccess = {
                        showError("$it")
                    }
                ) {
                    showError("$it")
                }
            }

        }

        rbGenderGroupEdit.setOnCheckedChangeListener { radioGroup, i ->
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
    }

    private fun setUpView() {
        edtName.setText(userName)


        var dateSplitData = birthDate?.split("-")
        var yearData = dateSplitData?.get(0)
        var monthData = dateSplitData?.get(1)
        var dayData = dateSplitData?.get(2)

        if(yearData != null && monthData != null && dayData != null)
        {
            var  getYearList = callYearDataList()
            var selectedYearPosition = 0
            for((index, yearItem)  in getYearList.withIndex())
            {
                if(yearData == yearItem)
                {
                    selectedYearPosition = index
                    break
                }
            }
            spYearEdit.setSelection(selectedYearPosition)
            spMonthEdit.setSelection(monthData?.toInt()?:0)
            spDayEdit.setSelection(dayData?.toInt()?:0)
        }


        when(genderType)
        {
            "Male"->{
                rbMaleEdit.isChecked = true
            }
            "Female"->{
                rbFemaleEdit.isChecked = true
            }
            "Other"->{
                rbOtherEdit.isChecked = true
            }
        }

    }

    private fun getArgumentData() {
         userName = arguments?.getString(BUNDLE_USER_NAME_EDIT).toString()
         birthDate = arguments?.getString(BUNDLE_DATE_OF_BIRTH_EDIT).toString()
         genderType= arguments?.getString(BUNDLE_GENDER_TYPE_EDIT).toString()
         uid = arguments?.getString(BUNDLE_USER_ID).toString()
    }


    private fun setUpSpinner() {

        val dayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item_selected, callDayDataList(0, 0)
        )

        dayAdapter.setDropDownViewResource(R.layout.spinner_item_selected)

        val monthAdapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item_selected, monthsDataList
        )
        monthAdapter.setDropDownViewResource(R.layout.spinner_item_selected)

        val yearAdapter = ArrayAdapter(
            requireContext(),
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

// if (compareValue != null) {
//                    val spinnerPosition: Int = adapter.getPosition(compareValue)
//                    mSpinner.setSelection(spinnerPosition)
//                }

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

    private fun callYearDataList(): ArrayList<String> {
        yearsList = arrayListOf()


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

    override fun onTapEditProfile(userName: String, dateOfBirth: String, genderType: String) {

    }

    override fun showMomentData(mMomentVOList: ArrayList<MomentVO>) {

    }

    override fun showError(error: String) {

    }
}