package com.t4zb.kotlinapitesting.helper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.modelLayer.rest.service.response.MoviesPopularity
import com.t4zb.kotlinapitesting.modelLayer.rest.service.response.MoviesTopRated
import com.t4zb.kotlinapitesting.ui.viewholder.FirebaseViewHolder
import com.t4zb.kotlinapitesting.util.FirebaseConstants
import com.t4zb.kotlinapitesting.util.showLogDebug
import com.t4zb.kotlinapitesting.util.showLogError

object GmsFavoriteHelper {
    private const val TAG = "GmsFavoriteHelper"

    fun insertFavoritePop(moviesPopularity: MoviesPopularity) {
        val favPopDbRef = FirebaseDbHelper.getMoviesFavorite()
        val favPopMap = HashMap<String, String>()
        favPopMap[FirebaseConstants.ADULT] = moviesPopularity.adult.toString()
        favPopMap[FirebaseConstants.BACKDROP_PATH] = moviesPopularity.backdrop_path
        favPopMap[FirebaseConstants.GENRE_IDS] = moviesPopularity.genre_ids[0].toString()
        favPopMap[FirebaseConstants.ID] = moviesPopularity.id.toString()
        favPopMap[FirebaseConstants.ORIGINAL_LANGUAGE] = moviesPopularity.original_language
        favPopMap[FirebaseConstants.ORIGINAL_TITLE] = moviesPopularity.original_title
        favPopMap[FirebaseConstants.OVERVIEW] = moviesPopularity.overview
        favPopMap[FirebaseConstants.POPULARITY] = moviesPopularity.popularity.toString()
        favPopMap[FirebaseConstants.POSTER_PATH] = moviesPopularity.poster_path
        favPopMap[FirebaseConstants.RELEASE_DATE] = moviesPopularity.release_date
        favPopMap[FirebaseConstants.TITLE] = moviesPopularity.title
        favPopMap[FirebaseConstants.VIDEO] = moviesPopularity.video.toString()
        favPopMap[FirebaseConstants.VOTE_AVERAGE] = moviesPopularity.vote_average.toString()
        favPopMap[FirebaseConstants.VOTE_COUNT] = moviesPopularity.vote_count.toString()

        showLogDebug(TAG,moviesPopularity.title)
        favPopDbRef.setValue(favPopMap).addOnCompleteListener { taskResult ->
            if (taskResult.isSuccessful) {
                showLogDebug(TAG, taskResult.result.toString())
            } else {
                showLogError(TAG, taskResult.exception?.message.toString())
            }
        }
    }

    fun insertFavoriteTOP(moviesTopRated: MoviesTopRated){
        val favTopDbRef = FirebaseDbHelper.getMoviesFavorite()
        val favTopMap = HashMap<String, String>()

        favTopMap[FirebaseConstants.POPULARITY] = moviesTopRated.popularity.toString()
        favTopMap[FirebaseConstants.VOTE_COUNT] = moviesTopRated.vote_count.toString()
        favTopMap[FirebaseConstants.POSTER_PATH] = moviesTopRated.poster_path
        favTopMap[FirebaseConstants.ID] = moviesTopRated.id.toString()
        favTopMap[FirebaseConstants.ADULT] = moviesTopRated.adult.toString()
        favTopMap[FirebaseConstants.BACKDROP_PATH] = moviesTopRated.backdrop_path
        favTopMap[FirebaseConstants.ORIGINAL_LANGUAGE] = moviesTopRated.original_language
        favTopMap[FirebaseConstants.ORIGINAL_TITLE] = moviesTopRated.original_title
        favTopMap[FirebaseConstants.TITLE] = moviesTopRated.title
        favTopMap[FirebaseConstants.VOTE_AVERAGE] = moviesTopRated.vote_average.toString()
        favTopMap[FirebaseConstants.OVERVIEW] = moviesTopRated.overview
        favTopMap[FirebaseConstants.RELEASE_DATE] = moviesTopRated.release_date



        favTopDbRef.setValue(favTopMap).addOnCompleteListener { taskResult->
            if (taskResult.isSuccessful) {
                showLogDebug(TAG, taskResult.result.toString())
            } else {
                showLogError(TAG, taskResult.exception?.message.toString())
            }

        }

    }

    fun setFirebaseRecycler(recyclerView: RecyclerView) {
        val options = FirebaseRecyclerOptions.Builder<MoviesPopularity>()
            .setQuery(FirebaseDbHelper.getMoviesFavorite(), MoviesPopularity::class.java).build()
        val adapterFire =
            object : FirebaseRecyclerAdapter<MoviesPopularity, FirebaseViewHolder>(options) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): FirebaseViewHolder {
                    //Inflater
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_card, parent, false)
                    return FirebaseViewHolder(view)
                }

                override fun onBindViewHolder(
                    holder: FirebaseViewHolder,
                    position: Int,
                    model: MoviesPopularity
                ) {
                    // Click item id
                    val lisResUID = getRef(position).key
                    holder.bindUI()
                }
            }
        adapterFire.startListening()
        recyclerView.adapter = adapterFire
    }

    fun getFavoriteList() {
        FirebaseDbHelper.getMoviesFavorite().get().addOnCompleteListener { taskResult ->
            if (taskResult.isSuccessful) {

            }
        }
    }

    fun deleteFavorites(favoriteID: String) {
        FirebaseDbHelper.getMoviesFavorite().child(favoriteID).removeValue()
            .addOnCompleteListener { taskResult ->
                if (taskResult.isSuccessful) {
                    showLogDebug(TAG, taskResult.result.toString())
                } else {
                    showLogError(TAG, taskResult.exception?.message.toString())
                }
            }
    }
}