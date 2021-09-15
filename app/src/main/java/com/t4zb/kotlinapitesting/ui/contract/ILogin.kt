package com.t4zb.kotlinapitesting.ui.contract

import android.content.Intent

/**
 * LoginHelper
 *
 * @author o00559125
 * @since 2021-09-02
 */
interface LoginHelper {
    fun onLoginClick(loginType: LoginType)
    fun checkSilentSignIn()
    fun onDataReceived(requestCode: Int, resultCode: Int, data: Intent?)
    fun setCallback(loginHelperCallback: LoginHelperCallback)
    fun onLoginEmail(email: String, password: String, verifyCode: String)
    fun sendVerificationCode(email: String, buttonString: String, interval: Int)
}

interface LoginHelperCallback {
    fun onLoginSuccess(loginUserData: LoginUserData, loginUserInfoData: LoginUserInfoData)
    fun onLoginFail(errorMessage: String)
    fun updateUserDB(loginUserData: LoginUserData)
    fun updateUserInfoDB(loginUserInfoData: LoginUserInfoData)
    fun onSilentSignInFail()
    fun redirectToEmail()
    fun redirectToSignIn(signInIntent: Intent, requestCode: Int)
    fun onSilentSignInSuccess(loginUserData: LoginUserData)
}

enum class LoginType(val displayName: String) {
    EMAIL("Email"),
    GOOGLE("Google")
}

data class LoginUserData(
    val userId: String,
    val loginType: LoginType
)

data class LoginUserInfoData(
    val name: String,
    val surname: String,
    val email: String,
    val uid: String,
    val avatar_url: String
)