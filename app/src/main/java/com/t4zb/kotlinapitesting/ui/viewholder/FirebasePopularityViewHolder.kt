package com.t4zb.kotlinapitesting.ui.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.helper.GmsFavoriteHelper
import com.t4zb.kotlinapitesting.model.MoviesFavorite
import com.t4zb.kotlinapitesting.helper.PicassoHelper

/**
 * 功能描述
 *
 * @author o00559125
 * @since 2021-09-08
 */
class FirebasePopularityViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val parent: View = itemView

    val favorite_card_image_view: ImageView
    val textView_title: TextView
    val delete_image: ImageView



    fun bindUI(model: MoviesFavorite) {
        PicassoHelper.picassoUtils(parent.context,model.poster_path,favorite_card_image_view)
        textView_title.text = model.original_title
    }

    init {
        favorite_card_image_view = parent.findViewById(R.id.favorite_card_image_view)
        textView_title = parent.findViewById(R.id.textView_title)
        delete_image = parent.findViewById(R.id.delete_image)
    }
}