package com.t4zb.kotlinapitesting.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialElevationScale
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.helper.PicassoHelper
import com.t4zb.kotlinapitesting.modelLayer.rest.service.response.MoviesPopularity
import com.t4zb.kotlinapitesting.ui.fragment.HomeFragmentDirections
import com.t4zb.kotlinapitesting.ui.viewmodel.SharedViewModel
import com.t4zb.kotlinapitesting.util.Constants
import com.t4zb.kotlinapitesting.util.showLogDebug
import java.util.*


/**
 * 功能描述
 *
 * @author o00559125
 * @since 2021-08-26
 */
class MoviePopAdapter(
    context: Context,
    movies: ArrayList<MoviesPopularity>,
    viewModel: SharedViewModel
) : RecyclerView.Adapter<MoviePopAdapter.ViewHolder>() {

    private val mContext = context
    private val mMovies = movies
    private val mViewModel = viewModel

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parent = itemView
        val cardImage: ImageView = parent.findViewById(R.id.card_image_view)
        val textView: TextView = parent.findViewById(R.id.textViewMovieName)
        val transitioningCard: MaterialCardView = parent.findViewById(R.id.transition_card_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentPosition = mMovies[position]


        with(holder) {

            cardImage.let {
                PicassoHelper.picassoOkhttp(mContext, currentPosition.poster_path, cardImage)
            }
            textView.let {
                it.text = currentPosition.title
            }
        }

        holder.transitioningCard.setOnClickListener {

            val transitionName = mContext.getString(R.string.detail_card_transition)
            val extras = FragmentNavigatorExtras(holder.transitioningCard to transitionName)
            val direction = HomeFragmentDirections.actionHomeFragmentDirectionToDetailFragment2()

            mViewModel.selectedMoviePop.value = currentPosition
            mViewModel.movieType.value = Constants.MOVIE_TYPE_POPULAR
            findNavController(holder.parent).navigate(direction, extras)
            showLogDebug(TAG, "Movie: ${mViewModel.selectedMoviePop.value.toString()}")
        }
    }

    override fun getItemCount(): Int {
        return mMovies.size
    }

    companion object {
        private const val TAG = "MoviePopAdapter"
    }
}