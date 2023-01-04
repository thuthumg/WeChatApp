package com.padcmyanmar.ttm.wechatapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.data.vos.ContactsListVO
import com.padcmyanmar.ttm.wechatapp.delegates.ContactListItemDelegate
import com.padcmyanmar.ttm.wechatapp.viewholders.ContactItemViewHolder

class ContactItemAdapter(var mDelegate: ContactListItemDelegate) : RecyclerView.Adapter<ContactItemViewHolder>() {

     // private var mData: List<> = listOf()
    private var mData:ArrayList<ContactsListVO> = arrayListOf()
    // contactsGroupList(it)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_contact_item,parent,false)

        return ContactItemViewHolder(view,mDelegate)
    }

    override fun onBindViewHolder(holder: ContactItemViewHolder, position: Int) {
       if(mData.isNotEmpty()){
           holder.bindData(mData[position])
       }
    }

    override fun getItemCount(): Int {
        return mData.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data:ArrayList<ContactsListVO>){
        mData = data
        notifyDataSetChanged()
    }
}