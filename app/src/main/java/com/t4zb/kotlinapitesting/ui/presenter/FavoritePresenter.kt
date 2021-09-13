package com.t4zb.kotlinapitesting.ui.presenter

import android.widget.Adapter
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.t4zb.kotlinapitesting.ui.contract.FavoriteContract
import com.t4zb.kotlinapitesting.util.showLogDebug

/**
 * com.t4zb.kotlinapitesting.ui.presenter
 *
 * @author o00559125
 * @since 2021-09-13
 */
class FavoritePresenter constructor(contract : FavoriteContract.ViewMain): FavoriteContract.PresenterMain {
    private val mContract = contract

    override fun onViewsCreated() {
        mContract.initializeViews()
        showLogDebug(TAG, "initialization Started")
    }

    fun removeItems(adapter: Adapter){

    }

    companion object {
        private const val TAG = "BasePresenter"
    }
}