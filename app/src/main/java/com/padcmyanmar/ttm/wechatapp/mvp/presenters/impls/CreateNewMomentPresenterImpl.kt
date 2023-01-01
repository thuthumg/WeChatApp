package com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.data.models.WeChatAppModelImpl
import com.padcmyanmar.ttm.wechatapp.data.vos.MediaDataVO
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.CreateNewMomentPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.views.CreateNewMomentView

class CreateNewMomentPresenterImpl:CreateNewMomentPresenter,
    AbstractBasePresenter<CreateNewMomentView>() {

    private val mWeChatAppModel = WeChatAppModelImpl


    override fun onTapToChoosePhotoAndVideo() {
        mView.navigateToChoosePhotoAndVideo()
    }

    override fun onTapCreate (
        imagesList: ArrayList<MediaDataVO>,
        likedIdList:ArrayList<String>,
        description:String,
        onSuccess: (message: String) -> Unit,
        onFailure:(message:String)-> Unit) {
        // mView.createFunction()
        mWeChatAppModel.addMoment(imgList = imagesList,
            likeIdList = likedIdList,
            description = description,
            onSuccess = {
            onSuccess(it)
        }
        ) {
            onFailure(it)
        }


    }



    override fun uploadFileCreate(fileUri: Uri, onSuccess: (returnUrlString: String?) -> Unit) {
        mWeChatAppModel.uploadImageAndVideoFile(fileUri,  onSuccess = {
            Log.d("CreateNewMoment","check image link at upload function 1= $it")
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