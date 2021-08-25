package com.t4zb.kotlinapitesting

import android.app.Application
import android.content.Context

/**
 * Main Application class for initial registry
 *
 * @author o00559125
 * @since 2021-08-23
 */
class KotlinAppTesting: Application() {

    override fun onCreate() {
        super.onCreate()
        setApp(this)
    }

    companion object {
        private const val TAG = "MainApplication"

        var instance: KotlinAppTesting? = null
            private set

        val context: Context
            get() = instance!!.applicationContext

        @Synchronized
        private fun setApp(application: KotlinAppTesting) {
            instance = application
        }
    }
}