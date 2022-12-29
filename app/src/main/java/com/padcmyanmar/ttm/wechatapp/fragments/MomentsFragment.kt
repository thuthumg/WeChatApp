package com.padcmyanmar.ttm.wechatapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.adapters.MomentsListAdapter
import com.padcmyanmar.ttm.wechatapp.data.vos.MomentVO
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.MomentFragmentPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls.MomentFragmentPresenterImpl
import com.padcmyanmar.ttm.wechatapp.mvp.views.MomentFragmentView
import kotlinx.android.synthetic.main.fragment_moments.*
import kotlin.collections.ArrayList


class MomentsFragment : Fragment(),MomentFragmentView {
    lateinit var mMomentsListAdapter : MomentsListAdapter

    private lateinit var mPresenter: MomentFragmentPresenter


    var mMomentsList: ArrayList<MomentVO> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_moments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()
        setUpMomentsList()
        context?.let { mPresenter.onUiReady(it,this) }
    }



    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MomentFragmentPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpMomentsList() {
        mMomentsListAdapter = MomentsListAdapter(mPresenter)
        rvMomentsList.adapter = mMomentsListAdapter
        rvMomentsList.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
        rvMomentsList.isNestedScrollingEnabled = false
    }

    override fun showMomentData(momentList: ArrayList<MomentVO>) {
        mMomentsList = momentList
        mMomentsListAdapter.setNewData(momentList.reversed())
    }

    override fun favouriteFunction(id: String?,likeUserPhoneNumber:String?) {
        for (momentData in mMomentsList.reversed())
        {
           if(momentData.id == id)
           {

              var arr =  momentData.likedId
              val key = likeUserPhoneNumber

              var checkDataExistOrNot: Boolean? =  arr?.let { key?.let { it1 -> checkItem(it, it1) } }

               if(checkDataExistOrNot == true)
               {
                   momentData.likedId?.remove(likeUserPhoneNumber)
               }else{
                   momentData.likedId?.add(likeUserPhoneNumber!!)
               }
                break
           }
        }

        mMomentsListAdapter.setNewData(mMomentsList.reversed())

            }

    override fun showError(error: String) {

    }

//    private fun <T> isPresent(arr: Array<String>, target: T): Boolean {
//        return target in arr
//    }

    fun checkItem(arr: ArrayList<String>, item: String): Boolean {
        return arr.indexOf(item) != -1
    }

}