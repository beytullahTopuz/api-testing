package com.t4zb.kotlinapitesting.modelLayer.rest.service.event

import com.t4zb.kotlinapitesting.modelLayer.rest.service.response.MoviesTopRated

/**
 * Data Class that we are going to use in a [List] format to
 * get our result [List] will be the [com.t4zb.kotlinapitesting.modelLayer.rest.service.response.MoviesTopRated]
 *
 * @author o00559125
 * @since 2021-08-23
 */
class MoviesByTopRatedList (val result: List<MoviesTopRated>)