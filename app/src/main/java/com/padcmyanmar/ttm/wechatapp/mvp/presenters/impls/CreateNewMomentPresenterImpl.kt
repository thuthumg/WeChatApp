package com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.data.models.WeChatAppModelImpl
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.CreateNewMomentPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.views.CreateNewMomentView

class CreateNewMomentPresenterImpl:CreateNewMomentPresenter,
    AbstractBasePresenter<CreateNewMomentView>() {

    private val mWeChatAppModel = WeChatAppModelImpl


    override fun onTapToChoosePhotoAndVideo() {
        mView.navigateToChoosePhotoAndVideo()
    }

    override fun onTapCreate (imagesList: ArrayList<String>,description:String,
                              onSuccess: (message: String) -> Unit,
                              onFailure:(message:String)-> Unit) {
        // mView.createFunction()
        mWeChatAppModel.addMoment(imagesList,description,
            onSuccess = {
            onSuccess(it)
        },
        onFailure = {
            onFailure(it)
        })


    }



    override fun uploadFileCreate(fileUri: Uri, onSuccess: (returnUrlString: String?) -> Unit) {
        mWeChatAppModel.uploadImageAndVideoFile(fileUri,  onSuccess = {
            onSuccess(it)
        })
    }

    override fun onTapClose() {
        mView.closeFunction()
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }

    override fun goToChooseMediaType() {
        onTapToChoosePhotoAndVideo()
    }
}