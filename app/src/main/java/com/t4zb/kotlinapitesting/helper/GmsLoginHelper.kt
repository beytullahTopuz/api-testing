package com.t4zb.kotlinapitesting.helper

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.ui.contract.*
import com.t4zb.kotlinapitesting.util.showLogDebug
import com.t4zb.kotlinapitesting.util.showLogError
import com.t4zb.kotlinapitesting.util.showToast
import com.t4zb.kotlinapitesting.util.toSafeString

/**
 * 功能描述
 *
 * @author o00559125
 * @since 2021-09-02
 */
class GmsLoginHelper(context: Context) : LoginHelper {
    // Any num could be request code
    // It is unique through out
    private val googleRequestCode = 1997
    private val cntx = context

    private val gso: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestId()
            .requestEmail()
            .build()
    }

    private val googleSignInClient: GoogleSignInClient by lazy {
        GoogleSignIn.getClient(context, gso)
    }

    private lateinit var loginHelperCallback: LoginHelperCallback

    override fun onLoginClick(loginType: LoginType) {
        when (loginType) {
            LoginType.GOOGLE -> onGoogleLogInClick()
            LoginType.EMAIL -> onEmailLogInClick()
            else -> onGoogleLogInClick()
        }
    }

    private fun onGoogleLogInClick() {
        loginHelperCallback.redirectToSignIn(googleSignInClient.signInIntent, googleRequestCode)
    }

    private fun onEmailLogInClick() {
        // Email Login
    }

    override fun checkSilentSignIn() {
        checkGoogleSilentSignIn()
    }

    private fun checkGoogleSilentSignIn() {
        googleSignInClient.silentSignIn().addOnSuccessListener { gAccount ->
            firebaseAuthWithGoogle(gAccount, isSilentLogin = true)
        }.addOnCanceledListener {
            loginHelperCallback.onSilentSignInFail()
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount, isSilentLogin: Boolean = false) {
        val auth = FirebaseAuth.getInstance()
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val loginUserData = LoginUserData(
                    userId = user?.uid.toSafeString(),
                    loginType = LoginType.GOOGLE
                )
                val loginUserInfoData = LoginUserInfoData(
                    uid = user?.uid.toSafeString(),
                    name = user?.displayName.toSafeString(),
                    surname = "Surname",
                    email = user?.email.toSafeString(),
                    avatar_url = user?.photoUrl.toString(),
                )
                showLogDebug(TAG, "SignInWithCredential:success")
                if (isSilentLogin)
                    loginHelperCallback.onSilentSignInSuccess(loginUserData)
                else
                    loginHelperCallback.onLoginSuccess(loginUserData, loginUserInfoData)
            } else
                loginHelperCallback.onLoginFail("Firebase Google Auth Fail")
        }
    }

    override fun onDataReceived(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            googleRequestCode -> onGoogleSignInDataReceived(data)
        }
    }

    override fun setCallback(loginHelperCallback: LoginHelperCallback) {
        this.loginHelperCallback = loginHelperCallback
    }

    override fun sendVerificationCode(email: String, buttonString: String, interval: Int) {
        val btn = buttonString
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener {
            if (it.isSuccessful) {
                showToast(cntx, "Please wait verification code, and then type it and press Login")
            }
        }
    }

    override fun onLoginEmail(email: String, password: String, verifyCode: String) {
        // Login Email
    }

    private fun onGoogleSignInDataReceived(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account!!)
        } catch (e: ApiException) {
            showLogError(TAG, "Google SignIn Failed: $e")
        }
    }

    companion object {
        private const val TAG = "GmsLoginHelper"
    }

}