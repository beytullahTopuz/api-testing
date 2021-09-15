package com.t4zb.kotlinapitesting.ui.fragment.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.t4zb.kotlinapitesting.modelLayer.UserModel
import com.t4zb.kotlinapitesting.util.Constants
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat.checkSelfPermission

class ProfileViewModel : ViewModel() {
    private val firastoreDB = Firebase.firestore
    val user: MutableLiveData<UserModel> = MutableLiveData()

    fun singOut(firebaseAuth: FirebaseAuth) {
        firebaseAuth.signOut()
    }

    fun getUserEmail(firebaseAuth: FirebaseAuth): String {
        return firebaseAuth.currentUser?.email.toString()
    }

    fun getUserToDB(firebaseAuth: FirebaseAuth) {
        val currentId = firebaseAuth.currentUser?.uid.toString()

        firastoreDB.collection(Constants.FIRABASE_COLLECTION_USER).document(currentId)
            .get(Source.DEFAULT).addOnCompleteListener { taskResult ->
                if (taskResult.isSuccessful) {

                    val json = taskResult.result!!
                    val tempUser = UserModel(
                        json.id,
                        json["name"].toString(),
                        json["surname"].toString(),
                        json["email"].toString(),
                        json["fotoURL"].toString()
                    )
                    user.value = tempUser
                }

            }

    }
}