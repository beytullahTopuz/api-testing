package com.t4zb.kotlinapitesting.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.t4zb.kotlinapitesting.modelLayer.rest.service.repo.MovieRepository
import com.t4zb.kotlinapitesting.modelLayer.rest.service.response.MoviesPopularity
import com.t4zb.kotlinapitesting.modelLayer.rest.service.response.MoviesTopRated

/**
 * [androidx.lifecycle.AndroidViewModel]
 *
 * @author o00559125
 * @since 2021-08-23
 */
class SharedViewModel (app: Application) : AndroidViewModel(app) {

    val dataRepo = MovieRepository(app)

    val selectedMoviePop = MutableLiveData<MoviesPopularity>()
    val selectedMovieTopRated = MutableLiveData<MoviesTopRated>()
}