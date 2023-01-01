package com.padcmyanmar.ttm.wechatapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.activities.MainActivity.Companion.BUNDLE_PHONE_NUMBER
import com.padcmyanmar.ttm.wechatapp.adapters.MomentsListAdapter
import com.padcmyanmar.ttm.wechatapp.data.vos.MomentVO
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.MomentFragmentPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls.MomentFragmentPresenterImpl
import com.padcmyanmar.ttm.wechatapp.mvp.views.MomentFragmentView
import com.padcmyanmar.ttm.wechatapp.utils.checkItem
import kotlinx.android.synthetic.main.content_create_moment_layout.*
import kotlinx.android.synthetic.main.fragment_moments.*


class MomentsFragment : Fragment(), MomentFragmentView {
    lateinit var mMomentsListAdapter: MomentsListAdapter

    private lateinit var mPresenter: MomentFragmentPresenter
    var loginUserPhoneNumber: String? = ""

    var mMomentsList: ArrayList<MomentVO> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        loginUserPhoneNumber = arguments?.getString(BUNDLE_PHONE_NUMBER)

        return inflater.inflate(R.layout.fragment_moments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()
        setUpMomentsList()
        context?.let { mPresenter.onUiReady(it, this) }
    }


    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MomentFragmentPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpMomentsList() {
        mMomentsListAdapter =
            MomentsListAdapter(mPresenter, loginUserPhoneNo = loginUserPhoneNumber)
        rvMomentsList.adapter = mMomentsListAdapter
        rvMomentsList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        rvMomentsList.isNestedScrollingEnabled = false
    }

    override fun showMomentData(momentList: ArrayList<MomentVO>) {
        mMomentsList = momentList
        mMomentsListAdapter.setNewData(momentList.reversed())
    }

    override fun favouriteFunction(momentVOParam: MomentVO) {

        //  outerLoop@for (momentItem in mMomentsList.reversed())
        // {
        // if(momentItem.id == momentVOParam.id)
        //  {
        var arr = momentVOParam.likedId
        val key = loginUserPhoneNumber

        var checkDataExistOrNot: Boolean? = arr?.let { key?.let { it1 -> checkItem(it, it1) } }

        if (checkDataExistOrNot == true) {

            momentVOParam.likedId?.remove(loginUserPhoneNumber)
            mPresenter.onTapEditMoment(
                momentVOParam,
                onSuccess = {
                    mMomentsListAdapter.setNewData(mMomentsList.reversed())
                },
                onFailure = { e ->
                    pbLoading.visibility = View.GONE
                    showError(e)
                }
            )

            /*if(loginUserPhoneNumber == momentData.phoneNumber)
            {
                momentData.likedId?.remove(momentVOParam.phoneNumber)
                momentData.id?.let {
                    mPresenter.onTapEditMoment(
                        momentData,
                        onSuccess = {

                        }
                    ) { e ->
                        pbLoading.visibility = View.GONE
                        showError(e)
                    }
                }
            }*/

        } else {

            loginUserPhoneNumber?.let {

                momentVOParam.likedId?.add(it)
            }
            mPresenter.onTapEditMoment(
                momentVOParam,
                onSuccess = {

                    mMomentsListAdapter.setNewData(mMomentsList.reversed())
                },
                onFailure = { e ->
                    pbLoading.visibility = View.GONE
                    showError(e)
                }
            )

            /* loginUserPhoneNumber?.let { momentData.likedId?.add(it) }
            /* momentData.id?.let {
                 mPresenter.onTapEditMoment(
                     it,
                     momentData.photoOrVideoUrlLink ?: arrayListOf(),
                     momentData.likedId ?: arrayListOf(),
                     momentData.description.toString(),

                     onSuccess = {

                     },
                     onFailure = { e ->
                         pbLoading.visibility = View.GONE
                         showError(e)
                     }
                 )
             }*/
             momentData.id?.let {
                 mPresenter.onTapEditMoment(
                     momentData,
                     onSuccess = {

                     }
                 ) { e ->
                     pbLoading.visibility = View.GONE
                     showError(e)
                 }
             }*/
        }

        //      break@outerLoop
        //  }
        // }


    }

    override fun showError(error: String) {

    }

}