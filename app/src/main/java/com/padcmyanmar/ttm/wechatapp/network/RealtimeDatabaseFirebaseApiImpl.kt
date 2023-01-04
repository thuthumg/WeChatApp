package com.padcmyanmar.ttm.wechatapp.network

import android.net.Uri
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

import com.padcmyanmar.ttm.wechatapp.data.vos.ChatMessageVO
import com.padcmyanmar.ttm.wechatapp.data.vos.MediaDataVO
import com.padcmyanmar.ttm.wechatapp.data.vos.MomentVO
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO


object RealtimeDatabaseFirebaseApiImpl:FirebaseApi {

    private val database: DatabaseReference = Firebase.database.reference
   // private val storage: FirebaseStorage = FirebaseStorage.getInstance()
   // private val storageReference: StorageReference = storage.reference

    override fun addUser(
        name: String,
        dateOfBirth: String,
        gender: String,
        password: String,
        phoneNum: String,
        userId: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
    }

    override fun getUser(
        phoneNum: String,
        password: String,
        onSuccess: (usersVO: UserVO) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
    }

    override fun uploadImageAndVideoFile(
        fileUri: Uri,
        onSuccess: (returnUrlString: String?) -> Unit
    ) {

    }

    override fun getMomentData(
        onSuccess: (momentsList: ArrayList<MomentVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {

    }

    override fun addMoment(
        imgList: ArrayList<MediaDataVO>,
        likedId: ArrayList<String>,
        description: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {

    }

    override fun editMoment(
        momentVO: MomentVO,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {

    }

    override fun addContacts(
        userId: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {

    }

    override fun getContacts(
        onSuccess: (contactsList: ArrayList<UserVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {

    }

    override fun editUser(
        userName: String,
        dateOfBirth: String,
        genderType: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {

    }

    override fun sendMessage(
        senderId: String,
        receiverId: String,
        msg: String,
        senderName: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {

        Log.d("realtime","before save to realtime db")

        var chatMessageVO = ChatMessageVO()
        val currentTimestamp = System.currentTimeMillis()
        chatMessageVO.message = msg
        chatMessageVO.user_id = senderId
        chatMessageVO.name = senderName
        chatMessageVO.timestamp = currentTimestamp
        Log.d("realtime","before save to realtime db 2 ${chatMessageVO.name}")
        try {
            database.child("contactsAndMessages")
                .child(senderId)
                .setValue(chatMessageVO)
                .addOnSuccessListener {
                    Log.d("realtime","success case")
                }
                .addOnFailureListener {
                    Log.d("realtime","fail case ${it.message.toString()}")
                }
        }catch (e:Exception){
            Log.d("realtime","fail case ${e.message.toString()}")
        }


       /* database.child("contacts&Messages")
            .child(senderId)
            .child(receiverId)
            .setValue(chatMessageVO)
            .addOnSuccessListener {

                database.child("contacts&Messages")
                    .child(receiverId)
                    .child(senderId)
                    .setValue(chatMessageVO)
                    .addOnSuccessListener {
                        onSuccess("Successfully send")
                    }
                    .addOnFailureListener {
                        onFailure(it.message.toString())
                    }
            }
            .addOnFailureListener {
                onFailure(it.message.toString())
            }*/


    }
}