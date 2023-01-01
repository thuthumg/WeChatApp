package com.padcmyanmar.ttm.wechatapp.viewholders

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.data.vos.MediaDataVO
import com.padcmyanmar.ttm.wechatapp.utils.loadBitMapFromUri
import com.padcmyanmar.ttm.wechatapp.utils.scaleToRatio
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.view_holder_image_item.view.*

class MomentImageListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

   init{
   }

    fun bindData(data: MediaDataVO, arrayCount: Int){

        var imageUri = data.mediaDataLink?.toUri()

        Log.d("momentimage","${data.mediaType}")

        if(data.mediaType == "jpg" ||
            data.mediaType == "png" ||
            data.mediaType == "jpeg") {
            itemView.flVideo.visibility = View.GONE



        }
        else{
            itemView.flVideo.visibility = View.VISIBLE

          /*  Glide
                .with(itemView.context)
                .asBitmap()
                .load(imageUri)
                .into(itemView.ivMomentVideo)*/
//
//
//
//            Glide.with(itemView.context).load(imageUri).thumbnail(0.1f)
//                .into(itemView.ivMomentVideo)



        }
        imageUri?.let { image->
            Observable.just(image)
                .map { it.loadBitMapFromUri(itemView.context) }
                .map { it.scaleToRatio(0.35) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    val w: Int = it.width
                    val h: Int = it.height

                    if(arrayCount == 1)
                    {
                        itemView.mcvImage.layoutParams.width =  ViewGroup.LayoutParams.MATCH_PARENT

                    }else{
                        if(w > h)
                        {
                            itemView.mcvImage.layoutParams.width =  ViewGroup.LayoutParams.MATCH_PARENT
                            //   itemView.mcvImage.layoutParams.height = 300
                        }else{
                            itemView.mcvImage.layoutParams.width = 350
                            // itemView.mcvImage.layoutParams.height = 500
                        }
                    }



                    itemView.ivMomentPhoto.setImageBitmap(it)
                }




        }

    }
}