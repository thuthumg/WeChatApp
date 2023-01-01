package com.padcmyanmar.ttm.wechatapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcmyanmar.ttm.wechatapp.R
import com.padcmyanmar.ttm.wechatapp.adapters.MomentsListAdapter
import com.padcmyanmar.ttm.wechatapp.data.vos.MomentVO
import com.padcmyanmar.ttm.wechatapp.delegates.MomentItemDelegate
import com.padcmyanmar.ttm.wechatapp.dialogs.ProfileDialogFragment
import com.padcmyanmar.ttm.wechatapp.dialogs.ProfileDialogFragment.Companion.TAG_PROFILE_EDIT_DIALOG
import kotlinx.android.synthetic.main.fragment_moments.*
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment(), MomentItemDelegate {

    lateinit var mMomentsListAdapter: MomentsListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUpMomentsList()
        clickListenerFunc()
    }

    private fun clickListenerFunc() {
        mcvEditProfile.setOnClickListener {
            setUpEditProfile()
        }
    }

    private fun setUpMomentsList() {
        mMomentsListAdapter =
            MomentsListAdapter(this,"")
        rvBookMarkList.adapter = mMomentsListAdapter
        rvBookMarkList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        rvBookMarkList.isNestedScrollingEnabled = false
    }

    override fun onTapFavorite(momentVO: MomentVO) {

    }

    override fun onTapSavePost() {

    }

    private fun setUpEditProfile(){

        val profileEditDialog = ProfileDialogFragment.newFragment()
//        val bundle = Bundle()
//        bundle.putString(BUNDLE_NAME, name)
//        bundle.putString(BUNDLE_DESCRIPTION,description)
//        bundle.putString(BUNDLE_AMOUNT, amount)
//        bundle.putString(BUNDLE_IMAGE,image)
//        groceryDialog.arguments = bundle
        profileEditDialog.show(parentFragmentManager, TAG_PROFILE_EDIT_DIALOG)
    }

}