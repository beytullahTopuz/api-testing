package com.t4zb.kotlinapitesting.modelLayer.network.cache

import android.content.Context
import com.t4zb.kotlinapitesting.modelLayer.network.NetworkUtils
import okhttp3.*
import java.io.File
import java.util.*

/**
 * Cache Operations
 *
 * @author o00559125
 * @since 2021-08-23
 */
object CacheClient {
    fun createCachedClient(context: Context): OkHttpClient? {
        val cacheDirectory = File(context.cacheDir, "cache_file")
        val cacheSize = Cache(cacheDirectory, 20 * 1024 * 1024)

        val interceptor = Interceptor {
            val originalResponse = it.proceed(it.request())
            return@Interceptor if (NetworkUtils.isNetworkAvailable(context)) {
                val maxAge = 60 // read from 1 minute
                originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .build()
            } else {
                val maxStale = 60 * 60 * 24 * 4
                originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-stale=$maxStale")
                    .build()
            }
        }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .cache(cacheSize)
            .build()
    }
}