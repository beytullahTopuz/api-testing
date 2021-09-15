package com.t4zb.kotlinapitesting.appUser

import com.google.firebase.auth.FirebaseAuth

/**
 * App User
 *
 * @author o00559125
 * @since 2021-09-06
 */
object AppUser {

    private var userId = ""

    fun setUserId(userId: String) {
        if (userId != "")
            this.userId = userId
        else
            this.userId = "dummy"
    }

    fun getUserId() = userId

    fun getFirebaseUser() = FirebaseAuth.getInstance().currentUser
}