package com.t4zb.kotlinapitesting.modelLayer.rest.service.response

/**
 * #    Tables
 *
 *      Type       |    Name
 *      --------------------------------
 *      Double          popularity
 *      String          vote_count
 *      String          poster_path
 *      Int             id
 *      Boolean         adult
 *      String          backdrop_path
 *      String          original_language
 *      String          original_title
 *      String          title
 *      Double          vote_average
 *      String          overview
 *      String          release_date
 *
 * @author o00559125
 * @since 2021-08-23
 */
data class MoviesTopRated(
    var popularity: Double,
    var vote_count: String,
    var poster_path: String,
    var id: Int,
    var adult: Boolean,
    var backdrop_path: String,
    var original_language: String,
    var original_title: String,
    var title: String,
    var vote_average : Double,
    var overview : String,
    var release_date : String

)