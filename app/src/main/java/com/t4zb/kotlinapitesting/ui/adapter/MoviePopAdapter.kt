package com.t4zb.kotlinapitesting.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.helper.PicassoHelper
import com.t4zb.kotlinapitesting.modelLayer.rest.service.response.MoviesPopularity
import com.t4zb.kotlinapitesting.ui.viewmodel.SharedViewModel
import java.util.ArrayList

/**
 * 功能描述
 *
 * @author o00559125
 * @since 2021-08-26
 */
class MoviePopAdapter(context: Context, movies: ArrayList<MoviesPopularity>, viewModel: SharedViewModel) : RecyclerView.Adapter<MoviePopAdapter.ViewHolder>(){

    private val mContext = context
    private val mMovies = movies
    private val mViewModel = viewModel

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val parent = itemView
        // ImageView
        val imageView: ImageView = parent.findViewById(R.id.image_movies)
        // CardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_movies,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPosition = mMovies[position]

        with(holder) {
            imageView.let {
                PicassoHelper.picassoOkhttp(mContext, currentPosition.poster_path,imageView)
            }
        }

        holder.imageView.setOnClickListener {
            // Navigation Components - NavController
        }
    }

    override fun getItemCount(): Int {
        return mMovies.size
    }
}