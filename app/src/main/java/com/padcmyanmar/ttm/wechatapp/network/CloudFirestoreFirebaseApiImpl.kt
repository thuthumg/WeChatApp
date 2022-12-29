package com.padcmyanmar.ttm.wechatapp.network

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.MetadataChanges

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.padcmyanmar.ttm.wechatapp.data.vos.MomentVO
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO
import java.io.ByteArrayOutputStream
import java.sql.Time
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


object CloudFirestoreFirebaseApiImpl : FirebaseApi{
    // [START get_firestore_instance]

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
        onSuccess: (message:String)->Unit,
        onFailure: (message:String) -> Unit
    ) {

        val userMap: HashMap<String,Any> = hashMapOf(
            "name" to name,
            "dateOfBirth" to dateOfBirth,
            "genderType" to gender,
            "password" to password,
            "phoneNumber" to phoneNum
        )
       Log.d("cloud ","check phone number $phoneNum")
        db.collection("users")
            .document(phoneNum)
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
        val usersList: MutableList<UserVO> = arrayListOf()
        db.firestoreSettings = settings
        db.collection("users").whereEqualTo("phoneNumber", phoneNum).whereEqualTo("password",password)
            .addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
                if (e != null) {
                 //   Log.w(TAG, "Listen error", e)
                    onFailure(e.message ?: " Please check connection ")
                    return@addSnapshotListener
                }

                for (change in querySnapshot!!.documentChanges) {
                    if (change.type == DocumentChange.Type.ADDED) {
                        Log.d("cloud", "check data: ${change.document.data}")


                        var userData = UserVO()
                        userData.name = change.document.data["name"] as String
                        userData.dateOfBirth = change.document.data["dateOfBirth"] as String
                        userData.genderType = change.document.data["genderType"] as String
                        userData.password = change.document.data["password"] as String
                        userData.phoneNumber = change.document.data["phoneNumber"] as String
                        mUserVO = userData
                        usersList.add(userData)
                    }

                    val source = if (querySnapshot.metadata.isFromCache)
                        "local cache"
                    else
                        "server"
                   // Log.d(TAG, "Data fetched from $source")
                }
                onSuccess(mUserVO)

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

    override fun addMoment(
        imgList: ArrayList<String>,
        likedId:ArrayList<String>,
        description: String,
        onSuccess: (message: String) -> Unit,
        onFailure: (message: String) -> Unit
    )
    {
//        DateTimeFormatter
//            .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
//            .withZone(ZoneOffset.UTC)
//            .format(Instant.now())
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
           // "timestampField" to currentTimestamp
        )

        db.collection("moments")
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

    override fun uploadImageUserVO(image: Bitmap, onSuccess: (returnUrlString: String?) -> Unit) {
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
           /* addMoment(
                name = momentVO.name ?: "",
                description = momentVO.description ?: "",
                timestamp = momentVO.timestamp ?: "",
                phoneNum = momentVO.phoneNumber ?: "",
                photoOrVideoLink = imageUrl ?: ""
            )*/

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
            /* addMoment(
                 name = momentVO.name ?: "",
                 description = momentVO.description ?: "",
                 timestamp = momentVO.timestamp ?: "",
                 phoneNum = momentVO.phoneNumber ?: "",
                 photoOrVideoLink = imageUrl ?: ""
             )*/
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
        db.collection("moments").addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
                if (e != null) {
                    //   Log.w(TAG, "Listen error", e)
                    onFailure(e.message ?: " Please check connection ")
                    return@addSnapshotListener
                }

                for (change in querySnapshot!!.documentChanges) {
                    if (change.type == DocumentChange.Type.ADDED) {
                        Log.d("cloud", "check data: ${change.document.data}")


                        var momentVO = MomentVO()
                        momentVO.id =  change.document.id
                        momentVO.name = change.document.data["name"] as String
                        momentVO.description = change.document.data["description"] as String
                        momentVO.phoneNumber = change.document.data["phoneNum"] as String
                        momentVO.photoOrVideoUrlLink = change.document.data["photoOrVideoUrlLink"] as ArrayList<String>
                        momentVO.timestamp = change.document.data["timestamp"] as String
                        momentVO.likedId = change.document.data["likedId"] as ArrayList<String>
                       // momentVO.timestampField = change.document.data["timestampField"] as Long

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