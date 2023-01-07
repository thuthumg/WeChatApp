package com.padcmyanmar.ttm.wechatapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.adapters.ContactItemAdapter
import com.padcmyanmar.ttm.wechatapp.adapters.SelectedChatItemListAdapter
import com.padcmyanmar.ttm.wechatapp.data.vos.ContactsListVO
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.GroupChatPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls.GroupChatPresenterImpl
import com.padcmyanmar.ttm.wechatapp.mvp.views.GroupChatView
import com.padcmyanmar.ttm.wechatapp.utils.mUserVO
import kotlinx.android.synthetic.main.activity_group_chat_list.*
import kotlinx.android.synthetic.main.activity_group_chat_list.btnClose
import kotlinx.android.synthetic.main.activity_group_chat_list.rvContactsList


class GroupChatActivity : AppCompatActivity(), GroupChatView {

    lateinit var mPresenter:GroupChatPresenter
    var mContactsList: ArrayList<UserVO> = arrayListOf()
    var mSelectedContactsList:ArrayList<UserVO> = arrayListOf()
    lateinit var mSelectedChatItemListAdapter: SelectedChatItemListAdapter
    private lateinit var mContactItemAdapter:ContactItemAdapter

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

                mPresenter.onTapCreateChatGroup(
                    groupName = edtGroupName.text.toString(),
                    membersList = membersList,
                    groupPhoto = "",
                    onSuccess = {
                        showError(it)
                        finish()
                    },
                    onFailure = {
                        showError(it)
                    }
                )
            }
        }

        btnClose.setOnClickListener {
            finish()
        }
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[GroupChatPresenterImpl::class.java]
        mPresenter.initPresenter(this)
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
            ContactItemAdapter("Group",mPresenter)
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
        Log.d("groupchat","check select obj data ${contactVO.isSelected}")
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
}