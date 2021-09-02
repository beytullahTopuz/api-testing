package com.t4zb.kotlinapitesting.modelLayer

data class UserModel(
    val uid: String,
    val name: String,
    val surname: String,
    val email: String,
    val avatar_url: String?
)