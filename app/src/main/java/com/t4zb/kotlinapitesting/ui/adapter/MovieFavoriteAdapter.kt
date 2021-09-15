package com.t4zb.kotlinapitesting.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.t4zb.kotlinapitesting.model.MoviesFavorite
import com.t4zb.kotlinapitesting.ui.viewmodel.SharedViewModel

class MovieFavoriteAdapter(
    context: Context,
    moviesFavoriteList: ArrayList<MoviesFavorite>,
    viewModel: SharedViewModel
) : RecyclerView.Adapter<MovieFavoriteAdapter.ViewHolder>() {

    private val mContext = context
    private val mMovies = moviesFavoriteList
    private val mViewModel = viewModel


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parent = itemView

    }

    companion object {
        private const val TAG = "MovieFavoriteAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return mMovies.size
    }
}