package com.t4zb.kotlinapitesting.ui.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.helper.MoviesFavorite
import com.t4zb.kotlinapitesting.helper.PicassoHelper

/**
 * 功能描述
 *
 * @author o00559125
 * @since 2021-09-08
 */
class FirebasePopularityViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val parent: View = itemView

    val image: ImageView
    val text: TextView

    fun bindUI(model: MoviesFavorite) {
        PicassoHelper.picassoUtils(parent.context,model.poster_path,image)
        text.text = model.original_title
    }

    init {
        image = parent.findViewById(R.id.card_image_view)
        text = parent.findViewById(R.id.textViewMovieName)
    }
}