package com.padcmyanmar.ttm.wechatapp.mvp.presenters

import android.graphics.Bitmap
import com.padcmyanmar.ttm.wechatapp.data.vos.MomentVO
import com.padcmyanmar.ttm.wechatapp.delegates.MomentItemDelegate
import com.padcmyanmar.ttm.wechatapp.mvp.views.ProfileFragmentView

interface ProfileFragmentPresenter:BasePresenter<ProfileFragmentView>, MomentItemDelegate {

    fun onTapEditUser(userName:String,
    dateOfBirth:String,
    genderType:String,
    phoneNumber:String,
    onSuccess: (message:String)-> Unit,
    onFailure: (message:String)-> Unit)

    fun onPhotoTaken(bitmap : Bitmap, onSuccess: (returnUrlString: String)->Unit)


    fun onTapEditMoment(
        momentVO: MomentVO,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    )



}