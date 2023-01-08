package com.padcmyanmar.ttm.wechatapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.delegates.ContactListItemDelegate
import com.padcmyanmar.ttm.wechatapp.utils.CHAT_TYPE_GROUP
import com.padcmyanmar.ttm.wechatapp.utils.VIEW_TYPE_CONTACT
import com.padcmyanmar.ttm.wechatapp.utils.VIEW_TYPE_GROUP_SELECT
import com.padcmyanmar.ttm.wechatapp.viewholders.BaseViewHolder
import com.padcmyanmar.ttm.wechatapp.viewholders.ContactProfileItemViewHolder
import com.padcmyanmar.ttm.wechatapp.viewholders.ContactProfileItemWithCheckBoxViewHolder

class ContactProfileItemAdapter(var changeUIView:String,var mDelegate: ContactListItemDelegate) : RecyclerView.Adapter<BaseViewHolder<UserVO>>() {

      private var mData: List<UserVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<UserVO> {

        when(viewType)
        {
            VIEW_TYPE_GROUP_SELECT->{
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_holder_contact_profile_item_with_checkbox,parent,false)
                return ContactProfileItemWithCheckBoxViewHolder(view,mDelegate)

            }
            VIEW_TYPE_CONTACT->{
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_holder_contact_profile_item,parent,false)

                return ContactProfileItemViewHolder(view,mDelegate)
            }else->{
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_contact_profile_item,parent,false)

            return   return ContactProfileItemViewHolder(view,mDelegate)
            }
        }

    }

    override fun onBindViewHolder(holder: BaseViewHolder<UserVO>, position: Int) {

        when(holder.itemViewType)
        {
            VIEW_TYPE_GROUP_SELECT->{
                var mContactProfileItemWithCheckBoxViewHolder: ContactProfileItemWithCheckBoxViewHolder =
                    holder as ContactProfileItemWithCheckBoxViewHolder
                mContactProfileItemWithCheckBoxViewHolder.bindData(mData[position])

            }
            VIEW_TYPE_CONTACT->{
                var mContactProfileItemViewHolder : ContactProfileItemViewHolder = holder as ContactProfileItemViewHolder
                mContactProfileItemViewHolder.bindData(mData[position])
            }

        }

//       if(mData.isNotEmpty()){
//           holder.bindData(mData[position])
//       }
    }

    override fun getItemCount(): Int {
         return mData.count()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data: List<UserVO>){
        mData = data
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
       return if(changeUIView == CHAT_TYPE_GROUP)
       {
           VIEW_TYPE_GROUP_SELECT
       }else{
           VIEW_TYPE_CONTACT
       }
    }

}