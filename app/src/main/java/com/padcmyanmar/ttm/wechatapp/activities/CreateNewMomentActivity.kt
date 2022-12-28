package com.padcmyanmar.ttm.wechatapp.activities

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager

import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.adapters.MediaTypeDataAdapter
import com.padcmyanmar.ttm.wechatapp.data.vos.MomentVO

import com.padcmyanmar.ttm.wechatapp.mvp.presenters.CreateNewMomentPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls.CreateNewMomentPresenterImpl
import com.padcmyanmar.ttm.wechatapp.mvp.views.CreateNewMomentView
import kotlinx.android.synthetic.main.activity_create_new_moment.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.content_create_moment_layout.*
import kotlinx.android.synthetic.main.fragment_moments.*
import java.io.File

class CreateNewMomentActivity : BaseActivity(),CreateNewMomentView {

    lateinit var mPresenter: CreateNewMomentPresenter

    var imageEncoded: String? = null
    var imagesEncodedList: ArrayList<Uri>? = arrayListOf()
    var imagesUrlList: ArrayList<String>? = arrayListOf()

    companion object {
        var PICK_IMAGE_OR_VIDEO_REQUEST = 100


        fun Context.getFileExtension(uri: Uri): String? = when (uri.scheme) {
            // get file extension
            ContentResolver.SCHEME_FILE -> File(uri.path!!).extension
            // get actual name of file
            //ContentResolver.SCHEME_FILE -> File(uri.path!!).name
            ContentResolver.SCHEME_CONTENT -> getCursorContent(uri)
            else -> null
        }

        private fun Context.getCursorContent(uri: Uri): String? = try {
            contentResolver.query(uri, null, null, null, null)?.let { cursor ->
                cursor.run {
                    val mimeTypeMap: MimeTypeMap = MimeTypeMap.getSingleton()
                    if (moveToFirst()) mimeTypeMap.getExtensionFromMimeType(
                        contentResolver.getType(
                            uri
                        )
                    )
                    // case for get actual name of file
                    //if (moveToFirst()) getString(getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    else null
                }.also { cursor.close() }
            }
        } catch (e: Exception) {
            null
        }

    }

    lateinit var mMediaTypeDataAdapter: MediaTypeDataAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_moment)

        setUpPresenter()
        setUpDescriptionText()
        setUpMediaDataAdapter()
        clickListener()
    }

    private fun setUpDescriptionText() {

        btnCreate.isEnabled = false
        btnCreate.background = ContextCompat.getDrawable(this, R.drawable.login_btn_disable_bg)

        edtDescription.addTextChangedListener(object : TextWatcher {
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
                if (s.isNotEmpty()) {
                    btnCreate.isEnabled = true
                    btnCreate.background = ContextCompat.getDrawable(
                        this@CreateNewMomentActivity,
                        R.drawable.login_btn_bg
                    )
                }

            }
        })
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<CreateNewMomentPresenterImpl,CreateNewMomentView>()
    }

    private fun clickListener() {

        btnCreate.setOnClickListener {
            var count = 0
            if(edtDescription.text.toString().isNotEmpty() || (imagesEncodedList?.size!! >= 1))
            {
                var bitmap: Bitmap? = null
                //to upload photo video
                for (imageUri in imagesEncodedList!!)
                {
                    //  val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(imageUri))
                    mPresenter.uploadFileCreate(imageUri, onSuccess = {
                        it?.let { it1 -> imagesUrlList?.add(it1)

                        }
                    })


                    /*  if(getFileExtension(imageUri) == ".jpg" ||
                          getFileExtension(imageUri) == ".png" ||
                          getFileExtension(imageUri) == ".jpeg")

                      {
                          if (Build.VERSION.SDK_INT >= 29) {

                              contentResolver?.let {
                                  val source: ImageDecoder.Source =
                                      ImageDecoder.createSource(it, imageUri)
                                  // pbLoading.visibility = View.VISIBLE
                                  bitmap = ImageDecoder.decodeBitmap(source)
                                  /*  mPresenter.onPhotoTaken(bitmap, onSuccess = {
                                        mImageUrl = it
                                        // pbLoading.visibility = View.GONE
                                    })*/

                              }


                          } else {
                              // pbLoading.visibility = View.VISIBLE
                              bitmap = MediaStore.Images.Media.getBitmap(
                                  applicationContext?.contentResolver, imageUri
                              )
                              /* mPresenter.onPhotoTaken(bitmap, onSuccess = {
                                   mImageUrl = it
                                   // pbLoading.visibility = View.GONE
                               })*/

                          }
                          bitmap?.let { it1 ->
                              mPresenter.onTapCreate(it1, onSuccess = {
                                  it?.let { it1 -> imagesUrlList?.add(it1)

                                  }
                              })
                          }
                      }else{
                          mPresenter.onTapVideoCreate(imageUri, onSuccess = {
                              it?.let { it1 -> imagesUrlList?.add(it1)

                              }
                          })
                      }*/



                    count +=1
                }

                if(count == (imagesEncodedList?.size?.minus(1)))
                {

                    imagesUrlList?.let { imgList ->
                        mPresenter.onTapCreate(
                            imgList,edtDescription.text.toString(), onSuccess = {
                                finish()
                            },
                            onFailure = {
                                showError(it)
                            })
                    }
                }

            }else{
                showError("Please fill the name and password.")
            }


        }
        btnClose.setOnClickListener {
            mPresenter.onTapClose()
        }
    }

    private fun setUpMediaDataAdapter() {
        mMediaTypeDataAdapter = MediaTypeDataAdapter(mPresenter)

        rvMediaTypeList.adapter = mMediaTypeDataAdapter
        rvMediaTypeList.layoutManager = GridLayoutManager(
            this, 3
        )
    }

    private fun openGallery() {
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
            if (requestCode == PICK_IMAGE_OR_VIDEO_REQUEST && resultCode == RESULT_OK && null != data) {
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
                    mImageUri?.let { imagesEncodedList?.add(it) }
                    mMediaTypeDataAdapter.setNewData(imagesEncodedList!!)
                    cursor?.close()
                } else {
                    if (data.clipData != null) {
                        val mClipData = data.clipData

                        for (i in 0 until mClipData!!.itemCount) {
                            val item = mClipData.getItemAt(i)
                            val uri = item.uri
                            //  mArrayUri.add(uri)
                            imagesEncodedList!!.add(uri)
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
                        Log.d("LOG_TAG", "Selected Images" + imagesEncodedList?.size)
                        mMediaTypeDataAdapter.setNewData(imagesEncodedList!!)

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

    override fun navigateToChoosePhotoAndVideo() {
        openGallery()
    }

    override fun createFunction() {

    }

    override fun closeFunction() {
        hideKeyboard(this)
        finish()
    }

}
