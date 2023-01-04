package com.padcmyanmar.ttm.wechatapp.network

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.MetadataChanges

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.padcmyanmar.ttm.wechatapp.data.vos.MediaDataVO
import com.padcmyanmar.ttm.wechatapp.data.vos.MomentVO
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import com.padcmyanmar.ttm.wechatapp.utils.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


object CloudFirestoreFirebaseApiImpl : FirebaseApi{
    // [START get_firestore_instance]
    @SuppressLint("StaticFieldLeak")
    private var db = Firebase.firestore
    // [END get_firestore_instance]

    // [START set_firestore_settings]
    private val settings = firestoreSettings {
        isPersistenceEnabled = true
    }

    // [END set_firestore_settings]


   var mUserVO:UserVO = UserVO()


    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val storageReference: StorageReference = storage.reference
    var returnUrlString:String? = ""

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

        val userMap: HashMap<String,Any> = hashMapOf(
            DOCUMENT_FIELD_NAME to name,
            DOCUMENT_FIELD_DATE_OF_BIRTH to dateOfBirth,
            DOCUMENT_FIELD_GENDER_TYPE to gender,
            DOCUMENT_FIELD_PASSWORD to password,
            DOCUMENT_FIELD_PHONE_NUM to phoneNum,
            DOCUMENT_FIELD_UID to userId
        )
        db.collection(USER_COLLECTION)
            .document(userId)
            .set(userMap)
            .addOnSuccessListener {
                Log.d("Success","Successfully added user data")
                onSuccess("Saved Successfully")
            }
            .addOnFailureListener {
                Log.d("Failure","Failed to add user data")
                onFailure("Save Failed")
            }
    }

    override fun getUser(
        phoneNum: String,
        password: String,
        onSuccess: (userVO: UserVO) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        Log.d("cloudFirestore", "getUser function $phoneNum")
      //  val usersList: MutableList<UserVO> = arrayListOf()
        db.firestoreSettings = settings

        db.collection(USER_COLLECTION)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                }?: run {
                   var checkFlag = false
                    var userData = UserVO()
                    val result : List<DocumentSnapshot> = value?.documents ?: arrayListOf()
                    outerLoop@for(document : DocumentSnapshot in result)
                    {
                        val data = document.data
                        if(data?.get(DOCUMENT_FIELD_PHONE_NUM) == phoneNum && data?.get(
                                DOCUMENT_FIELD_PASSWORD) == password)
                        {

                            userData.name = data[DOCUMENT_FIELD_NAME] as String
                            userData.dateOfBirth = data[DOCUMENT_FIELD_DATE_OF_BIRTH] as String
                            userData.genderType = data[DOCUMENT_FIELD_GENDER_TYPE] as String
                            userData.password = data[DOCUMENT_FIELD_PASSWORD] as String
                            userData.phoneNumber = data[DOCUMENT_FIELD_PHONE_NUM] as String
                            userData.id = data[DOCUMENT_FIELD_UID] as String
                            mUserVO = userData
                            checkFlag = true

                            break@outerLoop
                        }else{
                            checkFlag = false
                        }

                    }

                    if(checkFlag)
                    {
                        onSuccess(userData)
                    }else{
                        onFailure("Login Failed.")
                    }

//                    val source = if (value?.metadata?.isFromCache == true)
//                    {
//                        "local cache"
//
//                    }
//                    else
//                    {
//                        "server"
//                    }


                }




            }


    }

    override fun addMoment(
        imgList: ArrayList<MediaDataVO>,
        likedId:ArrayList<String>,
        description: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    )
    {
        val currentTimestamp = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val postedTime: String = dateFormat.format(Date())

        val momentMap : HashMap<String,Any> = hashMapOf(
            "name" to mUserVO.name.toString(),
            "description" to description,
            "timestamp" to postedTime,
            "phoneNum" to mUserVO.phoneNumber.toString(),
            "photoOrVideoUrlLink" to imgList,
            "likedId" to likedId
        )

        db.collection(MOMENTS_COLLECTION)
            .document(currentTimestamp.toString())
            .set(momentMap)
            .addOnSuccessListener {
                Log.d("Success","Successfully added moment")
                onSuccess("Successfully added moment")

            }
            .addOnFailureListener {
                Log.d("Failure","Failed to add moment")
                onFailure(it.message.toString())
            }
    }
    override fun editMoment(
        momentVO: MomentVO,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    )
    {

        var urlData: ArrayList<MediaDataVO>? = momentVO.photoOrVideoUrlLink //as ArrayList<HashMap<String,String>>
        var mediaDataList:ArrayList<MediaDataVO> = arrayListOf()
        if (urlData != null) {
            for(mediaUrlData in urlData)
            {
                // mediaDataList.add(MediaDataVO(mediaUrlData["mediaType"],mediaUrlData["mediaDataLink"]))
                mediaDataList.add(MediaDataVO(mediaUrlData.mediaType,mediaUrlData.mediaDataLink))
            }
        }


        var likedData = momentVO.likedId as ArrayList<String>
        var likedDataList:ArrayList<String> = arrayListOf()
        for(likeData in likedData)
        {

            likedDataList.add(likeData)
        }


        val momentMap : HashMap<String,Any> = hashMapOf(
            "name" to momentVO.name.toString(),
            "description" to momentVO.description.toString(),
            "timestamp" to momentVO.timestamp.toString(),
            "phoneNum" to momentVO.phoneNumber.toString(),
            "photoOrVideoUrlLink" to mediaDataList,
            "likedId" to likedDataList
        )

        momentVO.id?.let {
            db.collection(MOMENTS_COLLECTION)
                .document(it)
                .set(momentMap)
                .addOnSuccessListener {
                    Log.d("Success","Successfully edited moment")
                    onSuccess("Successfully added moment")

                }
                .addOnFailureListener {
                    Log.d("Failure","Failed to edit moment")
                    onFailure(it.message.toString())
                }
        }
    }

    override fun addContacts(
        userId: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {

       // Log.d("cloudFirestore", "getUser function $phoneNum")
        //  val usersList: MutableList<UserVO> = arrayListOf()
        db.firestoreSettings = settings

        db.collection(USER_COLLECTION)
            .document(userId)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                }?: run {
                    val result : DocumentSnapshot? = value
                    Log.d("CloudFirebase","check scan qr data " +
                            "scan user = ${result?.data?.get("name")}" +
                            "---- current user = ${mUserVO.id}")

                    var contactVO = UserVO()

                    contactVO.name =result?.data?.get(DOCUMENT_FIELD_NAME) as String
                    contactVO.dateOfBirth = result?.data?.get(DOCUMENT_FIELD_DATE_OF_BIRTH) as String
                    contactVO.genderType = result?.data?.get(DOCUMENT_FIELD_GENDER_TYPE) as String
                    contactVO.phoneNumber = result?.data?.get(DOCUMENT_FIELD_PHONE_NUM) as String
                    contactVO.id = result?.data?.get(DOCUMENT_FIELD_UID) as String
                    mUserVO.id?.let {qrcode->

                        db.collection("users")
                            .document(qrcode)
                            .collection("contacts")
                            .document(userId)
                            .set(contactVO)
                            .addOnSuccessListener {
                                Log.d("Success","Successfully updated moment")
                               // onSuccess("Successfully updated moment")

                                var currentUserVO = UserVO()
                                currentUserVO.name = mUserVO.name
                                currentUserVO.dateOfBirth =  mUserVO.dateOfBirth
                                currentUserVO.genderType =  mUserVO.genderType
                                currentUserVO.phoneNumber =  mUserVO.phoneNumber
                                currentUserVO.id =  mUserVO.id
                                db.collection("users")
                                    .document(userId)
                                    .collection("contacts")
                                    .document(qrcode)
                                    .set(currentUserVO)
                                    .addOnSuccessListener {
                                        Log.d("Success","Successfully updated moment")
                                        onSuccess("Successfully updated moment")

                                    }
                                    .addOnFailureListener {e->
                                        Log.d("Failure","Failed to update moment")
                                        onFailure(e.message.toString())
                                    }


                            }
                            .addOnFailureListener {e->
                                Log.d("Failure","Failed to update moment")
                                onFailure(e.message.toString())
                            }



                    }

                   /* var checkFlag = false
                    var userData = UserVO()
                    val result : List<DocumentSnapshot> = value?.documents ?: arrayListOf()
                    outerLoop@for(document : DocumentSnapshot in result)
                    {
                        val data = document.data
                        if(data?.get("phoneNumber") == phoneNum && data?.get("password") == password)
                        {

                            userData.name = data["name"] as String
                            userData.dateOfBirth = data["dateOfBirth"] as String
                            userData.genderType = data["genderType"] as String
                            userData.password = data["password"] as String
                            userData.phoneNumber = data["phoneNumber"] as String
                            userData.qrCode = data["qr_code"] as String
                            mUserVO = userData
                            checkFlag = true

                            break@outerLoop
                        }else{
                            checkFlag = false
                        }

                    }

                    if(checkFlag)
                    {
                        onSuccess(userData)
                    }else{
                        onFailure("Login Failed.")
                    }*/
                }




            }
    }

    override fun getContacts(
        onSuccess: (contactsList: ArrayList<UserVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {

        var mContactsList:ArrayList<UserVO> = arrayListOf()
        db.firestoreSettings = settings
        mUserVO.id?.let { userid->
            db.collection(USER_COLLECTION)
                .document(userid)
                .collection(CONTACTS_COLLECTION)
                .addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->

                    Log.d("cloudfirestore","before check get contacts list ${querySnapshot!!.documentChanges.size}")


                    if (e != null) {
                    //   Log.w(TAG, "Listen error", e)
                    onFailure(e.message ?: " Please check connection ")
                    return@addSnapshotListener
                }

                for (change in querySnapshot!!.documentChanges) {
                    if (change.type == DocumentChange.Type.ADDED) {
                        Log.d("cloudfirestore","check get contacts list ${change.document.data["name"]}")
                        var contactUserVO = UserVO()
                        contactUserVO.name = change.document.data[DOCUMENT_FIELD_NAME] as String
                        contactUserVO.phoneNumber =  change.document.data[DOCUMENT_FIELD_PHONE_NUM] as String
                        contactUserVO.genderType =  change.document.data[DOCUMENT_FIELD_GENDER_TYPE] as String
                        contactUserVO.dateOfBirth =  change.document.data[DOCUMENT_FIELD_DATE_OF_BIRTH] as String
                        contactUserVO.id =  change.document.data[DOCUMENT_FIELD_UID] as String
                        mContactsList.add(contactUserVO)
                    /*var userVO = UserVO()
                        momentVO.id =  change.document.id
                        momentVO.name = change.document.data["name"] as String
                        momentVO.description = change.document.data["description"] as String
                        momentVO.phoneNumber = change.document.data["phoneNum"] as String
                        var urlData = change.document.data["photoOrVideoUrlLink"] as ArrayList<HashMap<String,String>>
                        var mediaDataList:ArrayList<MediaDataVO> = arrayListOf()
                        for(mediaUrlData in urlData)
                        {
                            mediaDataList.add(MediaDataVO(mediaUrlData["mediaType"],mediaUrlData["mediaDataLink"]))
                        }

                        momentVO.photoOrVideoUrlLink = mediaDataList
                        momentVO.timestamp = change.document.data["timestamp"] as String
                        momentVO.likedId = change.document.data["likedId"] as ArrayList<String>

                        mMomentsList.add(momentVO)*/
                    }

                }
                     onSuccess(mContactsList)

            }
        }

    }

    override fun editUser(userName: String, dateOfBirth: String, genderType: String,
    onSuccess: (message: String) -> Unit,
    onFailure: (message: String) -> Unit) {
        val editUserDataMap : HashMap<String,Any> = hashMapOf(
           DOCUMENT_FIELD_NAME to userName,
           DOCUMENT_FIELD_DATE_OF_BIRTH to dateOfBirth,
            DOCUMENT_FIELD_GENDER_TYPE to genderType,
            DOCUMENT_FIELD_PASSWORD to mUserVO.password.toString(),
            DOCUMENT_FIELD_PHONE_NUM to mUserVO.phoneNumber.toString(),
            DOCUMENT_FIELD_UID to mUserVO.id.toString()
        )

        db.collection(USER_COLLECTION)
            .document(mUserVO.id.toString())
            .set(editUserDataMap)
            .addOnSuccessListener {
                Log.d("Success","Successfully added user data")
                onSuccess("Saved Successfully")
            }
            .addOnFailureListener {
                Log.d("Failure","Failed to add user data")
                onFailure("Save Failed")
            }
    }

    override fun sendMessage(
        senderId: String,
        receiverId: String,
        msg: String,
        senderName: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {

    }


    /* override fun uploadImageUserVO(image: Bitmap, onSuccess: (returnUrlString: String?) -> Unit) {
         val baos = ByteArrayOutputStream()
         image.compress(Bitmap.CompressFormat.JPEG, 100,baos)
         val data: ByteArray = baos.toByteArray()

         val imageRef: StorageReference = storageReference.child("images/${UUID.randomUUID()}")
         val uploadTask: UploadTask = imageRef.putBytes(data)
         uploadTask.addOnFailureListener{

         }.addOnSuccessListener {
         }

         val urlTask: Task<Uri> = uploadTask.continueWithTask {
             return@continueWithTask imageRef.downloadUrl
         }.addOnCompleteListener { task->
             val imageUrl : String? = task.result?.toString()
             returnUrlString = imageUrl
             onSuccess(returnUrlString)

         }
     }*/

    override fun uploadImageAndVideoFile(fileUri: Uri, onSuccess: (returnUrlString: String?) -> Unit) {
      //  val videoRef: StorageReference = storageReference.child("videos/${UUID.randomUUID()}")

        val filesRef: StorageReference = storageReference.child("files/${UUID.randomUUID()}")
        val uploadTask: UploadTask = filesRef.putFile(fileUri)
        uploadTask.addOnFailureListener{

        }.addOnSuccessListener {
        }

        val urlTask: Task<Uri> = uploadTask.continueWithTask {
            return@continueWithTask filesRef.downloadUrl
        }.addOnCompleteListener { task->
            val imageUrl : String? = task.result?.toString()
            returnUrlString = imageUrl
            Log.d("CreateNewMoment","check image link at upload function 2= $returnUrlString")
            onSuccess(returnUrlString)

        }
    }



   override fun getMomentData(
        onSuccess: (momentsList: ArrayList<MomentVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
         var mMomentsList:ArrayList<MomentVO> = arrayListOf()
        db.firestoreSettings = settings
        db.collection(MOMENTS_COLLECTION).addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
                if (e != null) {
                    //   Log.w(TAG, "Listen error", e)
                    onFailure(e.message ?: " Please check connection ")
                    return@addSnapshotListener
                }

                for (change in querySnapshot!!.documentChanges) {
                    if (change.type == DocumentChange.Type.ADDED) {

                        var momentVO = MomentVO()
                        momentVO.id =  change.document.id
                        momentVO.name = change.document.data["name"] as String
                        momentVO.description = change.document.data["description"] as String
                        momentVO.phoneNumber = change.document.data["phoneNum"] as String
                        var urlData = change.document.data["photoOrVideoUrlLink"] as ArrayList<HashMap<String,String>>
                        var mediaDataList:ArrayList<MediaDataVO> = arrayListOf()
                        for(mediaUrlData in urlData)
                        {
                            mediaDataList.add(MediaDataVO(mediaUrlData["mediaType"],mediaUrlData["mediaDataLink"]))
                        }

                        momentVO.photoOrVideoUrlLink = mediaDataList
                        momentVO.timestamp = change.document.data["timestamp"] as String
                        momentVO.likedId = change.document.data["likedId"] as ArrayList<String>

                        mMomentsList.add(momentVO)
                    }

                    val source = if (querySnapshot.metadata.isFromCache)
                        "local cache"
                    else
                        "server"
                    // Log.d(TAG, "Data fetched from $source")
                }
                onSuccess(mMomentsList)

            }
        /* db.collection("users")
             .get()
             .addOnSuccessListener{result->
                 val usersList: MutableList<UserVO> = arrayListOf()

                 for (document in result)
                 {
                     val data = document.data
                     var userData = UserVO()
                     userData.name = data["name"] as String
                     userData.dateOfBirth = data["dateOfBirth"] as String
                     userData.genderType = data["genderType"] as String
                     userData.password = data["password"] as String
                     userData.phoneNumber = data["phoneNumber"] as String
                     usersList.add(userData)
                 }

                 onSuccess(usersList)
             }
             .addOnFailureListener {
                 onFailure(it.message ?: " Please check connection ")
             }*/
    }

}