package com.padcmyanmar.ttm.wechatapp.viewholders

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.adapters.MomentImageListAdapter
import kotlinx.android.synthetic.main.view_holder_moment_item.view.*

class MomentsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
   private lateinit var mMomentImageListAdapter:MomentImageListAdapter
   var paramPosition = 0
   init{

    //  setUpMomentImageAdapter()
   }

   private fun setUpMomentImageAdapter(paramPosition: Int) {
      mMomentImageListAdapter = MomentImageListAdapter(paramPosition)
      itemView.rvPhotoList.adapter = mMomentImageListAdapter
      itemView.rvPhotoList.layoutManager = LinearLayoutManager(itemView.context,
         LinearLayoutManager.HORIZONTAL,false)
      itemView.rvPhotoList.isNestedScrollingEnabled = false
   }


    fun bindData(position:Int){
       paramPosition = position %2

       setUpMomentImageAdapter(paramPosition)
    }

//    fun bindData(data:MomentsVO){
//
//    }
}