package com.t4zb.kotlinapitesting.modelLayer.rest.service.repo

/**
 * #    The heart of this application
 *
 * Basically every part of our entity will be gather here
 * in a basis wrapped classes of **@WorkerThread** [kotlinx.coroutines]
 * will help us to call our web services every list that we
 * called ([com.t4zb.kotlinapitesting.modelLayer.rest.service.event.MoviesByPopularityList] and [com.t4zb.kotlinapitesting.modelLayer.rest.service.event.MoviesByTopRatedList])
 * and every entity that we generated
 * ([com.t4zb.kotlinapitesting.modelLayer.rest.service.response.MoviesTopRated] and [com.t4zb.kotlinapitesting.modelLayer.rest.service.response.MoviesPopularity])
 * will be used here
 *
 * ##   Classes that we will use are like this, Please Check :
 *
 * [kotlinx.coroutines.CoroutineScope]
 *
 * [kotlinx.coroutines.Dispatchers]
 *
 * [kotlinx.coroutines.launch]
 *
 * [retrofit2.Call]
 *
 * [retrofit2.Callback]
 *
 * [retrofit2.Response]
 *
 * @author o00559125
 * @since 2021-08-23
 */
class MovieRepository {
}