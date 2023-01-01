package com.padcmyanmar.ttm.wechatapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.delegates.ChatsListDelegate
import com.padcmyanmar.ttm.wechatapp.viewholders.ChatsListViewHolder

class ChatsListAdapter(var mDelegate:ChatsListDelegate) : RecyclerView.Adapter<ChatsListViewHolder>() {

  //  private var mData: List<> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_chat_list,parent,false)

        return ChatsListViewHolder(view,mDelegate)
    }

    override fun onBindViewHolder(holder: ChatsListViewHolder, position: Int) {
//       if(mData.isNotEmpty()){
//           holder.bindData(mData[position])
//       }
    }

    override fun getItemCount(): Int {
        //return mData.count()
        return 20
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun setNewData(data: List<>){
//        mData = data
//        notifyDataSetChanged()
//    }
}