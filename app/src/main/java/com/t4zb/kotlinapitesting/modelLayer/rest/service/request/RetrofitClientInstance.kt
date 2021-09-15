package com.t4zb.kotlinapitesting.modelLayer.rest.service.request

import android.content.Context
import com.t4zb.kotlinapitesting.modelLayer.network.cache.CacheClient
import com.t4zb.kotlinapitesting.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * #    Base Response event
 * [retrofit2.RequestBuilder] That we are going to use to create
 * our Retrofit instance using OkHttpClient to also [com.t4zb.kotlinapitesting.modelLayer.network.cache.CacheClient]
 * to cache our response
 *
 * ###   Important for [com.t4zb.kotlinapitesting.modelLayer.rest.service.repo.MovieRepository]
 *
 * Classes to check:
 *
 * [okhttp3.OkHttpClient]
 *
 * [retrofit2.Retrofit]
 *
 * [retrofit2.converter.gson.GsonConverterFactory]
 *
 * @author o00559125
 * @since 2021-08-23
 */
object RetrofitClientInstance {
    private var retrofit: Retrofit? = null

    fun buildRetrofit(context: Context): Retrofit? {
        val client = CacheClient.createCachedClient(context)
        if (retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}