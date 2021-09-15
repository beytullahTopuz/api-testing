package com.t4zb.kotlinapitesting.helper

import android.content.Context
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.behavior.SwipeDismissBehavior
import com.google.android.material.card.MaterialCardView
import com.t4zb.kotlinapitesting.util.showLogDebug

/**
 * SwipeDismissActions
 *
 * @author o00559125
 * @since 2021-09-08
 */
object SwipeDismissActions {

    fun fragmentDismiss(view: View){
        val swipe = SwipeDismissBehavior<MaterialCardView>()
        swipe.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END)

        swipe.listener = object : SwipeDismissBehavior.OnDismissListener{
            override fun onDismiss(view: View?) {
                Navigation.findNavController(view!!).navigateUp()
            }

            override fun onDragStateChanged(state: Int) {
                showLogDebug(this.javaClass.simpleName,"Drag State $state ")
            }
        }
        val params = view.layoutParams as CoordinatorLayout.LayoutParams
        params.behavior = swipe
    }
}