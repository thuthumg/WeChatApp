package com.padcmyanmar.ttm.wechatapp.fragments

import android.R.attr.bitmap
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context.WINDOW_SERVICE
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.zxing.WriterException
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.activities.CreateNewMomentActivity
import com.padcmyanmar.ttm.wechatapp.activities.MainActivity
import com.padcmyanmar.ttm.wechatapp.activities.MainActivity.Companion.BUNDLE_DATE_OF_BIRTH
import com.padcmyanmar.ttm.wechatapp.activities.MainActivity.Companion.BUNDLE_GENDER_TYPE
import com.padcmyanmar.ttm.wechatapp.activities.MainActivity.Companion.BUNDLE_PHONE_NUMBER
import com.padcmyanmar.ttm.wechatapp.activities.MainActivity.Companion.BUNDLE_PROFILE_IMG_URL
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
import kotlinx.android.synthetic.main.activity_create_new_moment.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.IOException


class ProfileFragment : Fragment(), MomentItemDelegate,ProfileFragmentView {

    lateinit var mPresenter: ProfileFragmentPresenter

    private lateinit var mMomentsListAdapter: MomentsListAdapter

    private var loginUserPhoneNumber = ""
    private var loginUserUserName = ""
    private var loginUserDateOfBirth = ""
    private var loginUserGenderType = ""
    private var loginUserQRCode = ""
    private var loginUserProfileImageUrl=""
    private val PICK_IMAGE_REQUEST_FOR_PROFILE = 100
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
        loginUserProfileImageUrl = arguments?.getString(BUNDLE_PROFILE_IMG_URL).toString()

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
        Log.d("ProfileFragment","check Url ${loginUserProfileImageUrl}")
       tvProfileName.text = loginUserUserName
        tvPhoneNumber.text = loginUserPhoneNumber
        tvBirthDate.text = loginUserDateOfBirth
        tvGenderType.text = loginUserGenderType
        Glide.with(this)
            .load(loginUserProfileImageUrl)
            .placeholder(R.drawable.empty_image)
            .into(ivUserProfile)

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
        ivGallery.setOnClickListener {
            openGallery()
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

    override fun onTapBookMark(momentVO: MomentVO) {

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

    }

    override fun showMomentData(mMomentVOList: ArrayList<MomentVO>) {
        Log.d("ProfileFragment","check momentlist size ${mMomentVOList.size}")
        mMomentsListAdapter.setNewData(mMomentVOList)
    }

    override fun showError(error: String) {

    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_FOR_PROFILE)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_FOR_PROFILE && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            val filePath = data.data
            try {


                filePath?.let {
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source: ImageDecoder.Source =
                            ImageDecoder.createSource(context?.contentResolver!!, filePath)

                        val bitmap = ImageDecoder.decodeBitmap(source)
                        mPresenter.onPhotoTaken(bitmap, onSuccess = {
                            Log.d("profileFragment","url $it")
                            Glide.with(this)
                                .load(it)
                                .placeholder(R.drawable.empty_image)
                                .into(ivUserProfile)
                        })
                    } else {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            context?.contentResolver, filePath
                        )
                        mPresenter.onPhotoTaken(bitmap, onSuccess = {
                            Log.d("profileFragment","url $it")
                            Glide.with(this)
                                .load(it)
                                .into(ivUserProfile)
                        })
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


}