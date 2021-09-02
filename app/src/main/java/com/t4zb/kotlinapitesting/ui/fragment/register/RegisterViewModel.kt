package com.t4zb.kotlinapitesting.ui.fragment.register

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.t4zb.kotlinapitesting.util.showLogDebug

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.t4zb.kotlinapitesting.util.Constants


class RegisterViewModel : ViewModel() {

    var str_name: Editable? = null
    var str_surname: Editable? = null
    var str_email: Editable? = null
    var str_password: Editable? = null
    var str_passwordConfirm: Editable? = null

    private val firastoreDB = Firebase.firestore

    val registerViewState: MutableLiveData<RegisterViewState> = MutableLiveData()

    private fun currentRegisterViewState(): RegisterViewState = registerViewState.value!!


    init {
        registerViewState.value = RegisterViewState()
    }


    fun btn_register_with_google(firebaseAuth: FirebaseAuth?) {

        registerWithGoogle(firebaseAuth)
    }

    fun btn_register_onclick(firebaseAuth: FirebaseAuth?) {

        registerViewState.value = currentRegisterViewState().copy(
            isLoading = true
        )

        if (!formValidate()) {
            return
        }
        if (firebaseAuth != null) {
            registerWithEmail(firebaseAuth)
        } else {
            showLogDebug(TAG, "unknown error")
        }

    }


    private fun formValidate(): Boolean {
        /*   println("name : ${str_name}")
           println("surname : ${str_surname}")
           println("email : ${str_email}")
           println("paswors : ${str_password}")
           println("pasword comflm : ${str_passwordConfirm}")*/

        if (str_email.toString().isEmpty() || str_password.toString()
                .isEmpty() || str_surname.toString().isEmpty()
            || str_passwordConfirm.toString().isEmpty() || str_name.toString().isEmpty()
        ) {
            showLogDebug(TAG, "not all fields are filled")
            registerViewState.value = currentRegisterViewState().copy(
                errorMsg = "not all fields are filled",
                isFail = true
            )
            return false
        }
        if (!(str_password.toString().equals(str_passwordConfirm.toString()))) {
            showLogDebug(TAG, "password is different")
            registerViewState.value = currentRegisterViewState().copy(
                errorMsg = "password is different",
                isFail = true
            )
            return false
        }
        return true

    }

    private fun registerWithGoogle(firebaseAuth: FirebaseAuth?) {

    }

    private fun saveToDB(firebaseAuth: FirebaseAuth) {

        firastoreDB.collection(Constants.FIRABASE_COLLECTION_USER)
            .document(firebaseAuth.currentUser!!.uid).set(

                hashMapOf(
                    "name" to str_name.toString(),
                    "surname" to str_surname.toString(),
                    "email" to str_email.toString(),
                    "fotoURL" to ""
                )
            ).addOnCompleteListener { taskResult ->
                if (taskResult.isSuccessful) {

                    showLogDebug(TAG,"User inserted to DB")
                }else{
                        firebaseAuth.currentUser!!.delete()
                    showLogDebug(TAG,"ERROR : ${taskResult.exception?.message}")
                }

            }

    }


    private fun registerWithEmail(firebaseAuth: FirebaseAuth) {
        firebaseAuth.createUserWithEmailAndPassword(str_email.toString(), str_password.toString())
            .addOnCompleteListener { taskResult ->
                if (taskResult.isSuccessful) {
                    registerViewState.value = currentRegisterViewState().copy(
                        isSuccess = true,
                        isLoading = false
                    )
                    saveToDB(firebaseAuth)
                    showLogDebug(TAG, "login successful")
                } else {
                    registerViewState.value = currentRegisterViewState().copy(
                        isLoading = false,
                        isSuccess = false,
                        isFail = true,
                        errorMsg = taskResult.exception?.message.toString()
                    )
                    showLogDebug(TAG, "ERROR: ${taskResult.exception?.message.toString()}")
                }
            }
    }

    companion object {
        private const val TAG = "RegisterViewModel"
    }
}