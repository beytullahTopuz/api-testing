package com.t4zb.kotlinapitesting.modelLayer.rest.service.response

/**
 * #    Tables
 *
 *      Type       |    Name
 *      --------------------------------
 *      Boolean         adult
 *      String          backdrop_path
 *      Int             id
 *      String          original_language
 *      String          original_title
 *      String          overview
 *      Double          popularity
 *      String          poster_path
 *      String          release_date
 *      String          title
 *      Boolean         video
 *      Double          vote_average
 *      Int             vote_count
 *
 * @author o00559125
 * @since 2021-08-23
 */
data class MoviesPopularity (
    val adult: Boolean,
    var backdrop_path: String,
    var id: Int,
    var original_language: String,
    var original_title: String,
    var overwiew : String,
    var popularity: String,
    var poster_path: String,
    var release_date: String,
    var title : String,
    var video:Boolean,
    var vote_average : Double,
    var vote_count: Int

)