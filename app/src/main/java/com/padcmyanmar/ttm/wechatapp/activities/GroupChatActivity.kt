package com.padcmyanmar.ttm.wechatapp.activities

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.adapters.ContactItemAdapter
import com.padcmyanmar.ttm.wechatapp.adapters.SelectedChatItemListAdapter
import com.padcmyanmar.ttm.wechatapp.data.vos.ContactsListVO
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.GroupChatPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls.CreateNewMomentPresenterImpl
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls.GroupChatPresenterImpl
import com.padcmyanmar.ttm.wechatapp.mvp.views.CreateNewMomentView
import com.padcmyanmar.ttm.wechatapp.mvp.views.GroupChatView
import com.padcmyanmar.ttm.wechatapp.utils.CHAT_TYPE_GROUP
import com.padcmyanmar.ttm.wechatapp.utils.mUserVO
import kotlinx.android.synthetic.main.activity_group_chat_list.*
import kotlinx.android.synthetic.main.activity_group_chat_list.btnClose
import kotlinx.android.synthetic.main.activity_group_chat_list.rvContactsList



class GroupChatActivity : BaseActivity(), GroupChatView {

    lateinit var mPresenter:GroupChatPresenter
    var mContactsList: ArrayList<UserVO> = arrayListOf()
    var mSelectedContactsList:ArrayList<UserVO> = arrayListOf()
    lateinit var mSelectedChatItemListAdapter: SelectedChatItemListAdapter
    private lateinit var mContactItemAdapter:ContactItemAdapter
    val PICK_IMAGE_REQUEST_FOR_PROFILE = 100
    var imageEncoded: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_chat_list)

        setUpPresenter()
        setUpSelectedChatItemListAdapter()
        setUpContactsListItemAdapter()
        setUpCreateButton()
        clickListener()

        mPresenter.onUiReady(this,this)

    }

    private fun setUpCreateButton() {
        btnChatGroupCreate.isEnabled = false
        btnChatGroupCreate.background = ContextCompat.getDrawable(this, R.drawable.login_btn_disable_bg)

        txtInputGroupName.setEndIconOnClickListener {

            edtGroupName.setText("")
            btnChatGroupCreate.isEnabled = false
            btnChatGroupCreate.background = ContextCompat.getDrawable(this, R.drawable.login_btn_disable_bg)
        }

        edtGroupName.addTextChangedListener(object : TextWatcher {
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
                    btnChatGroupCreate.isEnabled = true
                    btnChatGroupCreate.background = ContextCompat.getDrawable(
                        this@GroupChatActivity,
                        R.drawable.login_btn_bg
                    )
                }
            }
        })

    }

    private fun clickListener() {
        btnChatGroupCreate.setOnClickListener {

            if(edtGroupName.text.toString().isEmpty())
            {
                showError("Please Fill the Group Name")
            }else if(mSelectedContactsList.isEmpty()){
                showError("Please select the Contact")
            }
            else{
                var membersList:ArrayList<String> = arrayListOf()

                mSelectedContactsList.forEach {
                    membersList.add(it.id.toString())
                }
                membersList.add(mUserVO.id.toString())
                imageEncoded?.let { it1 ->
                    mPresenter.uploadImageAndVideoFile(it1) { urlString->
                        mPresenter.onTapCreateChatGroup(
                            groupName = edtGroupName.text.toString(),
                            membersList = membersList,
                            groupPhoto = urlString,
                            onSuccess = {
                                showError(it)
                                finish()
                            }
                        , onFailure = {
                            showError(it)
                            })
                    }
                }

            }
        }

        btnClose.setOnClickListener {
            finish()
        }
        btnGroupPhotoUpload.setOnClickListener {
            openGallery()
        }
    }

    private fun setUpPresenter() {
      //  mPresenter = ViewModelProvider(this)[GroupChatPresenterImpl::class.java]
      //  mPresenter.initPresenter(this)

        mPresenter = getPresenter<GroupChatPresenterImpl, GroupChatView>()
    }


    private fun setUpContactsListItemAdapter() {
        mSelectedChatItemListAdapter =
            SelectedChatItemListAdapter(mPresenter)
        rvSelectedChatList.adapter = mSelectedChatItemListAdapter
        rvSelectedChatList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )
    }

    private fun setUpSelectedChatItemListAdapter() {
        mContactItemAdapter =
            ContactItemAdapter(CHAT_TYPE_GROUP,mPresenter)
        rvContactsList.adapter = mContactItemAdapter
        rvContactsList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
    }



    override fun showContactsData(contactsList: ArrayList<UserVO>) {
        Log.d("ContactsFragment", "check contacts list = ${contactsList.size}")
        mContactsList = contactsList
        var contactsArrayList:ArrayList<ContactsListVO> = arrayListOf()
        val bindArrayList= contactsGroupList(contactsList)
        for (bindData in bindArrayList){
            var contactsListVO = ContactsListVO()
            contactsListVO.nameFirstCharacter = bindData.key
            contactsListVO.usersList = bindData.value
            contactsArrayList.add(contactsListVO)
        }

        mContactItemAdapter.setNewData(contactsArrayList)


    }

    override fun selectContactItem(contactName: String) {

    }


    override fun onTapSelectContactList(contactVO: UserVO) {
       if(mSelectedContactsList.isNotEmpty())
       {

           if(mSelectedContactsList.contains(contactVO))
           {
               if(!(contactVO.isSelected)){
                   mSelectedContactsList.remove(contactVO)               }


           }else{
               mSelectedContactsList.add(contactVO)
           }

       }else{
           mSelectedContactsList.add(contactVO)
       }


        mSelectedChatItemListAdapter.setNewData(mSelectedContactsList)

    }

    override fun onTapSelectContactCancel(contactVO: UserVO) {
        if(mSelectedContactsList.isNotEmpty())
        {
            var mContactsUpdateList: ArrayList<UserVO> = arrayListOf()

            for(contactObj in mContactsList)
            {
                if(contactObj.id  == contactVO.id)
                {
                    contactObj.isSelected = false
                }
                mContactsUpdateList.add(contactObj)
            }

            addDataToContactItemAdapter(mContactsUpdateList)

           mSelectedContactsList.remove(contactVO)
        }
        mSelectedChatItemListAdapter.setNewData(mSelectedContactsList)
    }

    override fun createGroupSuccess(msg: String) {
        showError(msg)
        finish()
    }

    private fun addDataToContactItemAdapter(mContactsUpdateList: ArrayList<UserVO>) {
        var contactsArrayList:ArrayList<ContactsListVO> = arrayListOf()
        val bindArrayList= contactsGroupList(mContactsUpdateList)
        for (bindData in bindArrayList){
            var contactsListVO = ContactsListVO()
            contactsListVO.nameFirstCharacter = bindData.key
            contactsListVO.usersList = bindData.value
            contactsArrayList.add(contactsListVO)
        }

        mContactItemAdapter.setNewData(contactsArrayList)
    }
    override fun showError(error: String) {
        Toast.makeText(this, error,Toast.LENGTH_SHORT).show()
    }
    private fun contactsGroupList(contactsList: ArrayList<UserVO>): HashMap<String, ArrayList<UserVO>> {
        val arrayList= HashMap<String,ArrayList<UserVO>>()
        for (userItem in contactsList){
            val nameFirstCharacter=userItem.name?.subSequence(0,1).toString()
            if (arrayList.isEmpty()){
                val nameList= arrayListOf<UserVO>()
                nameList.add(userItem)
                arrayList[nameFirstCharacter]=nameList
            }else{
                var customGroupList= arrayListOf<UserVO>()
                if (arrayList[nameFirstCharacter].isNullOrEmpty()){
                    customGroupList.add(userItem)
                }else{
                    customGroupList= arrayList[nameFirstCharacter]!!
                    customGroupList.add(userItem)
                }
                if (customGroupList.isNotEmpty()) {
                    arrayList[nameFirstCharacter] = customGroupList
                }
            }
        }
        Log.d("LogData",arrayList.toString())
        return arrayList
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_FOR_PROFILE)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_REQUEST_FOR_PROFILE && resultCode == RESULT_OK && null != data) {


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
                   // imageEncoded = cursor?.getString(columnIndex!!)

                    mImageUri?.let {
                        imageEncoded = it
                        //imagesEncodedList?.add(it)
                        Glide.with(this)
                            .load(it)
                            .placeholder(R.drawable.profile_sample)
                            .into(ivGroupPhotoUpload)
//                         mPresenter.uploadFileCreate(it, onSuccess = {uploadedFileUrl->
//                             uploadedFileUrl?.let { imgUrl -> imagesUrlList.add(imgUrl) }
//                         })
                    }

                    cursor?.close()
                } else {
                    if (data.clipData != null) {
                        val mClipData = data.clipData

                        for (i in 0 until mClipData!!.itemCount) {
                            val item = mClipData.getItemAt(i)
                            val uri = item.uri
                            imageEncoded = uri
                            //  mArrayUri.add(uri)
                           // imagesEncodedList!!.add(uri)
                            Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.profile_sample)
                                .into(ivGroupPhotoUpload)
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
                           // imageEncoded = cursor?.getString(columnIndex!!)
                            cursor?.close()
                        }


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