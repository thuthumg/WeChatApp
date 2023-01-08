package com.padcmyanmar.ttm.wechatapp.viewholders

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.adapters.MomentImageListAdapter
import com.padcmyanmar.ttm.wechatapp.data.vos.MomentVO
import com.padcmyanmar.ttm.wechatapp.delegates.MomentItemDelegate
import com.padcmyanmar.ttm.wechatapp.utils.covertTimeToText
import kotlinx.android.synthetic.main.view_holder_moment_item.view.*

class MomentsListViewHolder(itemView: View, private var mDelegate: MomentItemDelegate) : RecyclerView.ViewHolder(itemView){
   private lateinit var mMomentImageListAdapter:MomentImageListAdapter
   var mMomentVO:MomentVO = MomentVO()
   init{
      itemView.ivFavorite.setOnClickListener {
         mDelegate.onTapFavorite(mMomentVO)
      }
      itemView.ivBookMark.setOnClickListener {
         mDelegate.onTapBookMark(mMomentVO)
      }
    //  setUpMomentImageAdapter()
   }

   private fun setUpMomentImageAdapter() {
      mMomentImageListAdapter = MomentImageListAdapter()
      itemView.rvPhotoList.adapter = mMomentImageListAdapter
      itemView.rvPhotoList.layoutManager = LinearLayoutManager(itemView.context,
         LinearLayoutManager.HORIZONTAL,false)
      itemView.rvPhotoList.isNestedScrollingEnabled = false
   }


    fun bindData(position: Int, data: MomentVO, loginUserPhoneNo: String?){
       mMomentVO = data

       setUpMomentImageAdapter()
       itemView.tvNameTitle.text = data.name
       itemView.tvDescription.text = data.description

       itemView.tvPostedTime.text =  covertTimeToText(data.timestamp)

       data.photoOrVideoUrlLink?.let { mMomentImageListAdapter.setNewData(it) }

       mMomentVO.likedId.let {
             if(it?.size != 0)
             {
                outerLoop@for (likeUser in it!!)
                {
                   if(likeUser == loginUserPhoneNo)
                   {
                      itemView.ivFavorite.setBackgroundResource(R.drawable.ic_favorite_fill)
                      break@outerLoop
                   }
                   else
                   {
                      itemView.ivFavorite.setBackgroundResource(R.drawable.ic_favorite_light)

                   }
                }

             }else{
                itemView.ivFavorite.setBackgroundResource(R.drawable.ic_favorite_light)

             }

          }
       itemView.tvChatCount.text = "0"
       itemView.tvFavoriteCount.text = mMomentVO.likedId?.size.toString()

       mMomentVO.bookMarkedId.let {
          if(it?.size != 0)
          {
             outerLoop@for (bookMarkUser in it!!)
             {
                if(bookMarkUser == loginUserPhoneNo)
                {
                   itemView.ivBookMark.setBackgroundResource(R.drawable.ic_bookmark_fill)
                   break@outerLoop
                }
                else
                {
                   itemView.ivBookMark.setBackgroundResource(R.drawable.ic_bookmark_light)

                }
             }

          }else{
             itemView.ivBookMark.setBackgroundResource(R.drawable.ic_bookmark_light)

          }

       }

       Glide.with(itemView.context)
          .load(mMomentVO.profileUrl)
          .placeholder(R.drawable.empty_image)
          .into(itemView.ivMomentProfileImage)


      /*itemView.ivFavorite.setOnClickListener {
          mDelegate.onTapFavorite(mMomentVO)
       }*/
    }

}