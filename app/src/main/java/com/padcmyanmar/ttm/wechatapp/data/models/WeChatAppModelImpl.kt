package com.padcmyanmar.ttm.wechatapp.data.models

import android.graphics.Bitmap
import android.net.Uri
import com.padcmyanmar.ttm.wechatapp.data.vos.MomentVO
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.network.CloudFirestoreFirebaseApiImpl
import com.padcmyanmar.ttm.wechatapp.network.FirebaseApi

object WeChatAppModelImpl: WeChatAppModel  {
    override var mFirebaseApi: FirebaseApi = CloudFirestoreFirebaseApiImpl


    override fun addUser(
        name: String,
        dateOfBirth: String,
        gender: String,
        password: String,
        phoneNo: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseApi.addUser(name = name,
            dateOfBirth =  dateOfBirth,
            gender =  gender,
            password = password,
            phoneNum = phoneNo,
            onSuccess,
            onFailure)
    }

    override fun getUser(
        phoneNo:String,
        password: String,
        onSuccess: (userVO: UserVO) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseApi.getUser(
            phoneNum = phoneNo,
            password = password,
            onSuccess = {
                        onSuccess(it)
            },
            onFailure = {
                onFailure(it)
            })
    }


    override fun uploadPhoto(image: Bitmap, onSuccess: (returnUrlString: String?) -> Unit){
        mFirebaseApi.uploadImageUserVO(
            image = image,
            onSuccess = onSuccess
        )
    }
    override fun uploadImageAndVideoFile(fileUri: Uri, onSuccess: (returnUrlString: String?) -> Unit){
        mFirebaseApi.uploadImageAndVideoFile(
            fileUri = fileUri,
            onSuccess = {
                onSuccess(it)
            }
        )
    }

    override fun addMoment(
        imgList: ArrayList<String>,
        likeIdList:ArrayList<String>,
        description: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        mFirebaseApi.addMoment(
            imgList = imgList,
            likedId = likeIdList,
            description = description,
            onSuccess = {
                        onSuccess(it)
            },
            onFailure= {
                onFailure(it)
            })
    }


    override fun getMomentData  (
    onSuccess: (momentsList: ArrayList<MomentVO>) -> Unit,
    onFailure: (message: String) -> Unit
    ) {
        mFirebaseApi.getMomentData(
            onSuccess = {
                onSuccess(it)
            },
            onFailure = {
                onFailure(it)
            })
    }

}