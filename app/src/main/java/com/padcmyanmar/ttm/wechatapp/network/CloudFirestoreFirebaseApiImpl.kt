package com.padcmyanmar.ttm.wechatapp.network

import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.MetadataChanges

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import com.padcmyanmar.ttm.wechatapp.data.vos.UserVO


object CloudFirestoreFirebaseApiImpl : FirebaseApi{
   // private val db = Firebase.firestore



    // [START get_firestore_instance]
    val db = Firebase.firestore
    // [END get_firestore_instance]

    // [START set_firestore_settings]
    private val settings = firestoreSettings {
        isPersistenceEnabled = true
    }

    // [END set_firestore_settings]


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

    override fun getUsers(
        onSuccess: (usersList: List<UserVO>) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        val usersList: MutableList<UserVO> = arrayListOf()
        db.firestoreSettings = settings
        db.collection("users").whereEqualTo("phoneNumber", "09252685565")
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
                        usersList.add(userData)
                    }

                    val source = if (querySnapshot.metadata.isFromCache)
                        "local cache"
                    else
                        "server"
                   // Log.d(TAG, "Data fetched from $source")
                }
                onSuccess(usersList)
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