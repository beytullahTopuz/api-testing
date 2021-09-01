package com.t4zb.kotlinapitesting.ui.adapter

import android.content.Context
import android.provider.SyncStateContract
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.navigation.Navigation

import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.helper.PicassoHelper
import com.t4zb.kotlinapitesting.modelLayer.rest.service.response.MoviesTopRated
import com.t4zb.kotlinapitesting.ui.fragment.HomeFragmentDirections
import com.t4zb.kotlinapitesting.ui.viewmodel.SharedViewModel
import com.t4zb.kotlinapitesting.util.Constants
import java.util.ArrayList

class MovieTopRatedAdapter(context: Context, movies: ArrayList<MoviesTopRated>, viewModel: SharedViewModel)
    : RecyclerView.Adapter<MovieTopRatedAdapter.ViewHolder>()
{

    private val mContext = context
    private val mMovies = movies
    private val mViewModel = viewModel

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val parent = itemView
        val imageView: ImageView = parent.findViewById(R.id.image_movies)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var view = layoutInflater.inflate(R.layout.item_movies,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentPosition = mMovies[position]
        with(holder){
            imageView.let {
                PicassoHelper.picassoOkhttp(mContext,currentPosition.poster_path,imageView)
            }
        }


        holder.parent.setOnClickListener {

           // val transitionName = mContext.getString(R.string.detail_card_transition)
           // val extras = FragmentNavigatorExtras(holder. to transitionName)
            val direction = HomeFragmentDirections.actionHomeFragmentDirectionToDetailFragment2()

            mViewModel.movieType.value = Constants.MOVIE_TYPE_TOP_RATED
            mViewModel.selectedMovieTopRated.value = currentPosition
            findNavController(holder.parent).navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return mMovies.size
    }
}


