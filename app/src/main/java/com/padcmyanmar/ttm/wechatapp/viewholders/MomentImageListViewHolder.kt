package com.padcmyanmar.ttm.wechatapp.viewholders

import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.utils.loadBitMapFromUri
import com.padcmyanmar.ttm.wechatapp.utils.scaleToRatio
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.view_holder_image_item.view.*
import kotlinx.android.synthetic.main.view_holder_media_item_layout.view.*

class MomentImageListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

   init{
   }

    fun bindData(data:String){



        var imageUri = data.toUri()
        imageUri?.let { image->
            Observable.just(image)
                .map { it.loadBitMapFromUri(itemView.context) }
                .map { it.scaleToRatio(0.35) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    val w: Int = it.width
                    val h: Int = it.height


                    if(w > h)
                    {
                        itemView.ivMomentPhoto.layoutParams.width = 400
                        itemView.ivMomentPhoto.layoutParams.height = 300
                    }else{
                        itemView.ivMomentPhoto.layoutParams.width = 350
                        itemView.ivMomentPhoto.layoutParams.height = 400
                    }

                    itemView.ivMomentPhoto.setImageBitmap(it)
                }

        }

//        Glide.with(itemView.context)
//            .load(data)
//            .into(itemView.ivMomentPhoto)

//        if(paramPosition == 0)
//        {
//
//            val layoutParams: ViewGroup.LayoutParams = holder.itemView.mcvImage.layoutParams
//            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
//            holder.itemView.mcvImage.layoutParams = layoutParams
//        }else{
//            val layoutParams: ViewGroup.LayoutParams = holder.itemView.mcvImage.layoutParams
//            layoutParams.width = 300
//            holder.itemView.mcvImage.layoutParams = layoutParams
//        }
    }
}