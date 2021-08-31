package com.t4zb.kotlinapitesting.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation.findNavController
import androidx.preference.PreferenceFragment
import androidx.recyclerview.widget.RecyclerView
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.helper.PicassoHelper
import com.t4zb.kotlinapitesting.modelLayer.rest.service.response.MoviesPopularity
import com.t4zb.kotlinapitesting.ui.activity.MainActivity
import com.t4zb.kotlinapitesting.ui.viewmodel.SharedViewModel
import java.util.ArrayList


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


        // CardView

      //  val cardView: CardView = parent.findViewById(R.id.item_card_view)
        val cardImage : ImageView = parent.findViewById(R.id.card_image_view)
        val textView : TextView = parent.findViewById(R.id.textViewMovieName)


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
            textView.text = currentPosition.title



        }

        holder.parent.setOnClickListener {

            //findNavController().navigate(R.id.homeFragment, null)

        }

      /*  holder.imageView.setOnClickListener {
            // Navigation Components - NavController
        }
        */
    }

    override fun getItemCount(): Int {
        return mMovies.size
    }
}