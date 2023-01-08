package com.padcmyanmar.ttm.wechatapp.activities

import android.content.Context
import android.content.Intent
import android.database.Cursor

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.net.toUri

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager


import com.padcmyanmar.ttm.wechatapp.R

import com.padcmyanmar.ttm.wechatapp.adapters.ChatDetailListAdapter
import com.padcmyanmar.ttm.wechatapp.adapters.MediaTypeMessageAdapter
import com.padcmyanmar.ttm.wechatapp.data.vos.ChatMessageVO
import com.padcmyanmar.ttm.wechatapp.data.vos.MediaDataVO

import com.padcmyanmar.ttm.wechatapp.mvp.presenters.ChatDetailPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls.ChatDetailPresenterImpl

import com.padcmyanmar.ttm.wechatapp.mvp.views.ChatDetailView
import com.padcmyanmar.ttm.wechatapp.utils.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_chat_detail.*
import kotlinx.android.synthetic.main.content_chat_detail_layout.*
import kotlinx.android.synthetic.main.content_chat_detail_layout.rvMediaTypeList


class ChatDetailActivity : BaseActivity(),ChatDetailView {

    lateinit var mPresenter: ChatDetailPresenter
    lateinit var mChatDetailListAdapter: ChatDetailListAdapter
    lateinit var mMediaTypeMessageAdapter: MediaTypeMessageAdapter
    var mChatUserId = ""
    var mGroupOrPrivateChat = ""
    var mChatUserProfile = ""

    var imageEncoded: String? = null
    var imagesEncodedList: ArrayList<Uri> = arrayListOf()
    var imagesUrlList: ArrayList<MediaDataVO> = arrayListOf()

    companion object {
        var PICK_IMAGE_OR_VIDEO_REQUEST = 100
        private const val BUNDLE_CHAT_USER_NAME = "BUNDLE_CHAT_USER_NAME"
        private const val BUNDLE_CHAT_USER_ID = "BUNDLE_CHAT_USER_ID"
        private const val BUNDLE_GROUP_OR_PRIVATE_CHAT = "BUNDLE_GROUP_OR_PRIVATE_CHAT"
        private const val BUNDLE_USER_PROFILE = "BUNDLE_USER_PROFILE"
      //  private const val BUNDLE_CHAT_USER_PHONE_NUM = "BUNDLE_CHAT_USER_PHONE_NUM"
//  chatUserPhoneNum: String
        fun newIntent(
            context: Context,
            chatUserName: String,
            chatUserId: String,
            chatUserProfile:String,
            checkGroupOrPrivateChat:String

        ): Intent {
            val intent = Intent(context, ChatDetailActivity::class.java)
            intent.putExtra(BUNDLE_CHAT_USER_NAME, chatUserName)
            intent.putExtra(BUNDLE_CHAT_USER_ID, chatUserId)
            intent.putExtra(BUNDLE_GROUP_OR_PRIVATE_CHAT, checkGroupOrPrivateChat)
            intent.putExtra(BUNDLE_USER_PROFILE,chatUserProfile)
            return intent
        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_detail)

        //  setUpBottomButtonLayout()
        setUpPresenter()
        getIntentData()
        setUpPhotoOrVideoAdapter()
        setUpMessageAdapter()
        clickListener()
       // mPresenter.onUiReady(this,this)
        mPresenter.onUiReadyInChatDetails(this, receiverId = mChatUserId, checkPrivateOrGroup = mGroupOrPrivateChat)
    }

    private fun setUpPhotoOrVideoAdapter() {
        mMediaTypeMessageAdapter =
            MediaTypeMessageAdapter()
        rvMediaTypeList.adapter = mMediaTypeMessageAdapter
        rvMediaTypeList.layoutManager = GridLayoutManager(this,3)

    }

    private fun clickListener() {
        ivSendMessage.setOnClickListener {
            var count = 0
            hideKeyboard(this)

            if(imagesEncodedList?.size == 0)
            {
                sendMsgFunction(arrayListOf())
//                mPresenter.onTapCreate(
//                    arrayListOf(), arrayListOf(), edtDescription.text.toString(), onSuccess = {
//                        Log.d("CreateNewMoment","success moment")
//                        pbLoading.visibility = View.GONE
//                        finish()
//                    }
//                ) {
//                    showError(it)
//                    pbLoading.visibility = View.GONE
//                }


            }else{
                //to upload photo video
                for (imageUri in imagesEncodedList!!)
                {
                    //  val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(imageUri))
                    mPresenter.uploadFileCreate(imageUri, onSuccess = {
                        it?.let { it1 ->
                            // Log.d("CreateNewMoment","check image link at upload function= $it1")
                            var mediaDataVO = MediaDataVO()
                            mediaDataVO.mediaDataLink = it1
                            mediaDataVO.mediaType = getFileExtensionFunc(this,imageUri)
                            imagesUrlList?.add(mediaDataVO)
                            count +=1

                            if(count == (imagesEncodedList?.size))
                            {

                                imagesUrlList?.let { imgList ->
                                    //  Log.d("CreateNewMoment","check image link = $imgList")

                                    sendMsgFunction(imgList)
//                                    mPresenter.onTapCreate(
//                                        imgList,
//                                        arrayListOf(),edtDescription.text.toString(), onSuccess = {
//                                            Log.d("CreateNewMoment","success moment")
//                                            pbLoading.visibility = View.GONE
//                                            finish()
//                                        }
//                                    ) { e ->
//                                        pbLoading.visibility = View.GONE
//                                        showError(e)
//                                    }
                                }
                            }
                        }
                    })

                }
            }




        }
        cvGallery.setOnClickListener {
            openGallery()
        }
        ivChatDetailBack.setOnClickListener {
            finish()
        }
    }

    private fun sendMsgFunction(imgList: ArrayList<MediaDataVO>) {
        //Note ---> just only for one photo

        if(mGroupOrPrivateChat == CHAT_TYPE_GROUP)
        {
            mPresenter.sendGroupMessage(
                senderId= mUserVO.id.toString(),
                receiverId= mChatUserId,
                msg= edtTypeMsg.text.toString(),
                senderName= mUserVO.name.toString(),
                fileUrl = imgList.firstOrNull()?.mediaDataLink.toString(),
                profileUrl = mUserVO.profileUrl.toString(),
                onSuccess = {
                    //  showError(it)
                    clearMessageView()

                }

            ) {
                showError(it)
            }
        }else{
            mPresenter.onTapSendMsg(
                senderId= mUserVO.id.toString(),
                receiverId= mChatUserId,
                msg= edtTypeMsg.text.toString(),
                senderName= mUserVO.name.toString(),
                fileUrl = imgList.firstOrNull()?.mediaDataLink.toString(),
                profileUrl = mUserVO.profileUrl.toString(),
                onSuccess = {
                    //  showError(it)
                    clearMessageView()

                }

            ) {
                showError(it)
            }
        }
    }

    private fun clearMessageView() {
        edtTypeMsg.setText("")
        edtTypeMsg.clearFocus()
        imagesEncodedList.clear()
        rvMediaTypeList.visibility = View.GONE
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ChatDetailPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun getIntentData() {
        tvChatName.text = intent?.getStringExtra(BUNDLE_CHAT_USER_NAME).toString()
        mChatUserId = intent?.getStringExtra(BUNDLE_CHAT_USER_ID).toString()
        mGroupOrPrivateChat = intent?.getStringExtra(BUNDLE_GROUP_OR_PRIVATE_CHAT).toString()
        mChatUserProfile = intent?.getStringExtra(BUNDLE_USER_PROFILE).toString()

        var imageUri = mChatUserProfile?.toUri()
        imageUri?.let { image->
            Observable.just(image)
                .map { it.loadBitMapFromUri(this) }
                .map { it.scaleToRatio(0.35) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    ivChatDetailProfile.setImageBitmap(it)
                }
        }
    }


    private fun setUpMessageAdapter() {
        mChatDetailListAdapter =
            ChatDetailListAdapter()
        rvChatDetailList.adapter = mChatDetailListAdapter
        rvChatDetailList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        rvChatDetailList.isNestedScrollingEnabled = false
    }

    private fun setUpBottomButtonLayout() {


        //Add custom tab menu
        /*val galleryView = bottomNavigationChat.getChildAt(0) as BottomNavigationMenuView

        val positionView1 = galleryView.getChildAt(1)
       val cameraView = positionView1 as BottomNavigationItemView

        val positionView2 = galleryView.getChildAt(2)
        val gifView = positionView2 as BottomNavigationItemView

        val positionView3 = galleryView.getChildAt(3)
        val locationView = positionView3 as BottomNavigationItemView


        val positionView4 = galleryView.getChildAt(4)
        val microphoneView = positionView4 as BottomNavigationItemView

        val viewCustom1 = LayoutInflater.from(this).inflate(R.layout.bottom_gallery, galleryView, false)
        bottomNavigationChat.addView(viewCustom1)

        val viewCustom2 = LayoutInflater.from(this).inflate(R.layout.bottom_camera, positionView1, false)
        cameraView.addView(viewCustom2)

        val viewCustom3 = LayoutInflater.from(this).inflate(R.layout.bottom_gif, positionView2, false)
        gifView.addView(viewCustom3)

        val viewCustom4 = LayoutInflater.from(this).inflate(R.layout.bottom_location, positionView3, false)
        locationView.addView(viewCustom4)

        val viewCustom5 = LayoutInflater.from(this).inflate(R.layout.bottom_microphone, positionView4, false)
        microphoneView.addView(viewCustom5)*/
    }

    override fun showChatMessageList(chatMessagesList: List<ChatMessageVO>) {

        mChatDetailListAdapter.setNewData(chatMessagesList.sortedBy { chatMessageVO -> chatMessageVO.timestamp })
    }
//
//    override fun onTapSendMessage(
//        senderId: String,
//        receiverId: String,
//        msg: String,
//        senderName: String
//    ) {
//
//    }

    override fun showError(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }

    private fun openGallery() {
        rvMediaTypeList.visibility = View.VISIBLE
        val intent = Intent()
        //  intent.type = "image/*,video/*"
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture Or Video"),
            PICK_IMAGE_OR_VIDEO_REQUEST
        )
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            // When an Image is picked
            if (requestCode == CreateNewMomentActivity.PICK_IMAGE_OR_VIDEO_REQUEST && resultCode == RESULT_OK && null != data) {


                // Get the Image from data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                if (data.data != null) {
                    val mImageUri = data.data

                    // Get the cursor
                    val cursor: Cursor? = contentResolver?.query(
                        mImageUri!!,
                        filePathColumn, null, null, null
                    )
                    // Move to first row
                    cursor?.moveToFirst()
                    val columnIndex: Int? = cursor?.getColumnIndex(filePathColumn[0])
                    imageEncoded = cursor?.getString(columnIndex!!)
                    mImageUri?.let {
                        imagesEncodedList?.add(it)

//                         mPresenter.uploadFileCreate(it, onSuccess = {uploadedFileUrl->
//                             uploadedFileUrl?.let { imgUrl -> imagesUrlList.add(imgUrl) }
//                         })
                    }
                    mMediaTypeMessageAdapter.setNewData(imagesEncodedList)
                    cursor?.close()
                } else {
                    if (data.clipData != null) {
                        val mClipData = data.clipData

                        for (i in 0 until mClipData!!.itemCount) {
                            val item = mClipData.getItemAt(i)
                            val uri = item.uri
                            //  mArrayUri.add(uri)
                            imagesEncodedList!!.add(uri)

//                             mPresenter.uploadFileCreate(uri, onSuccess = {
//                                 it?.let { imgUrl ->
//                                     imagesUrlList.add(imgUrl)
//
//                                 }
//                             })

                            // Get the cursor
                            val cursor: Cursor? =
                                contentResolver
                                    ?.query(uri, filePathColumn, null, null, null)
                            // Move to first row
                            cursor?.moveToFirst()
                            val columnIndex: Int? = cursor?.getColumnIndex(filePathColumn[0])
                            imageEncoded = cursor?.getString(columnIndex!!)
                            cursor?.close()
                        }
                        mMediaTypeMessageAdapter.setNewData(imagesEncodedList)

                    }
                }
            } else {
                Toast.makeText(
                    this, "You haven't picked Image",
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                .show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}