package com.padcmyanmar.ttm.wechatapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.delegates.SelectedContactListItemDelegate
import kotlinx.android.synthetic.main.view_holder_chat_item.view.*


class SelectedChatItemViewHolder(itemView: View, mDelegate: SelectedContactListItemDelegate) : RecyclerView.ViewHolder(itemView){

    var mContactVO:UserVO = UserVO()
    init{
        itemView.ivCancel.setOnClickListener {
            mDelegate.onTapSelectContactCancel(mContactVO)
        }

   }

    fun bindData(userVO: UserVO) {
        mContactVO = userVO
        itemView.tvSelectedContactName.text = userVO.name
    }

}