package com.t4zb.kotlinapitesting.helper

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import com.t4zb.kotlinapitesting.R

/**
 * HeaderView
 *
 * @author o00559125
 * @since 2021-08-31
 */
class HeaderView : LinearLayout {

   // lateinit var searchButton: ImageView

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
//        searchButton = findViewById(R.id.widget_header_search)
    }

    fun bindTo() {

    }
}