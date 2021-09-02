package com.t4zb.kotlinapitesting.ui.fragment.login

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.t4zb.kotlinapitesting.util.showLogDebug

class LoginViewModel : ViewModel() {

    var str_email: Editable? = null
    var str_password: Editable? = null

    val loginViewState: MutableLiveData<LoginViewState> = MutableLiveData()

    private fun currentLoginViewState(): LoginViewState = loginViewState.value!!

    init {
        loginViewState.value = LoginViewState()
    }

    fun btn_login_onclick(firebaseAuth: FirebaseAuth?) {
        if (!formValidate()) {
            return
        }
        if (firebaseAuth != null) {
            loginWithEmail(firebaseAuth)
        } else {
            showLogDebug(TAG, "unknown error")
        }

    }

    private fun formValidate(): Boolean {

        println("email : ${str_email}")
        println("passowrd : ${str_password}")
        if (str_email.toString().isEmpty() || str_password.toString().isEmpty()) {
            showLogDebug(TAG, "Email and password cannot be left blank")
            loginViewState.value = currentLoginViewState().copy(
                isFail = true,
                errorMsg = "Email and password cannot be left blank"
            )
            return false
        }
        return true
    }

    private fun loginWithEmail(firebaseAuth: FirebaseAuth) {
        firebaseAuth.signInWithEmailAndPassword(str_email.toString(), str_password.toString())
            .addOnCompleteListener { taskResult ->
                if (taskResult.isSuccessful) {
                    showLogDebug(TAG, "login successful")
                    loginViewState.value = currentLoginViewState().copy(
                        isSuccess = true,
                    )
                } else {
                    showLogDebug(TAG, "ERROR: ${taskResult.exception?.message.toString()}")
                    loginViewState.value = currentLoginViewState().copy(
                        isFail = true,
                        errorMsg = taskResult.exception?.message.toString()
                    )
                }
            }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}