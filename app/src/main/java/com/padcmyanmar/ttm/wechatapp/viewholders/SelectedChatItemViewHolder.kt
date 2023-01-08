package com.padcmyanmar.ttm.wechatapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.ttm.wechatapp.R
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

        Glide.with(itemView.context)
            .load(userVO.profileUrl)
            .placeholder(R.drawable.profile_sample)
            .into(itemView.tvSelectedContactProfile)
    }

}