package com.padcmyanmar.ttm.wechatapp.network

import android.annotation.SuppressLint
import android.graphics.Bitmap
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
import java.io.ByteArrayOutputStream
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
        profileImageUrl: String,
        activeStatus: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    ) {

        val userMap: HashMap<String,Any> = hashMapOf(
            USER_DOCUMENT_FIELD_NAME to name,
            USER_DOCUMENT_FIELD_DATE_OF_BIRTH to dateOfBirth,
            USER_DOCUMENT_FIELD_GENDER_TYPE to gender,
            USER_DOCUMENT_FIELD_PASSWORD to password,
            USER_DOCUMENT_FIELD_PHONE_NUM to phoneNum,
            USER_DOCUMENT_FIELD_UID to userId,
            USER_DOCUMENT_FIELD_PROFILE_URL to profileImageUrl,
            USER_DOCUMENT_FIELD_LOGIN_ACTIVE_STATUS to activeStatus
        )
        db.collection(USER_COLLECTION)
            .document(userId)
            .set(userMap)
            .addOnSuccessListener {
                onSuccess("Saved Successfully")
            }
            .addOnFailureListener {
                onFailure("Save Failed")
            }
    }

    override fun getUser(
        phoneNum: String,
        password: String,
        onSuccess: (userVO: UserVO) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
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
                        if(data?.get(USER_DOCUMENT_FIELD_PHONE_NUM) == phoneNum && data?.get(
                                USER_DOCUMENT_FIELD_PASSWORD) == password)
                        {

                            userData.name = data[USER_DOCUMENT_FIELD_NAME] as String
                            userData.dateOfBirth = data[USER_DOCUMENT_FIELD_DATE_OF_BIRTH] as String
                            userData.genderType = data[USER_DOCUMENT_FIELD_GENDER_TYPE] as String
                            userData.password = data[USER_DOCUMENT_FIELD_PASSWORD] as String
                            userData.phoneNumber = data[USER_DOCUMENT_FIELD_PHONE_NUM] as String
                            userData.id = data[USER_DOCUMENT_FIELD_UID] as String
                            userData.profileUrl = data[USER_DOCUMENT_FIELD_PROFILE_URL] as String
                            userData.activeStatus = "1"
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
            USER_DOCUMENT_FIELD_NAME to mUserVO.name.toString(),
            MOMENT_DOCUMENT_FIELD_DESCRIPTION to description,
            MOMENT_DOCUMENT_FIELD_TIMESTAMP to postedTime,
            USER_DOCUMENT_FIELD_PHONE_NUM to mUserVO.phoneNumber.toString(),
            MOMENT_DOCUMENT_PHOTO_VIDEO_LINK to imgList,
            MOMENT_DOCUMENT_FIELD_LIKED_ID to likedId,
            MOMENT_DOCUMENT_FIELD_BOOK_MARKED_ID to arrayListOf<String>(),
            USER_DOCUMENT_FIELD_PROFILE_URL to mUserVO.profileUrl.toString()
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


        var bookMarkedData = momentVO.bookMarkedId as ArrayList<String>
        var bookMarkDataList:ArrayList<String> = arrayListOf()
        for(bookMarkData in bookMarkedData)
        {
            bookMarkDataList.add(bookMarkData)
        }
        val momentMap : HashMap<String,Any> = hashMapOf(
            USER_DOCUMENT_FIELD_NAME to momentVO.name.toString(),
            MOMENT_DOCUMENT_FIELD_DESCRIPTION to momentVO.description.toString(),
            MOMENT_DOCUMENT_FIELD_TIMESTAMP to momentVO.timestamp.toString(),
            USER_DOCUMENT_FIELD_PHONE_NUM to momentVO.phoneNumber.toString(),
            MOMENT_DOCUMENT_PHOTO_VIDEO_LINK to mediaDataList,
            MOMENT_DOCUMENT_FIELD_LIKED_ID to likedDataList,
            MOMENT_DOCUMENT_FIELD_BOOK_MARKED_ID to bookMarkDataList,
            USER_DOCUMENT_FIELD_PROFILE_URL to momentVO.profileUrl.toString()
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

        db.firestoreSettings = settings
        db.collection(USER_COLLECTION)
            .document(userId)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                }?: run {
                    val result : DocumentSnapshot? = value

                    var contactVO = UserVO()

                    contactVO.name =result?.data?.get(USER_DOCUMENT_FIELD_NAME) as String
                    contactVO.dateOfBirth = result?.data?.get(USER_DOCUMENT_FIELD_DATE_OF_BIRTH) as String
                    contactVO.genderType = result?.data?.get(USER_DOCUMENT_FIELD_GENDER_TYPE) as String
                    contactVO.phoneNumber = result?.data?.get(USER_DOCUMENT_FIELD_PHONE_NUM) as String
                    contactVO.id = result?.data?.get(USER_DOCUMENT_FIELD_UID) as String
                    contactVO.profileUrl =  result?.data?.get(USER_DOCUMENT_FIELD_PROFILE_URL) as String
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
                                contactVO.profileUrl =  mUserVO.profileUrl
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
                    if (e != null) {
                    //   Log.w(TAG, "Listen error", e)
                    onFailure(e.message ?: " Please check connection ")
                    return@addSnapshotListener
                }

                for (change in querySnapshot!!.documentChanges) {
                    if (change.type == DocumentChange.Type.ADDED) {

                        val contactUserVO = UserVO()
                        contactUserVO.name = change.document.data[USER_DOCUMENT_FIELD_NAME] as String
                        contactUserVO.phoneNumber =  change.document.data[USER_DOCUMENT_FIELD_PHONE_NUM] as String
                        contactUserVO.genderType =  change.document.data[USER_DOCUMENT_FIELD_GENDER_TYPE] as String
                        contactUserVO.dateOfBirth =  change.document.data[USER_DOCUMENT_FIELD_DATE_OF_BIRTH] as String
                        contactUserVO.id =  change.document.data[USER_DOCUMENT_FIELD_UID] as String
                        contactUserVO.profileUrl = change.document.data[USER_DOCUMENT_FIELD_PROFILE_URL] as String
                        mContactsList.add(contactUserVO)

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
           USER_DOCUMENT_FIELD_NAME to userName,
           USER_DOCUMENT_FIELD_DATE_OF_BIRTH to dateOfBirth,
            USER_DOCUMENT_FIELD_GENDER_TYPE to genderType,
            USER_DOCUMENT_FIELD_PASSWORD to mUserVO.password.toString(),
            USER_DOCUMENT_FIELD_PHONE_NUM to mUserVO.phoneNumber.toString(),
            USER_DOCUMENT_FIELD_UID to mUserVO.id.toString(),
            USER_DOCUMENT_FIELD_PROFILE_URL to mUserVO.profileUrl.toString(),
            USER_DOCUMENT_FIELD_LOGIN_ACTIVE_STATUS to mUserVO.activeStatus
        )

        db.collection(USER_COLLECTION)
            .document(mUserVO.id.toString())
            .set(editUserDataMap)
            .addOnSuccessListener {

                onSuccess("Saved Successfully")
            }
            .addOnFailureListener {

                onFailure("Save Failed")
            }
    }

    override fun uploadImageAndEditUserVO(
        image: Bitmap,
        userVO: UserVO,
        onSuccess: (returnStringData: String?) -> Unit
    ) {
        val byteArrayOutput = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100,byteArrayOutput)
        val data: ByteArray = byteArrayOutput.toByteArray()

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
            addUser(
                name=userVO.name ?: "",
                dateOfBirth = userVO.dateOfBirth ?: "",
                gender = userVO.genderType ?: "",
                password = userVO.password ?: "",
                phoneNum = userVO.phoneNumber ?: "",
                userId = userVO.id ?: "",
                profileImageUrl = imageUrl ?: "",
                activeStatus = mUserVO.activeStatus,
                onSuccess = {}) {}

            onSuccess(returnUrlString)

        }
    }

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
            onSuccess(returnUrlString)

        }
    }



   override fun getMomentData(
        onSuccess: (momentsList: ArrayList<MomentVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
         val mMomentsList:ArrayList<MomentVO> = arrayListOf()
        db.firestoreSettings = settings
        db.collection(MOMENTS_COLLECTION).addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
                if (e != null) {
                    //   Log.w(TAG, "Listen error", e)
                    onFailure(e.message ?: " Please check connection ")
                    return@addSnapshotListener
                }

                for (change in querySnapshot!!.documentChanges) {
                    if (change.type == DocumentChange.Type.ADDED) {

                        val momentVO = MomentVO()
                        momentVO.id =  change.document.id
                        momentVO.name = change.document.data[USER_DOCUMENT_FIELD_NAME] as String
                        momentVO.description = change.document.data[MOMENT_DOCUMENT_FIELD_DESCRIPTION] as String
                        momentVO.phoneNumber = change.document.data[USER_DOCUMENT_FIELD_PHONE_NUM] as String
                        val urlData = change.document.data[MOMENT_DOCUMENT_PHOTO_VIDEO_LINK] as ArrayList<HashMap<String,String>>
                        val mediaDataList:ArrayList<MediaDataVO> = arrayListOf()
                        for(mediaUrlData in urlData)
                        {
                            mediaDataList.add(MediaDataVO(mediaUrlData[MOMENT_DOCUMENT_FIELD_MEDIA_TYPE],
                                mediaUrlData[MOMENT_DOCUMENT_FIELD_MEDIA_DATA_LINK]))
                        }

                        momentVO.photoOrVideoUrlLink = mediaDataList
                        momentVO.timestamp = change.document.data[MOMENT_DOCUMENT_FIELD_TIMESTAMP] as String
                        momentVO.likedId = change.document.data[MOMENT_DOCUMENT_FIELD_LIKED_ID] as ArrayList<String>
                        momentVO.bookMarkedId = change.document.data[MOMENT_DOCUMENT_FIELD_BOOK_MARKED_ID] as ArrayList<String>
                        momentVO.profileUrl = change.document.data[USER_DOCUMENT_FIELD_PROFILE_URL] as String

                        mMomentsList.add(momentVO)
                    }

                    val source = if (querySnapshot.metadata.isFromCache)
                        "local cache"
                    else
                        "server"
                }
                onSuccess(mMomentsList)

            }

    }


    override fun getMomentDataByBookMarkList(
        onSuccess: (momentsList: ArrayList<MomentVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        val mMomentsList:ArrayList<MomentVO> = arrayListOf()
        db.firestoreSettings = settings
        db.collection(MOMENTS_COLLECTION).addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
            if (e != null) {
                //   Log.w(TAG, "Listen error", e)
                onFailure(e.message ?: " Please check connection ")
                return@addSnapshotListener
            }

            for (change in querySnapshot!!.documentChanges) {
                if (change.type == DocumentChange.Type.ADDED) {

                    val momentVO = MomentVO()
                    val bookMarkArray = change.document.data[MOMENT_DOCUMENT_FIELD_BOOK_MARKED_ID]as ArrayList<String>
                    val checkPhoneNum = change.document.data[USER_DOCUMENT_FIELD_PHONE_NUM] as String

                    if(bookMarkArray.isNotEmpty() && checkPhoneNum == mUserVO.phoneNumber){
                        momentVO.id =  change.document.id
                        momentVO.name = change.document.data[USER_DOCUMENT_FIELD_NAME] as String
                        momentVO.description = change.document.data[MOMENT_DOCUMENT_FIELD_DESCRIPTION] as String
                        momentVO.phoneNumber = change.document.data[USER_DOCUMENT_FIELD_PHONE_NUM] as String
                        val urlData = change.document.data[MOMENT_DOCUMENT_PHOTO_VIDEO_LINK] as ArrayList<HashMap<String,String>>
                        val mediaDataList:ArrayList<MediaDataVO> = arrayListOf()
                        for(mediaUrlData in urlData)
                        {
                            mediaDataList.add(MediaDataVO(mediaUrlData[MOMENT_DOCUMENT_FIELD_MEDIA_TYPE],
                                mediaUrlData[MOMENT_DOCUMENT_FIELD_MEDIA_DATA_LINK]))
                        }

                        momentVO.photoOrVideoUrlLink = mediaDataList
                        momentVO.timestamp = change.document.data[MOMENT_DOCUMENT_FIELD_TIMESTAMP] as String
                        momentVO.likedId = change.document.data[MOMENT_DOCUMENT_FIELD_LIKED_ID] as ArrayList<String>
                        momentVO.bookMarkedId = change.document.data[MOMENT_DOCUMENT_FIELD_BOOK_MARKED_ID] as ArrayList<String>
                        momentVO.profileUrl = change.document.data[USER_DOCUMENT_FIELD_PROFILE_URL] as String

                        mMomentsList.add(momentVO)

                    }


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