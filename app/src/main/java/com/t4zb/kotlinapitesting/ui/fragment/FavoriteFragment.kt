package com.t4zb.kotlinapitesting.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.helper.GmsFavoriteHelper


class FavoriteFragment(context: Context) : Fragment() {

    private val  mContext = context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    init {
        GmsFavoriteHelper(mContext).getFavoriteList()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    companion object {


    }
}