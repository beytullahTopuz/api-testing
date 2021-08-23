package com.t4zb.kotlinapitesting.modelLayer.network

import android.content.Context
import android.net.ConnectivityManager

/**
 * Mainly for managing [android.net.ConnectivityManager]
 *
 * @author o00559125
 * @since 2021-08-23
 */
object NetworkUtils {
    @Suppress("DEPRECATION")
    fun isNetworkAvailable(app: Context): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }
}