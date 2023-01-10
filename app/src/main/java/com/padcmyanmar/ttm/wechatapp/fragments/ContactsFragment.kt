package com.padcmyanmar.ttm.wechatapp.fragments

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
//import com.blikoon.qrcodescanner.QrCodeActivity
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.activities.ChatDetailActivity
import com.padcmyanmar.ttm.wechatapp.activities.GroupChatActivity
import com.padcmyanmar.ttm.wechatapp.activities.QRScannerActivity
import com.padcmyanmar.ttm.wechatapp.adapters.ChatGroupsListAdapter
import com.padcmyanmar.ttm.wechatapp.adapters.ContactItemAdapter
import com.padcmyanmar.ttm.wechatapp.data.vos.ChatGroupVO
import com.padcmyanmar.ttm.wechatapp.data.vos.ContactsListVO
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.ContactsFragmentPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls.ContactsFragmentPresenterImpl
import com.padcmyanmar.ttm.wechatapp.mvp.views.ContactsFragmentView
import com.padcmyanmar.ttm.wechatapp.network.auth.PhoneAuth
import com.padcmyanmar.ttm.wechatapp.utils.CHAT_TYPE_GROUP
import com.padcmyanmar.ttm.wechatapp.utils.CHAT_TYPE_PRIVATE
import com.padcmyanmar.ttm.wechatapp.utils.STORAGE_PERMISSIONS
import kotlinx.android.synthetic.main.fragment_contacts.*


class ContactsFragment : Fragment(), ContactsFragmentView {
    lateinit var mPresenter: ContactsFragmentPresenter

    private lateinit var mChatGroupsListAdapter: ChatGroupsListAdapter
    private lateinit var mContactItemAdapter: ContactItemAdapter
    private var contactCount = 0
    private var groupCount = 0
    //  private var REQUEST_CODE_QR_SCAN = 101

    var mContactsList: ArrayList<UserVO> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()

        setUpEmptyChatUI()

        setUpChatGroupAdapter()
        setUpContactsListAdapter()
        clickListener()
        context?.let { mPresenter.onUiReady(it, this) }


    }

    private fun setUpContactCount() {
        if(contactCount > 1)
        {
            tvContactText.text = "Contacts"
            tvContactCount.text = "(${contactCount})"

        }
        else{
            tvContactText.text = "Contact"
            tvContactCount.text = "(${contactCount})"

        }



    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ContactsFragmentPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpEmptyChatUI() {
        val styledText =
            "No contact or group with name <br> \" <font color='#113A5D' size='18px'>Aung Aung</font>\" exits."
        tvEmptyText.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE)

    }

    private fun clickListener() {
        ivEditProfile.setOnClickListener {
            /*  val i = Intent(context, QrCodeActivity::class.java)
              startActivityForResult(i, REQUEST_CODE_QR_SCAN)*/

            qrCode()
        }
    }

    private fun setUpContactsListAdapter() {
        mContactItemAdapter =
            ContactItemAdapter("Contact", mPresenter)
        rvContactsList.adapter = mContactItemAdapter
        rvContactsList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        rvContactsList.isNestedScrollingEnabled = false
    }

    private fun setUpChatGroupAdapter() {
        mChatGroupsListAdapter =
            ChatGroupsListAdapter(mPresenter)
        rvChatGroupsList.adapter = mChatGroupsListAdapter
        rvChatGroupsList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )
         rvChatGroupsList.isNestedScrollingEnabled = false
    }

    private fun qrCode() {

        activity?.let {
            if (hasPermissions(activity as Context, STORAGE_PERMISSIONS)) {
                scanQR()

//                val i = Intent(context, QRScannerActivity::class.java)
//                startActivity(i)
            } else {

                cameraReqLauncher.launch(
                    STORAGE_PERMISSIONS
                )
            }
        }
    }

    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

    private val cameraReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value == true
            }
            if (granted) {
                scanQR()
            }
        }

    private fun scanQR() {
        val intent = Intent(requireContext(), QRScannerActivity::class.java)
        qrLauncher.launch(intent)
    }

    private var qrLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val code =
                    result.data!!.getStringExtra("qr_code")!!

                try {
                    var qrCodeParam = code
                    Log.d("contacts", "check qr code $qrCodeParam")

                    mPresenter.onTapAddContacts(qrCodeParam,
                        onSuccess = {
                            mPresenter.getContactsList(onSuccess = {
                                // set data
                                mContactsList = it
                                Log.d("ContactsFragment","after add contacts ${it.size}")
                              var contactsArrayList:ArrayList<ContactsListVO> = arrayListOf()
                                val bindArrayList= contactsGroupList(it)
                                for (bindData in bindArrayList){
                                    var contactsListVO = ContactsListVO()
                                    contactsListVO.nameFirstCharacter = bindData.key
                                    contactsListVO.usersList = bindData.value
                                    contactsArrayList.add(contactsListVO)
                                }
                                contactCount = contactsArrayList.size
                                setUpContactCount()
                                mContactItemAdapter.setNewData(contactsArrayList)

                            },
                                onFailure = {
                                    showError(it)
                                })


                        }, onFailure = {
                            showError(it)
                        })

                    //  val qrList: List<String> = code.split(",")

                    /* if (checkInOut == 2) {
                         mCheckTimeInOutViewModel.timeOut(
                             attendanceId,
                             qrList[1],
                             qrList[0].toInt(),
                             null,
                             null
                         )
                     } else if (checkInOut == 1) {
                         mCheckTimeInOutViewModel.timeIn(
                             user?.employeeId!!,
                             qrList[1],
                             qrList[0].toInt(),
                             null,
                             null
                         )
                     }*/
                } catch (e: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "error qr code",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    //navigateToChatGroupDetailPage

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
        contactCount = contactsArrayList.size


        setUpContactCount()
        mContactItemAdapter.setNewData(contactsArrayList)


    }

    override fun navigateToChatDetailFromContactPage(
        contactName: String,
        chatId: String,
        contactProfile: String
    ) {
        Log.d("contactsFragment","${contactName}-----${PhoneAuth.getCurrentUser()?.uid}")

        startActivity(ChatDetailActivity.newIntent(
            context = requireContext(),
            chatUserName= contactName,
            chatUserId= chatId,
            chatUserProfile = contactProfile,
            checkGroupOrPrivateChat = CHAT_TYPE_PRIVATE
        ))
//,
//            chatUserPhoneNum = contactVO.phoneNumber.toString()

    }

    override fun navigateToChatGroupCreate(loginUserId: String) {
        startActivity(Intent(context,GroupChatActivity::class.java))
    }

    override fun showChatGroupsList(chatGroupList: ArrayList<ChatGroupVO>) {
        groupCount = chatGroupList.size

        if(groupCount > 1)
        {
            tvGroupCount.text = "Groups(${groupCount})"
        }else{
            tvGroupCount.text = "Group(${groupCount})"
        }

        mChatGroupsListAdapter.setNewData(chatGroupList)
    }

    override fun navigateToChatGroupDetailPage(contactName: String, chatId: String, contactProfile: String) {
        startActivity(ChatDetailActivity.newIntent(
            context = requireContext(),
            chatUserName= contactName,
            chatUserId= chatId,
            chatUserProfile = contactProfile,
            checkGroupOrPrivateChat = CHAT_TYPE_GROUP
        ))
    }


    override fun showError(error: String) {
       Toast.makeText(context,error,Toast.LENGTH_SHORT).show()
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




}