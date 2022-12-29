package com.padcmyanmar.ttm.wechatapp.mvp.presenters

import android.graphics.Bitmap
import android.net.Uri
import com.padcmyanmar.ttm.wechatapp.delegates.MediaTypeDataDelegate
import com.padcmyanmar.ttm.wechatapp.mvp.views.CreateNewMomentView

interface CreateNewMomentPresenter : BasePresenter<CreateNewMomentView>, MediaTypeDataDelegate {

    fun onTapToChoosePhotoAndVideo()

    fun onTapClose()

   // fun onTapCreate(image: Bitmap, onSuccess: (returnUrlString: String?) -> Unit)


    fun uploadFileCreate(fileUri: Uri, onSuccess: (returnUrlString: String?) -> Unit)


    fun onTapCreate(
        imagesList: ArrayList<String>,
        likedIdList: ArrayList<String>,
        description: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    )
}