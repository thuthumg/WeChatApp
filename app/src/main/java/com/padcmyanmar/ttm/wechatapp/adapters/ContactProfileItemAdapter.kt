package com.padcmyanmar.ttm.wechatapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.delegates.ContactListItemDelegate
import com.padcmyanmar.ttm.wechatapp.viewholders.ContactProfileItemViewHolder

class ContactProfileItemAdapter(var mDelegate: ContactListItemDelegate) : RecyclerView.Adapter<ContactProfileItemViewHolder>() {

      private var mData: List<UserVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactProfileItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_contact_profile_item,parent,false)

        return ContactProfileItemViewHolder(view,mDelegate)
    }

    override fun onBindViewHolder(holder: ContactProfileItemViewHolder, position: Int) {
       if(mData.isNotEmpty()){
           holder.bindData(mData[position])
       }
    }

    override fun getItemCount(): Int {
         return mData.count()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data: List<UserVO>){
        mData = data
        notifyDataSetChanged()
    }
}