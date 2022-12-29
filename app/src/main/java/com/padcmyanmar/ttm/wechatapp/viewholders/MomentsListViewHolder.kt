package com.padcmyanmar.ttm.wechatapp.viewholders

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.adapters.MomentImageListAdapter
import com.padcmyanmar.ttm.wechatapp.data.vos.MomentVO
import com.padcmyanmar.ttm.wechatapp.delegates.MomentItemDelegate
import com.padcmyanmar.ttm.wechatapp.utils.covertTimeToText
import kotlinx.android.synthetic.main.view_holder_moment_item.view.*

class MomentsListViewHolder(itemView: View, private var mDelegate: MomentItemDelegate) : RecyclerView.ViewHolder(itemView){
   private lateinit var mMomentImageListAdapter:MomentImageListAdapter
   var paramPosition = 0
   var mMomentVO:MomentVO = MomentVO()
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


    fun bindData(position:Int,data:MomentVO){
       mMomentVO = data
       paramPosition = position %2

       setUpMomentImageAdapter(paramPosition)
       itemView.tvNameTitle.text = data.name
       itemView.tvDescription.text = data.description
       data.photoOrVideoUrlLink?.let { mMomentImageListAdapter.setNewData(it) }
       itemView.tvPostedTime.text =  covertTimeToText(data.timestamp)
        //  Log.d("momentsfragment","check time ago = ${covertTimeToText("2022-12-29T15:01:35")}")


       mMomentVO.likedId.let {
          if(it?.size != 0)
          {
            outerLoop@for (likeUser in it!!)
             {
                if(likeUser == mMomentVO.phoneNumber)
                {
                   itemView.ivFavorite.setBackgroundResource(R.drawable.ic_favorite_fill)
                  break@outerLoop
                }
                else
                {
                   itemView.ivFavorite.setBackgroundResource(R.drawable.ic_favorite_light)

                }
             }
          }
       }

       itemView.ivFavorite.setOnClickListener {
          mDelegate.onTapFavorite(mMomentVO.id,mMomentVO.phoneNumber)
       }
    }

}