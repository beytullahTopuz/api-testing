package com.t4zb.kotlinapitesting.ui.fragment.login

data class LoginViewState(
    val isLoading: Boolean = false,
    val isFail: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMsg: String? = null,
)
