package com.t4zb.kotlinapitesting.modelLayer.rest.service.repo

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.t4zb.kotlinapitesting.modelLayer.rest.service.api.GetMovieEndPointApi
import com.t4zb.kotlinapitesting.modelLayer.rest.service.event.MoviesByPopularityList
import com.t4zb.kotlinapitesting.modelLayer.rest.service.event.MoviesByTopRatedList
import com.t4zb.kotlinapitesting.modelLayer.rest.service.request.RetrofitClientInstance
import com.t4zb.kotlinapitesting.modelLayer.rest.service.response.MoviesPopularity
import com.t4zb.kotlinapitesting.modelLayer.rest.service.response.MoviesTopRated
import com.t4zb.kotlinapitesting.util.Constants
import com.t4zb.kotlinapitesting.util.showLogError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
class MovieRepository(val app: Application) {
    val moviePopularityData = MutableLiveData<List<MoviesPopularity>>()
    val movieTopRatedData = MutableLiveData<List<MoviesTopRated>>()

    val movieBanner = MutableLiveData<MoviesPopularity>()

    var isDataTopRated = MutableLiveData<Boolean>()

    @WorkerThread
    fun callWebServiceForMoviePopularityEntity() {
        val retrofit = RetrofitClientInstance.buildRetrofit(app.applicationContext)
        val service = retrofit!!.create(GetMovieEndPointApi::class.java)
        val call = service.getMoviesByPopularity(Constants.LANGUAGE, Constants.API_KEY)
        call.enqueue(object :
            Callback<MoviesByPopularityList> {
            override fun onResponse(
                call: Call<MoviesByPopularityList>,
                response: Response<MoviesByPopularityList>
            ) {
                if (response.isSuccessful) {
                    moviePopularityData.postValue(response.body()!!.results)
                    movieBanner.postValue(response.body()!!.results[0])
                    isDataTopRated.postValue(false)
                }
            }

            override fun onFailure(call: Call<MoviesByPopularityList>, t: Throwable) {
                showLogError(TAG, t.printStackTrace().toString())
            }
        })
    }

    @WorkerThread
    fun callWebServiceForMovieTopRatedEntity() {
        val retrofit = RetrofitClientInstance.buildRetrofit(app.applicationContext)
        val service = retrofit!!.create(GetMovieEndPointApi::class.java)
        service.getMoviesByTopRated(Constants.LANGUAGE, Constants.API_KEY).enqueue(object :
            Callback<MoviesByTopRatedList> {
            override fun onResponse(
                call: Call<MoviesByTopRatedList>,
                response: Response<MoviesByTopRatedList>
            ) {
                if (response.isSuccessful) {
                    movieTopRatedData.postValue(response.body()!!.results)
                    isDataTopRated.postValue(false)
                }
            }

            override fun onFailure(call: Call<MoviesByTopRatedList>, t: Throwable) {
                showLogError(TAG, t.printStackTrace().toString())
            }
        })
    }


    init {
        CoroutineScope(Dispatchers.IO).launch {
            callWebServiceForMoviePopularityEntity()
            callWebServiceForMovieTopRatedEntity()
        }
    }

    companion object {
        private const val TAG = "MovieRepository"
    }
}