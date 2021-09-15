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
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)