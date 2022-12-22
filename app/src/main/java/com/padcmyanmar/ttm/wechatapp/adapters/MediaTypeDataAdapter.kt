package com.padcmyanmar.ttm.wechatapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.delegates.MediaTypeDataDelegate
import com.padcmyanmar.ttm.wechatapp.viewholders.AddMediaTypeDataViewHolder
import com.padcmyanmar.ttm.wechatapp.viewholders.BaseViewHolder
import com.padcmyanmar.ttm.wechatapp.viewholders.MediaTypeDataViewHolder

class MediaTypeDataAdapter(private val mDelegate:MediaTypeDataDelegate) : RecyclerView.Adapter<BaseViewHolder>() {
    companion object{
        const val VIEW_TYPE_ADD_MEDIA = 1
        const val VIEW_TYPE_IMAGE = 2
    }
   // private var mData: List<MediaTypeDataAdapter> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        when(viewType)
        {
            VIEW_TYPE_ADD_MEDIA ->{
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.view_holder_media_choose_layout, parent, false)
                return AddMediaTypeDataViewHolder(view,mDelegate)
            }
            VIEW_TYPE_IMAGE->{
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.view_holder_media_item_layout, parent, false)
                return MediaTypeDataViewHolder(view,mDelegate)
            }
            else->{
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.view_holder_media_item_layout, parent, false)
                return MediaTypeDataViewHolder(view,mDelegate)
            }
        }

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
//       if(mData.isNotEmpty()){
//           holder.bindData(mData[position])
//       }
    }

    override fun getItemCount(): Int {
       // return mData.count()
        return 5

    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun setNewData(data: List<MediaTypeDataAdapter>){
//        mData = data
//        notifyDataSetChanged()
//    }

    override fun getItemViewType(position: Int): Int {


        return when (position) {
            itemCount - 1 -> {
                VIEW_TYPE_ADD_MEDIA
            }
            else -> {
                VIEW_TYPE_IMAGE
            }

        }
    }
}