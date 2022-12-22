package com.padcmyanmar.ttm.wechatapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.adapters.MomentsListAdapter
import kotlinx.android.synthetic.main.fragment_moments.*

class MomentsFragment : Fragment() {
    lateinit var mMomentsListAdapter : MomentsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_moments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpMomentsList()
    }

    private fun setUpMomentsList() {
        mMomentsListAdapter = MomentsListAdapter()
        rvMomentsList.adapter = mMomentsListAdapter
        rvMomentsList.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
        rvMomentsList.isNestedScrollingEnabled = false
    }


}