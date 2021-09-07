package com.t4zb.kotlinapitesting.helper

import android.content.Context
import com.t4zb.kotlinapitesting.util.FirebaseConstants
import com.t4zb.kotlinapitesting.util.showLogDebug
import com.t4zb.kotlinapitesting.util.showLogError

class GmsFavoriteHelper(context: Context) {


   public fun insertFavorite(moviesFavorite: MoviesFavorite) {

        FirebaseDbHelper.getMoviesFavorite().setValue(
            hashMapOf(
                FirebaseConstants.ADULT to moviesFavorite.adult,
                FirebaseConstants.BACKDROP_PATH to moviesFavorite.backdrop_path,
                FirebaseConstants.GENRE_IDS to moviesFavorite.genre_ids,
                FirebaseConstants.ID to moviesFavorite.id,
                FirebaseConstants.ORIGINAL_LANGUAGE to moviesFavorite.original_language,
                FirebaseConstants.ORIGINAL_TITLE to moviesFavorite.original_title,
                FirebaseConstants.OVERVIEW to moviesFavorite.overview,
                FirebaseConstants.POPULARITY to moviesFavorite.popularity,
                FirebaseConstants.POSTER_PATH to moviesFavorite.poster_path,
                FirebaseConstants.RELEASE_DATE to moviesFavorite.release_date,
                FirebaseConstants.TITLE to moviesFavorite.title,
                FirebaseConstants.VIDEO to moviesFavorite.video,
                FirebaseConstants.VOTE_AVERAGE to moviesFavorite.vote_average,
                FirebaseConstants.VOTE_COUNT to moviesFavorite.vote_count
            )

        ).addOnCompleteListener { taskResult->
            if (taskResult.isSuccessful){
                showLogDebug(TAG,taskResult.result.toString())
            }else{
                showLogError(TAG,taskResult.exception?.message.toString())
            }
        }
    }
    public fun getFavoriteList(){
        FirebaseDbHelper.getMoviesFavorite().get().addOnCompleteListener {  taskResult->
            if (taskResult.isSuccessful){

            }
        }
    }
    public fun deleteFavorites(favoirteID : String){
        FirebaseDbHelper.getMoviesFavorite().child(favoirteID).removeValue().addOnCompleteListener { taskResult->
            if (taskResult.isSuccessful){
                showLogDebug(TAG,taskResult.result.toString())
            }else{
                showLogError(TAG,taskResult.exception?.message.toString())
            }
        }
    }
    companion object {
        private const val TAG = "GmsFavoriteHelper"
    }
}