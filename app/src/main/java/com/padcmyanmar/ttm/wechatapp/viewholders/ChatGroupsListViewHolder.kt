package com.padcmyanmar.ttm.wechatapp.viewholders

import android.view.View
import androidx.core.net.toUri
import com.padcmyanmar.ttm.wechatapp.data.vos.ChatGroupVO
import com.padcmyanmar.ttm.wechatapp.delegates.ContactListItemDelegate
import com.padcmyanmar.ttm.wechatapp.utils.CHAT_TYPE_GROUP
import com.padcmyanmar.ttm.wechatapp.utils.loadBitMapFromUri
import com.padcmyanmar.ttm.wechatapp.utils.scaleToRatio
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.view_holder_chat_group_item.view.*


class ChatGroupsListViewHolder(itemView: View, mDelegate: ContactListItemDelegate) :BaseViewHolderForChatGroup(itemView){
    var mContactVO:ChatGroupVO = ChatGroupVO()
    init{
        itemView.setOnClickListener {
              mDelegate.goToChatDetailFromContactGroupList(mContactVO.name.toString(),mContactVO.id.toString(),
                  mContactVO.profileUrl.toString(),
                  CHAT_TYPE_GROUP)
        }
    }

    fun bindData(data: ChatGroupVO){
        mContactVO = data
        itemView.tvGroupName.text = mContactVO.name.toString()

        var imageUri = data.profileUrl?.toUri()
        imageUri?.let { image->
            Observable.just(image)
                .map { it.loadBitMapFromUri(itemView.context) }
                .map { it.scaleToRatio(0.35) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    itemView.ivGroupProfile.setImageBitmap(it)
                }
        }

    }
}