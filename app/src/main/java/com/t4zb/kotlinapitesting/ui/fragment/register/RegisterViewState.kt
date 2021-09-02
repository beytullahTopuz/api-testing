package com.t4zb.kotlinapitesting.ui.fragment.register

data class RegisterViewState(
    val isLoading: Boolean = false,
    val isFail: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMsg: String? = null,
)