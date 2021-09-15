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
    val popularity: Double,
    val vote_count: String,
    val poster_path: String,
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String,
    val original_language: String,
    val original_title: String,
    val title: String,
    val vote_average: Double,
    val overview: String,
    val release_date: String
)