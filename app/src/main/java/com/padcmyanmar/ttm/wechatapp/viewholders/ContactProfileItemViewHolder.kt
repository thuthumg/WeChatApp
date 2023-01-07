package com.padcmyanmar.ttm.wechatapp.viewholders

import android.view.View
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.delegates.ContactListItemDelegate
import kotlinx.android.synthetic.main.view_holder_contact_profile_item.view.*

class ContactProfileItemViewHolder(itemView: View, var mDelegate: ContactListItemDelegate) : BaseViewHolder<UserVO>(itemView,null){

    var contactVO:UserVO = UserVO()

    init{
            itemView.clContact.setOnClickListener {
                mDelegate.goToChatDetailFromContactList(contactVO.name.toString(),contactVO.id.toString())
            }
    }


    override fun bindData(data: UserVO?) {
        if (data != null) {
            contactVO = data
        }
        itemView.tvContactProfileName.text = data?.name
    }
}