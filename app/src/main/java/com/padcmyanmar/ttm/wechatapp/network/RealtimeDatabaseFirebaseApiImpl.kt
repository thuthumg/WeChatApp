package com.padcmyanmar.ttm.wechatapp.network

import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.padcmyanmar.ttm.wechatapp.data.vos.*
import com.padcmyanmar.ttm.wechatapp.utils.*
import java.text.SimpleDateFormat
import java.util.*


object RealtimeDatabaseFirebaseApiImpl : RealTimeFirebaseApi {

    private val database: DatabaseReference =
        FirebaseDatabase.getInstance(REALTIME_DB_INSTANCE_PATH).reference
    private var db = Firebase.firestore

    override fun sendMessage(
        senderId: String,
        receiverId: String,
        msg: String,
        senderName: String,
        fileUrl: String,
        profileUrl: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {

        var chatMessageVO = ChatMessageVO()
        var currentTimestamp = System.currentTimeMillis()
        chatMessageVO.file = fileUrl
        chatMessageVO.message = msg
        chatMessageVO.name = senderName
        chatMessageVO.profileUrl = profileUrl
        chatMessageVO.timestamp = currentTimestamp
        chatMessageVO.userId = senderId



        database.child(CONTACTS_AND_MESSAGES_COLLECTION)
            .child(senderId)
            .child(receiverId)
            .child(currentTimestamp.toString())
            .setValue(chatMessageVO)
            .addOnSuccessListener {

                database.child(CONTACTS_AND_MESSAGES_COLLECTION)
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
        receiverId: String,
        checkPrivateOrGroup: String,
        onSuccess: (chatMsgList: List<ChatMessageVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        if (checkPrivateOrGroup == CHAT_TYPE_GROUP) {
            database.child(GROUPS_COLLECTION)
                .child(receiverId)
                .child(GROUP_DOCUMENT_FIELD_GROUP_MESSAGE)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        onFailure(error.message)
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val mChatMsgList = arrayListOf<ChatMessageVO>()
                      var sortedListData = snapshot.children.sortedByDescending { dataSnapshot -> dataSnapshot.key }
                        sortedListData.forEach { dataSnapshot ->
                            dataSnapshot.getValue(ChatMessageVO::class.java)?.let {
                                mChatMsgList.add(it)
                            }
                        }
                        onSuccess(mChatMsgList)
                    }
                })
        } else {
            database.child(CONTACTS_AND_MESSAGES_COLLECTION)
                .child(mUserVO.id.toString())
                .child(receiverId)
                .addValueEventListener(object : ValueEventListener {
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

        database.child(CONTACTS_AND_MESSAGES_COLLECTION)
            .child(mUserVO.id.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {

                    val mChatHistoryListVO = arrayListOf<ChatHistoryVO>()

                    for ((index, dataSnapshot) in snapshot.children.withIndex()) {
                        //user of receiver
                        getChatUser(dataSnapshot?.key ?: "", onSuccess = { receiverUserVO ->

                         var sortedListData =  dataSnapshot.children.sortedByDescending { dataSnapshot -> dataSnapshot.key }
                            sortedListData.firstOrNull()?.getValue(ChatMessageVO::class.java)
                                ?.let { chatMsg ->

                                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                                    val dateString: String =
                                        dateFormat.format(chatMsg.timestamp?.let { Date(it) })


                                    Log.d("realtimedb","check msg ${chatMsg.message}")

                                    mChatHistoryListVO.add(
                                        ChatHistoryVO(
                                            chatUserId = dataSnapshot?.key,
                                            chatUserName = receiverUserVO.name,
                                            chatMsg = chatMsg.message,
                                            chatTime = dateString,
                                            chatUserProfileUrl = receiverUserVO.profileUrl
                                        )
                                    )

                                    if (index == (snapshot.children.count()) - 1) {
                                        onSuccess(mChatHistoryListVO)
                                    }

                                }
                        }, onFailure = { errorMsg ->
                            onFailure(errorMsg)
                        })

                    }


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
        database.child(GROUPS_COLLECTION)
            .child(currentTimestamp.toString())
            .setValue(
                ChatGroupVO(
                    id = currentTimestamp.toString(),
                    name = groupName,
                    message = hashMapOf(),
                    membersList = membersList,
                    profileUrl = groupPhoto

                )
            )
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
    ) {  Log.d("realtime","check user vo and memberslist")
        database.child(GROUPS_COLLECTION)
            .addValueEventListener(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    Log.d("realtime","check user vo and memberslist error")
                    onFailure(error.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val mChatGroupVOList = arrayListOf<ChatGroupVO>()
                    Log.d("realtime","check user vo and memberslist 1")

                    for((index,dataSnapshot) in snapshot.children.withIndex())
                    {
                        dataSnapshot.getValue(ChatGroupVO::class.java)?.let {

                            Log.d("realtime","check user vo and memberslist 2 ${it.membersList} ---- ${mUserVO.id.toString()}")

                            if(it.membersList?.contains(mUserVO.id.toString()) == true)
                            {
                                mChatGroupVOList.add(it)
                            }
                        }

                        if(index == (snapshot.children.count())-1)
                        {
                            onSuccess(mChatGroupVOList)
                        }
                    }


                }
            })
    }
    /*override fun getChatGroupsList(
        onSuccess: (chatGroupList: ArrayList<ChatGroupVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
       /* val rootRef = FirebaseDatabase.getInstance().reference

        val valueEventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                val mChatGroupListVO: MutableList<ChatGroupVO?> = ArrayList()
                for (valueRes in dataSnapshot.children) {
                    val chatGroupVO: ChatGroupVO? =
                        valueRes.getValue(ChatGroupVO::class.java)
                    Log.d("Test", "${chatGroupVO?.message?.firstOrNull()?.message}")
                    mChatGroupListVO.add(chatGroupVO)
                }
                onSuccess(mChatGroupListVO)
                //Do what you need to do with listRes
            }

            override fun onCancelled(@NonNull databaseError: DatabaseError) {
                throw databaseError.toException()
            }
        }
        database.child(GROUPS_COLLECTION).addListenerForSingleValueEvent(valueEventListener)
        */

        database.child(GROUPS_COLLECTION)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {



                    /*val mChatGroupListVO: ArrayList<ChatGroupVO> = arrayListOf()
                    for (valueRes in snapshot.children) {
                        val chatGroupVO = ChatGroupVO()
                        chatGroupVO.id = valueRes.getValue<ChatGroupVO>()?.id
                        chatGroupVO.message = valueRes.getValue<ChatGroupVO>()?.message
                        chatGroupVO.membersList = valueRes.getValue<ChatGroupVO>()?.membersList
                        chatGroupVO.name = valueRes.getValue<ChatGroupVO>()?.name
                        chatGroupVO.profileUrl =valueRes.getValue<ChatGroupVO>()?.profileUrl
                     //   mChatGroupListVO.add(chatGroupVO)
//                        val chatGroupVO: ChatGroupVO? =
//                            valueRes.getValue(ChatGroupVO::class.java)
//                        Log.d("Test", "${chatGroupVO?.message?.firstOrNull()?.message}")
//                        chatGroupVO?.let { mChatGroupListVO.add(it) }
                    }

                    onSuccess(mChatGroupListVO)*/

                    val mChatGroupVOList = arrayListOf<ChatGroupVO>()

                    for ((index, dataSnapshot) in snapshot.children.withIndex()) {


                        dataSnapshot.getValue(ChatGroupVO::class.java)?.let {

                            if (it.membersList?.contains(mUserVO.id.toString()) == true) {
                                mChatGroupVOList.add(it)
                            }
                        }

                        if (index == (snapshot.children.count()) - 1) {
                            onSuccess(mChatGroupVOList)
                        }
                    }


                }
            })
    }*/

    private fun getChatUser(
        receiverId: String, onSuccess: (mChatUserData: UserVO) -> Unit,
        onFailure: (message: String) -> Unit
    ) {

        db.collection(USER_COLLECTION)
            .document(receiverId)
            .get()
            .addOnSuccessListener { result ->

                val data = result.data
                var chatUserData = UserVO()
                data?.let {userVO->

                    chatUserData.id = userVO[USER_DOCUMENT_FIELD_UID] as String
                    chatUserData.name = userVO[USER_DOCUMENT_FIELD_NAME] as String
                    chatUserData.dateOfBirth = userVO[USER_DOCUMENT_FIELD_DATE_OF_BIRTH] as String
                    chatUserData.genderType = userVO[USER_DOCUMENT_FIELD_GENDER_TYPE] as String
                    chatUserData.password = userVO[USER_DOCUMENT_FIELD_PASSWORD] as String
                    chatUserData.phoneNumber = userVO[USER_DOCUMENT_FIELD_PHONE_NUM] as String
                    chatUserData.profileUrl = userVO[USER_DOCUMENT_FIELD_PROFILE_URL] as String

                }

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
        fileUrl: String,
        profileUrl: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {

        val chatMessageVO = ChatMessageVO()
        var currentTimestamp = System.currentTimeMillis()
        chatMessageVO.file = fileUrl
        chatMessageVO.message = msg
        chatMessageVO.name = senderName
        chatMessageVO.profileUrl = profileUrl
        chatMessageVO.timestamp = currentTimestamp
        chatMessageVO.userId = senderId

        database.child(GROUPS_COLLECTION)
            .child(receiverId)
            .child(GROUP_DOCUMENT_FIELD_GROUP_MESSAGE)
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