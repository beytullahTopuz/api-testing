package com.t4zb.kotlinapitesting.util

import android.view.View

/**
 * 功能描述
 *
 * @author o00559125
 * @since 2021-09-02
 */
fun String?.toSafeString(): String {
    return this ?: ""
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.setVisible(b: Boolean) {
    if (b)
        this.visibility = View.VISIBLE
    else
        this.visibility = View.GONE
}

fun View.expandView() {
    val isViewVisible = when (this.visibility) {
        View.VISIBLE -> true
        else -> false
    }
    if (isViewVisible) {
        setVisible(false)
    } else {
        setVisible(true)
    }
}