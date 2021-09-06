package com.t4zb.kotlinapitesting.helper

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

/**
 * 功能描述
 *
 * @author o00559125
 * @since 2021-09-06
 */
object FirebaseDbHelper {

    fun rootRef() : DatabaseReference {
        return FirebaseDatabase.getInstance().reference
    }

    fun getUser(userID : String) : DatabaseReference {
        return FirebaseDatabase.getInstance().reference.child("User").child(userID)
    }

    fun onDisconnect(db : DatabaseReference) : Task<Void> {
        return db.child("onlineStatus").onDisconnect().setValue(ServerValue.TIMESTAMP)
    }

    fun onConnect(db : DatabaseReference) : Task<Void> {
        return db.child("onlineStatus").setValue("true")
    }

    fun getUserInfo(userID : String) : DatabaseReference {
        return FirebaseDatabase.getInstance().reference.child("UserInfo").child(userID)
    }
}