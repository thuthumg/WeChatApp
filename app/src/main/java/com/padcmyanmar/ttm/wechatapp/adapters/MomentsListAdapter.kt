package com.padcmyanmar.ttm.wechatapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.data.vos.MomentVO
import com.padcmyanmar.ttm.wechatapp.delegates.MomentItemDelegate
import com.padcmyanmar.ttm.wechatapp.viewholders.MomentsListViewHolder
import kotlinx.android.synthetic.main.view_holder_moment_item.view.*

class MomentsListAdapter(private val mDelegate:MomentItemDelegate) : RecyclerView.Adapter<MomentsListViewHolder>() {

    private var mData: List<MomentVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentsListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_moment_item,parent,false)

        return MomentsListViewHolder(view,mDelegate)
    }

    override fun onBindViewHolder(holder: MomentsListViewHolder, position: Int) {

      //  holder.bindData(position)

       if(mData.isNotEmpty()){
           holder.bindData(position,mData[position])
       }
    }

    override fun getItemCount(): Int {
        return mData.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data: List<MomentVO>){
        mData = data
        notifyDataSetChanged()
    }
}
