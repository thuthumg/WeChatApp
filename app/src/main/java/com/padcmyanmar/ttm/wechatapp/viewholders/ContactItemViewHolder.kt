package com.padcmyanmar.ttm.wechatapp.viewholders

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.adapters.ContactProfileItemAdapter
import com.padcmyanmar.ttm.wechatapp.data.vos.ContactsListVO
import com.padcmyanmar.ttm.wechatapp.delegates.ContactListItemDelegate
import kotlinx.android.synthetic.main.view_holder_contact_item.view.*


class ContactItemViewHolder(itemView: View,var changeUIView:String, var mDelegate: ContactListItemDelegate) : RecyclerView.ViewHolder(itemView){

    lateinit var mContactProfileItemAdapter: ContactProfileItemAdapter

    init{
        setUpContactsProfileListAdapter()
    }

    private fun setUpContactsProfileListAdapter() {
        mContactProfileItemAdapter =
            ContactProfileItemAdapter(changeUIView,mDelegate)
        itemView.rvContactProfileList.adapter = mContactProfileItemAdapter
        itemView.rvContactProfileList.layoutManager = LinearLayoutManager(
            itemView.context,
            LinearLayoutManager.VERTICAL, false
        )
    }

    fun bindData(contactsListVO: ContactsListVO) {
        itemView.tvContactNameFirstCharacter.text = contactsListVO.nameFirstCharacter

        contactsListVO.usersList?.let { mContactProfileItemAdapter.setNewData(it) }
    }

}