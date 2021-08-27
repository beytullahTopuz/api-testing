package com.t4zb.kotlinapitesting.ui.presenter

import com.t4zb.kotlinapitesting.ui.contract.BaseContract
import com.t4zb.kotlinapitesting.util.showLogDebug

/**
 * 功能描述
 *
 * @author o00559125
 * @since 2021-08-23
 */
class BasePresenter constructor(contract: BaseContract.ViewMain) : BaseContract.PresenterMain {
    private val mContract = contract

    override fun onViewsCreated() {
        mContract.setupViewModel()
        mContract.initializeViews()
        showLogDebug(TAG, "initialization Started")
    }

    companion object {
        private const val TAG = "BasePresenter"
    }
}