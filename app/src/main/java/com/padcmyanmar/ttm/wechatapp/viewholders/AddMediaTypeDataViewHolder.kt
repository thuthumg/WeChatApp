package com.padcmyanmar.ttm.wechatapp.viewholders

import android.view.View
import com.padcmyanmar.ttm.wechatapp.delegates.MediaTypeDataDelegate

class AddMediaTypeDataViewHolder(itemView: View, delegate: MediaTypeDataDelegate)
    : BaseViewHolder(itemView,delegate){

   init{
       itemView.setOnClickListener {
           delegate.goToChooseMediaType()
       }
   }

}