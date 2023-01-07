package com.padcmyanmar.ttm.wechatapp.network

import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.padcmyanmar.ttm.wechatapp.data.vos.*

import com.padcmyanmar.ttm.wechatapp.utils.mUserVO
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


object RealtimeDatabaseFirebaseApiImpl:RealTimeFirebaseApi {

    private val database: DatabaseReference = FirebaseDatabase.getInstance("https://wechatapp-e0922-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
    private var db = Firebase.firestore

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

           /* database.child("contactsAndMessages")
                .child("234567")
                .setValue("bbb")
                .addOnSuccessListener {
                    Log.d("realtime","success case")
                }
                .addOnFailureListener {
                    Log.d("realtime","fail case ${it.message.toString()}")
                }*/



        database.child("contacts&Messages")
            .child(senderId)
            .child(receiverId)
            .child(currentTimestamp.toString())
            .setValue(chatMessageVO)
            .addOnSuccessListener {

                database.child("contacts&Messages")
                    .child(receiverId)
                    .child(senderId)
                    .child(currentTimestamp.toString())
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
            }


    }

    override fun getChatMessageList(
        receiverId:String,
        checkPrivateOrGroup:String,
        onSuccess: (chatMsgList: List<ChatMessageVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        if(checkPrivateOrGroup == "GroupChat")

        {
            database.child("groups")
                .child(receiverId)
                .child("messages")
                .addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        onFailure(error.message)
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val mChatMsgList = arrayListOf<ChatMessageVO>()

                        snapshot.children.forEach { dataSnapshot ->
                            dataSnapshot.getValue(ChatMessageVO::class.java)?.let {
                                mChatMsgList.add(it)
                            }
                        }
                        Log.d("Realtime","check chat msg list = ${mChatMsgList.size}")
                        onSuccess(mChatMsgList)
                    }
                })
        } else{
            database.child("contacts&Messages")
                .child(mUserVO.id.toString())
                .child(receiverId)
                .addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        onFailure(error.message)
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val mChatMsgList = arrayListOf<ChatMessageVO>()

                        snapshot.children.forEach { dataSnapshot ->
                            dataSnapshot.getValue(ChatMessageVO::class.java)?.let {
                                mChatMsgList.add(it)
                            }
                        }
                        Log.d("Realtime","check chat msg list = ${mChatMsgList.size}")
                        onSuccess(mChatMsgList)
                    }
                })
        }
    }

    override fun getChatHistoryList(
        senderId: String,
        onSuccess: (chatHistoryListVO: List<ChatHistoryVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {

        database.child("contacts&Messages")
            .child(mUserVO.id.toString())
            .addValueEventListener(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("Realtime","onDataChange 1")
                    val mChatHistoryListVO = arrayListOf<ChatHistoryVO>()

                    for((index,dataSnapshot) in snapshot.children.withIndex())
                    {
                       // val mChatMsgList = arrayListOf<ChatMessageVO>()
                        getChatUser(dataSnapshot?.key ?: "", onSuccess = {userVO->
                            Log.d("Realtime","onDataChange 6 ${userVO.name} ")
                            // chatHistoryVO.chatUserName = userVO.name
                            dataSnapshot.children.lastOrNull()?.getValue(ChatMessageVO::class.java)?.let {chatMsg->
                                //  mChatMsgList.add(chatMsg)
                                //  chatHistoryVO.chatMsgListVO = chatMsg
                                //  mChatHistoryVO.add(chatHistoryVO)



                                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                                val  dateString:String = dateFormat.format( chatMsg.timestamp?.let { Date(it) })



                                mChatHistoryListVO.add(ChatHistoryVO(
                                    chatUserId = dataSnapshot?.key,
                                    chatUserName = userVO.name,
                                    chatMsg = chatMsg.message,
                                    chatTime = dateString))
                                Log.d("Realtime","onDataChange 7 = ${chatMsg.message}")

                                Log.d("Realtime","onDataChange 8 $index ---- ${(snapshot.children.count())-1}")
                                if(index == (snapshot.children.count())-1)
                                {
                                    /*getChatGroupsList(onSuccess = {
                                        it.forEach {chatGroupVO->

                                            chatGroupVO.messages?.sortedByDescending { chatMessageVO -> chatMessageVO.timestamp }
                                            Log.d("Realtime","onDataChange 10 ${chatGroupVO.messages?.firstOrNull()?.timestamp}")
                                            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                                            val  dateString:String = dateFormat.format(
                                                chatGroupVO.messages?.firstOrNull()?.timestamp?.let { Date(it) })



                                            mChatHistoryListVO.add(ChatHistoryVO(
                                                chatUserId = chatGroupVO.id,
                                                chatUserName = chatGroupVO.name,
                                                chatMsg = chatGroupVO.messages?.firstOrNull()?.message,
                                                chatTime = dateString))
                                        }
                                        onSuccess(mChatHistoryListVO)
                                    },
                                    onFailure = {
                                        onSuccess(mChatHistoryListVO)
                                    })*/

                                    onSuccess(mChatHistoryListVO)
                                }

                            }
                        }, onFailure = {errorMsg->
                            onFailure(errorMsg)
                        })

                    }

                  //  snapshot.children.forEach { dataSnapshot ->
                 //       Log.d("Realtime","onDataChange 2 ${dataSnapshot.key}")
                       // var chatHistoryVO = ChatHistoryVO()






//                        dataSnapshot.children.forEach{ chatMsgList ->
//                            Log.d("Realtime","onDataChange 7")
//
//                            dataSnapshot?.key?.let {
//                                Log.d("Realtime","onDataChange 3 $it")
//                                chatHistoryVO.chatUserName = "aaaa"
//                                getChatUser(it, onSuccess = {userVO->
//                                    Log.d("Realtime","onDataChange 6 ${userVO.name} ")
//                                    chatHistoryVO.chatUserName = userVO.name
//
//
//                                }, onFailure = {errorMsg->
//                                    onFailure(errorMsg)
//                                })
//                            }
//
//
//                            chatMsgList.getValue(ChatMessageVO::class.java)?.let {chatMsg->
//                                mChatMsgList.add(chatMsg)
//                                chatHistoryVO.chatMsgListVO = mChatMsgList
//                                //  mChatHistoryVO.add(chatHistoryVO)
//
//                            }
//
//                        }

                      //  mChatHistoryVO.add(chatHistoryVO)
                      //  Log.d("Realtime","onDataChange 8 ${mChatHistoryVO.size}")

                  //  }

                      //  Log.d("Realtime","check chat msg list = ${mChatHistoryVO.size}")
                     //   onSuccess(mChatHistoryVO)


                }
            })
    }

    override fun createChatGroup(
        groupName: String,
        membersList: ArrayList<String>,
        groupPhoto: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        val currentTimestamp = System.currentTimeMillis()
        Log.d("Realtime","createChatGroup $groupName --- ${membersList.size}")

        database.child("groups")
            .child(currentTimestamp.toString())
            .setValue(ChatGroupVO(
                id = currentTimestamp.toString(),
                name = groupName,
            membersList = membersList,
                photo = groupPhoto,
                message = arrayListOf()
            ))
            .addOnSuccessListener {
                onSuccess("Successfully created")
            }
            .addOnFailureListener {
                onFailure(it.message.toString())
            }
    }

    override fun getChatGroupsList(
        onSuccess: (chatGroupList: ArrayList<ChatGroupVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        Log.d("realtime","chat group data")
        database.child("groups")
            .addValueEventListener(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("realtime","chat group data 1")
                    val mChatGroupVOList = arrayListOf<ChatGroupVO>()

                    for((index,dataSnapshot) in snapshot.children.withIndex())
                    {
                        dataSnapshot.getValue(ChatGroupVO::class.java)?.let {
                            Log.d("realtime","chat group data 2")
                            if(it.membersList?.contains(mUserVO.id.toString()) == true)
                            {
                                mChatGroupVOList.add(it)

                            }
                        }

                        if(index == (snapshot.children.count())-1)
                        {
                            Log.d("realtime","chat group data 3")
                            onSuccess(mChatGroupVOList)
                        }
                    }


                }
            })
    }

    private fun getChatUser(receiverId: String,onSuccess: (mChatUserData:UserVO) -> Unit,
    onFailure: (message: String) -> Unit){

        Log.d("Realtime","onDataChange 4 $receiverId")
        db.collection("users")
            .document(receiverId)
            .get()
            .addOnSuccessListener{result->
//                val usersList: MutableList<UserVO> = arrayListOf()
//
//                for (document in result)
//                {

                val data = result.data
                Log.d("Realtime","onDataChange 5 ${data?.get("name")}")
                var chatUserData = UserVO()
                chatUserData.name = data?.get("name") as String
                chatUserData.dateOfBirth = data["dateOfBirth"] as String
                chatUserData.genderType = data["genderType"] as String
                chatUserData.password = data["password"] as String
                chatUserData.phoneNumber = data["phoneNumber"] as String
                //     usersList.add(userData)
                //  }

                onSuccess(chatUserData)
            }
            .addOnFailureListener {
                onFailure(it.message ?: " Please check connection ")
            }
    }

    override fun sendGroupMessage(
        senderId: String,
        receiverId: String,
        msg: String,
        senderName: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {

      //  Log.d("realtime","before save to realtime db")

        var chatMessageVO = ChatMessageVO()
        val currentTimestamp = System.currentTimeMillis()
        chatMessageVO.message = msg
        chatMessageVO.user_id = senderId
        chatMessageVO.name = senderName
        chatMessageVO.timestamp = currentTimestamp
      //  Log.d("realtime","before save to realtime db 2 ${chatMessageVO.name}")

        database.child("groups")
            .child(receiverId)
            .child("messages")
            .child(currentTimestamp.toString())
            .setValue(chatMessageVO)
            .addOnSuccessListener {
                onSuccess("Successfully send")

            }
            .addOnFailureListener {
                onFailure(it.message.toString())
            }


    }

}