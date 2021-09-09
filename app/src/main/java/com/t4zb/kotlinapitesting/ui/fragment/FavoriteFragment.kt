package com.t4zb.kotlinapitesting.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.databinding.FragmentFavoriteBinding
import com.t4zb.kotlinapitesting.helper.GmsFavoriteHelper
import com.t4zb.kotlinapitesting.ui.contract.BaseContract
import com.t4zb.kotlinapitesting.ui.fragment.basefragment.BaseFragment


class FavoriteFragment : BaseFragment(R.layout.fragment_favorite), BaseContract.ViewMain {




    private val  mContext = context

    private lateinit var mBinding : FragmentFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    init {
        //GmsFavoriteHelper(mContext).getFavoriteList()

        GmsFavoriteHelper.setFirebaseRecycler(mBinding.moviesFavoriteRecyclerView)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentFavoriteBinding.bind(view)
    }


    companion object {


    }

    override fun setupViewModel() {
        TODO("Not yet implemented")
    }

    override fun initializeViews() {
        TODO("Not yet implemented")
    }
}