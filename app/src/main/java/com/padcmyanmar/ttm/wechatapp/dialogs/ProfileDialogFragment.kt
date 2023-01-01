package com.padcmyanmar.ttm.wechatapp.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.padcmyanmar.ttm.wechatapp.R

class ProfileDialogFragment : DialogFragment() {
    companion object {
        const val TAG_PROFILE_EDIT_DIALOG = "TAG_PROFILE_EDIT_DIALOG"
//        const val BUNDLE_NAME = "BUNDLE_NAME"
//        const val BUNDLE_DESCRIPTION = "BUNDLE_DESCRIPTION"
//        const val BUNDLE_AMOUNT = "BUNDLE_AMOUNT"
//        const val BUNDLE_IMAGE = "BUNDLE_IMAGE"

        fun newFragment(): ProfileDialogFragment {
            return ProfileDialogFragment()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_edit_profile, container, false)
        return view
    }




}