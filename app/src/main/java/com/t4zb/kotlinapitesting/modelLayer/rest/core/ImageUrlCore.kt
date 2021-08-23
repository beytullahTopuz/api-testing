package com.t4zb.kotlinapitesting.modelLayer.rest.core

import android.net.Uri

/**
 * Handles movie image base url
 *
 *      "https://image.tmdb.org/t/p/"
 *
 * Use image size as "w185"
 *
 * Check [android.net.Uri]
 *
 * @author o00559125
 * @since 2021-08-23
 */
object ImageUrlCore {

    fun buildImageCore(relativePath: String): String {
        return Uri.parse(MovieUrl).buildUpon()
            .appendPath(imageSize)
            .appendEncodedPath(relativePath)
            .build().toString()
    }
}