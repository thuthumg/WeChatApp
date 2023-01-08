package com.padcmyanmar.ttm.wechatapp.viewholders

import android.view.View
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.delegates.ContactListItemDelegate
import com.padcmyanmar.ttm.wechatapp.utils.loadBitMapFromUri
import com.padcmyanmar.ttm.wechatapp.utils.scaleToRatio
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.view_holder_chat_group_item.view.*
import kotlinx.android.synthetic.main.view_holder_contact_profile_item.view.*

class ContactProfileItemViewHolder(itemView: View, var mDelegate: ContactListItemDelegate) : BaseViewHolder<UserVO>(itemView,null){

    var contactVO:UserVO = UserVO()

    init{
            itemView.clContact.setOnClickListener {
                mDelegate.goToChatDetailFromContactList(contactVO.name.toString(),
                    contactVO.id.toString(),
                contactVO.profileUrl.toString())
            }
    }


    override fun bindData(data: UserVO?) {
        if (data != null) {
            contactVO = data
        }
        itemView.tvContactProfileName.text = data?.name


//        var imageUri = data?.profileUrl?.toUri()
//        imageUri?.let { image->
//            Observable.just(image)
//                .map { it.loadBitMapFromUri(itemView.context) }
//                .map { it.scaleToRatio(0.35) }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    itemView.ivContactProfilePhoto.setImageBitmap(it)
//                }
//        }


        Glide.with(itemView.context)
            .load(data?.profileUrl)
            .placeholder(R.drawable.empty_image)
            .into(itemView.ivContactProfilePhoto)
    }
}