package com.padcmyanmar.ttm.wechatapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.delegates.ContactListItemDelegate
import kotlinx.android.synthetic.main.view_holder_contact_profile_item.view.*

class ContactProfileItemViewHolder(itemView: View, var mDelegate: ContactListItemDelegate) : RecyclerView.ViewHolder(itemView){

    var contactVO:UserVO = UserVO()

    init{
            itemView.clContact.setOnClickListener {
                mDelegate.goToChatDetailFromContactList(contactVO)
            }
    }

    fun bindData(data:UserVO){
        contactVO = data
        itemView.tvContactProfileName.text = data.name
    }
}