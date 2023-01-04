package com.padcmyanmar.ttm.wechatapp.fragments

import android.R.attr.bitmap
import android.annotation.SuppressLint
import android.content.Context.WINDOW_SERVICE
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.*
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.WriterException
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.activities.MainActivity.Companion.BUNDLE_DATE_OF_BIRTH
import com.padcmyanmar.ttm.wechatapp.activities.MainActivity.Companion.BUNDLE_GENDER_TYPE
import com.padcmyanmar.ttm.wechatapp.activities.MainActivity.Companion.BUNDLE_PHONE_NUMBER
import com.padcmyanmar.ttm.wechatapp.activities.MainActivity.Companion.BUNDLE_QR_CODE
import com.padcmyanmar.ttm.wechatapp.activities.MainActivity.Companion.BUNDLE_USER_NAME
import com.padcmyanmar.ttm.wechatapp.adapters.MomentsListAdapter
import com.padcmyanmar.ttm.wechatapp.data.vos.MomentVO
import com.padcmyanmar.ttm.wechatapp.delegates.MomentItemDelegate
import com.padcmyanmar.ttm.wechatapp.dialogs.ProfileDialogFragment
import com.padcmyanmar.ttm.wechatapp.dialogs.ProfileDialogFragment.Companion.BUNDLE_DATE_OF_BIRTH_EDIT
import com.padcmyanmar.ttm.wechatapp.dialogs.ProfileDialogFragment.Companion.BUNDLE_GENDER_TYPE_EDIT
import com.padcmyanmar.ttm.wechatapp.dialogs.ProfileDialogFragment.Companion.BUNDLE_USER_ID
import com.padcmyanmar.ttm.wechatapp.dialogs.ProfileDialogFragment.Companion.BUNDLE_USER_NAME_EDIT
import com.padcmyanmar.ttm.wechatapp.dialogs.ProfileDialogFragment.Companion.TAG_PROFILE_EDIT_DIALOG
import com.padcmyanmar.ttm.wechatapp.dialogs.QRCodePopUpDialog
import com.padcmyanmar.ttm.wechatapp.dialogs.QRCodePopUpDialog.Companion.LOGIN_USER_CODE
import com.padcmyanmar.ttm.wechatapp.dialogs.QRCodePopUpDialog.Companion.LOGIN_USER_NAME
import com.padcmyanmar.ttm.wechatapp.dialogs.QRCodePopUpDialog.Companion.TAG_QR_CODE_DIALOG
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.ProfileFragmentPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls.ProfileFragmentPresenterImpl
import com.padcmyanmar.ttm.wechatapp.mvp.views.ProfileFragmentView
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment(), MomentItemDelegate,ProfileFragmentView {

    lateinit var mPresenter: ProfileFragmentPresenter

    lateinit var mMomentsListAdapter: MomentsListAdapter

    private var loginUserPhoneNumber = ""
    private var loginUserUserName = ""
    private var loginUserDateOfBirth = ""
    private var loginUserGenderType = ""
    private var loginUserQRCode = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()

        loginUserPhoneNumber = arguments?.getString(BUNDLE_PHONE_NUMBER).toString()
        loginUserUserName = arguments?.getString(BUNDLE_USER_NAME).toString()
        loginUserDateOfBirth = arguments?.getString(BUNDLE_DATE_OF_BIRTH).toString()
        loginUserGenderType = arguments?.getString(BUNDLE_GENDER_TYPE).toString()
        loginUserQRCode = arguments?.getString(BUNDLE_QR_CODE).toString()

        setUpProfileUI()
        setUpMomentsList()
        clickListenerFunc()
        context?.let { mPresenter.onUiReady(it, this) }
    }

    private fun setUpPresenter() {
       mPresenter = ViewModelProvider(this)[ProfileFragmentPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpProfileUI() {
       tvProfileName.text = loginUserUserName
        tvPhoneNumber.text = loginUserPhoneNumber
        tvBirthDate.text = loginUserDateOfBirth
        tvGenderType.text = loginUserGenderType
        generateQRCode(loginUserQRCode)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun generateQRCode(loginUserQRCode: String) {
        val windowManager: WindowManager = context?.getSystemService(WINDOW_SERVICE)  as WindowManager

        // on below line we are initializing a
        // variable for our default display
        val display: Display = windowManager.defaultDisplay

        // on below line we are creating a variable
        // for point which is use to display in qr code
        val point: Point = Point()
        display.getSize(point)

        // on below line we are getting
        // height and width of our point
        val width = point.x
        val height = point.y

        // on below line we are generating
        // dimensions for width and height
        var dimen = if (width < height) width else height
        dimen = dimen * 3 / 4
        Log.d("ProfileFragment","test qrcode$loginUserQRCode")

        val qrgEncoder = QRGEncoder(loginUserQRCode, null, QRGContents.Type.TEXT, dimen)
        qrgEncoder.colorBlack = Color.WHITE
        qrgEncoder.colorWhite = Color.BLACK
        try {
            // Getting QR-Code as Bitmap
        var    bitmap = qrgEncoder.bitmap
            // Setting Bitmap to ImageView
            ivQrCode.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            Log.v("ProfileFragment", e.toString())
        }


    }

    private fun clickListenerFunc() {
        mcvEditProfile.setOnClickListener {
            setUpEditProfile()
        }

        ivQrCode.setOnClickListener {

            val qrCodePopUpDialog = QRCodePopUpDialog.newFragment()
            val bundle = Bundle()
            bundle.putString(LOGIN_USER_NAME,loginUserUserName)
            bundle.putString(LOGIN_USER_CODE,loginUserQRCode)
            qrCodePopUpDialog.arguments = bundle
            qrCodePopUpDialog.show(parentFragmentManager, TAG_QR_CODE_DIALOG)


        }
    }

    private fun setUpMomentsList() {
        mMomentsListAdapter =
            MomentsListAdapter(this, "")
        rvBookMarkList.adapter = mMomentsListAdapter
        rvBookMarkList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        rvBookMarkList.isNestedScrollingEnabled = false
    }

    override fun onTapFavorite(momentVO: MomentVO) {

    }

    override fun onTapSavePost() {

    }

    private fun setUpEditProfile() {

        val profileEditDialog = ProfileDialogFragment.newFragment()
        val bundle = Bundle()
        bundle.putString(BUNDLE_USER_ID, loginUserQRCode)
        bundle.putString(BUNDLE_USER_NAME_EDIT, loginUserUserName)
        bundle.putString(BUNDLE_DATE_OF_BIRTH_EDIT,loginUserDateOfBirth)
        bundle.putString(BUNDLE_GENDER_TYPE_EDIT, loginUserGenderType)
        profileEditDialog.arguments = bundle
        profileEditDialog.show(parentFragmentManager, TAG_PROFILE_EDIT_DIALOG)
    }

    override fun onTapEditProfile(userName: String, dateOfBirth: String, genderType: String) {
       // TODO("Not yet implemented")
    }

    override fun showError(error: String) {
      //  TODO("Not yet implemented")
    }

}