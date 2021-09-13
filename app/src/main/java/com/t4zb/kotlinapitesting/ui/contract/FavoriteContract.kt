package com.t4zb.kotlinapitesting.ui.contract

/**
 * com.t4zb.kotlinapitesting.ui.contract
 *
 * @author o00559125
 * @since 2021-09-13
 */
class FavoriteContract {
    interface ViewMain {
        fun initializeViews()
    }

    interface PresenterMain {
        fun onViewsCreated()
    }
}