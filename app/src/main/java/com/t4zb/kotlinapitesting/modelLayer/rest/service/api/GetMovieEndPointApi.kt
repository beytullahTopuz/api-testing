package com.t4zb.kotlinapitesting.modelLayer.rest.service.api

import com.t4zb.kotlinapitesting.modelLayer.rest.service.event.MoviesByPopularityList
import com.t4zb.kotlinapitesting.modelLayer.rest.service.event.MoviesByTopRatedList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * We are going to use
 *
 * [retrofit2.Call]
 *
 * [retrofit2.http.GET]
 *
 * [retrofit2.http.Path]
 *
 * [retrofit2.http.Query]
 *
 * to utilize our response from the endpoint
 *
 * @author o00559125
 * @since 2021-08-23
 */
interface GetMovieEndPointApi {

    /**
     * https://api.themoviedb.org/3/movie/popular?api_key=841d0fa80309aa3e96d864930905571d
     * Headers & Querry
     * <p>
     *
     *     @Headers({"cache-control:public, max-age=21600","content-type:content-type"})
     *     @Query("api_key","841d0fa80309aa3e96d864930905571d")
     *
     * @param language String
     * @param api_key String
     */
    @GET("movie/popular")
    fun getMoviesByPopularity(
        @Query("language") language: String?,
        @Query("api_key") apiKey: String?
    ): Call<MoviesByPopularityList>


    @GET("movie/top_rated")
    fun getMoviesByTopRated(
        @Query("language") language: String?,
        @Query("api_key") apiKey: String?
    ):Call<MoviesByTopRatedList>
}