package com.padcmyanmar.ttm.wechatapp.viewholders

import android.view.View
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.delegates.ContactListItemDelegate
import kotlinx.android.synthetic.main.view_holder_contact_profile_item.view.tvContactProfileName
import kotlinx.android.synthetic.main.view_holder_contact_profile_item_with_checkbox.view.*


class ContactProfileItemWithCheckBoxViewHolder(itemView: View, mDelegate: ContactListItemDelegate) : BaseViewHolder<UserVO>(itemView,null){

    var mContactVO:UserVO = UserVO()
   init{
       itemView.chkContactProfile.setOnClickListener {
           mContactVO.isSelected = itemView.chkContactProfile.isChecked
           mDelegate.onTapSelectContactList(mContactVO)


       }
   }

    override fun bindData(data: UserVO?) {
        mContactVO = data!!
        itemView.tvContactProfileName.text = data?.name
        itemView.chkContactProfile.isChecked = data.isSelected

    }
}