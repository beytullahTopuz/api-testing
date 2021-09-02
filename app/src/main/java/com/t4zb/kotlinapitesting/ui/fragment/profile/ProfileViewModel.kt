package com.t4zb.kotlinapitesting.ui.fragment.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel: ViewModel() {

    fun singOut(firebaseAuth: FirebaseAuth){
        firebaseAuth.signOut()
    }

    fun getUserEmail(firebaseAuth: FirebaseAuth): String{
        return firebaseAuth.currentUser?.email.toString()
    }
}