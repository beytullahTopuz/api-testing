package com.t4zb.kotlinapitesting.ui.presenter

import android.content.Intent
import com.t4zb.kotlinapitesting.appUser.AppUser
import com.t4zb.kotlinapitesting.helper.FirebaseDbHelper
import com.t4zb.kotlinapitesting.ui.contract.*
import com.t4zb.kotlinapitesting.util.showLogDebug
import com.t4zb.kotlinapitesting.util.showLogError
import com.t4zb.kotlinapitesting.util.toSafeString

/**
 * 功能描述
 *
 * @author o00559125
 * @since 2021-09-06
 */
class LoginPresenter constructor(
    private val loginContract: LoginContract,
    private val iLoginHelper: LoginHelper
) : LoginHelperCallback{

    fun onViewsCreate() {
        iLoginHelper.setCallback(this)
        iLoginHelper.checkSilentSignIn()
        loginContract.initializeViews()
    }

    fun onEmailClick(){
        iLoginHelper.setCallback(this)
    }

    fun onSignInGClick(){
        iLoginHelper.onLoginClick(LoginType.GOOGLE)
    }

    override fun onLoginSuccess(
        loginUserData: LoginUserData,
        loginUserInfoData: LoginUserInfoData
    ) {
        iLoginHelper.setCallback(this)
        AppUser.setUserId(loginUserData.userId)
        updateUserDB(loginUserData)
        updateUserInfoDB(loginUserInfoData)
    }

    override fun onLoginFail(errorMessage: String) {
        showLogError(TAG,errorMessage)
    }

    override fun updateUserDB(loginUserData: LoginUserData) {
        val userDbRef = FirebaseDbHelper.getUser(loginUserData.userId)
        val userMap = HashMap<String, String>()
        userMap["tokenID"] = loginUserData.userId
        userMap["signInMethod"] = loginUserData.loginType.displayName

        userDbRef.setValue(userMap).addOnCompleteListener {
            if (it.isSuccessful)
                loginContract.redirectToUserProfile()
            else
                showLogError(TAG,"on update fail")
        }
    }

    override fun updateUserInfoDB(loginUserInfoData: LoginUserInfoData) {
        val userInfoDbRef = FirebaseDbHelper.getUserInfo(loginUserInfoData.uid)
        val userInfoMap = HashMap<String, String>()
        userInfoMap["uid"] = loginUserInfoData.uid.toSafeString()
        userInfoMap["name"] = loginUserInfoData.name.toSafeString()
        userInfoMap["surname"] = loginUserInfoData.surname.toSafeString()
        userInfoMap["email"] = loginUserInfoData.email.toSafeString()
        userInfoMap["avatar_url"] = loginUserInfoData.avatar_url.toSafeString()

        userInfoDbRef.setValue(userInfoMap).addOnCompleteListener {
            if (it.isSuccessful)
                showLogDebug(TAG, "on update success")
            else
                showLogError(TAG, "on update fail")
        }
    }

    override fun onSilentSignInFail() {
        showLogError(this.javaClass.simpleName,"Sign failed")
    }

    override fun redirectToEmail() {
        showLogDebug(TAG,"Redirecting")
    }

    override fun redirectToSignIn(signInIntent: Intent, requestCode: Int) {
        loginContract.redirectToSignIn(signInIntent,requestCode)
    }

    override fun onSilentSignInSuccess(loginUserData: LoginUserData) {
        AppUser.setUserId(loginUserData.userId)
        loginContract.redirectToUserProfile()
    }

    interface LoginContract{
        fun redirectToUserProfile()
        fun restartApp()
        fun redirectToSignIn(signInIntent: Intent, requestCode: Int)
        fun initializeViews()
    }

    companion object{
        private const val TAG = "LoginPresenter"
    }
}