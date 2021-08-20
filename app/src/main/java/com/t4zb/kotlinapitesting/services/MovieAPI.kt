package com.t4zb.kotlinapitesting.services


import com.t4zb.kotlinapitesting.model.Movies
import retrofit2.Call
import retrofit2.http.GET

interface MovieAPI {
 //   @GET

    //GET all popular link
    // https://api.themoviedb.org/3/movie/popular?api_key=841d0fa80309aa3e96d864930905571d&language=en-US&page=1

    // base url : https://api.themoviedb.org/3
    @GET("/3/movie/popular?api_key=841d0fa80309aa3e96d864930905571d&language=en-US&page=1")
    fun getAllData(): Call<Movies>
}